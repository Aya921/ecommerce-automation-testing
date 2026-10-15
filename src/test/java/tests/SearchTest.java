package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchResultsPage;
import utils.ConfigReader;
import utils.ExcelReader;

import java.util.ArrayList;
import java.util.List;


@Listeners({ io.qameta.allure.testng.AllureTestNg.class })
@Epic("E-Commerce Store")
@Feature("Search Test")
public class SearchTest extends BaseTest {


    LoginPage login;
    HomePage home;
    SearchResultsPage results;

    @BeforeMethod
    public void setupTest(){
        login = new LoginPage(driver);
        home = new HomePage(driver);
        home.openLoginPage();
        login.login(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );
         results = new SearchResultsPage(driver);
    }
    @DataProvider(name = "searchData")
    public Object[][] searchData() {
        String sheet = "Search";
        int totalRows = ExcelReader.getRowCount(sheet);
        List<Object[]> data = new ArrayList<>();

        for (int i = 1; i <= totalRows; i++) {
            String keyword = ExcelReader.getData(sheet, i, 0);
            String category = ExcelReader.getData(sheet, i, 1);
            if (keyword != null && !keyword.trim().isEmpty()
                    && (category == null || category.trim().isEmpty())) {
                data.add(new Object[]{ keyword.trim() });
            }
        }

        return data.toArray(new Object[0][]);
    }

    @DataProvider(name = "subCategoryData")
    public Object[][] subCategoryData() {
        String sheet = "Search";
        int totalRows = ExcelReader.getRowCount(sheet);
        List<Object[]> data = new ArrayList<>();

        for (int i = 1; i <= totalRows; i++) {
            String keyword         = ExcelReader.getData(sheet, i, 0);
            String category        = ExcelReader.getData(sheet, i, 1);
            String expectedProduct = ExcelReader.getData(sheet, i, 2);

            if (keyword != null && !keyword.trim().isEmpty()
                    && category != null && !category.trim().isEmpty()) {
                data.add(new Object[]{
                        keyword.trim(),
                        category.trim(),
                        expectedProduct != null ? expectedProduct.trim() : ""
                });
            }
        }

        return data.toArray(new Object[0][]);
    }



  @Test(dataProvider = "searchData")
  @Story("search Test")
  public void searchByNameTest( String keyword) {

    home.searchForProduct(keyword);
    Assert.assertFalse(results.getAllProducts().isEmpty(), "No results found!");


      Assert.assertTrue(
              results.allProductsMatchKeyword(keyword),
              "Some products do not match keyword"
      );


      home.clickLogout();
      Assert.assertEquals(home.getHeadingText(), "Account Logout");

  }



  @Test(dataProvider = "subCategoryData")
  @Story("search subCategiry Test")
  public void searchInSubCategoriesTest( String keyword, String category,
      String expectedProduct) {

    home.openAdvancedSearch();

    results.enterSearchKeyword(keyword);

    results.chooseCategory(category);

    results.clickSearch();


    Assert.assertTrue(results.isNoResultDisplayed());


    results.enableSubCategory();

    results.clickSearch();


      Assert.assertTrue(results.isProductDisplayed(expectedProduct));


      home.clickLogout();
      Assert.assertEquals(home.getHeadingText(), "Account Logout");
  }
}