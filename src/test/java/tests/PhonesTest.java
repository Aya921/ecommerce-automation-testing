package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.PhonesPage;
import utils.ConfigReader;
import utils.ExcelReader;

import java.util.ArrayList;
import java.util.List;
@Listeners({ io.qameta.allure.testng.AllureTestNg.class })
@Epic("E-Commerce Store")
@Feature("Phones Test")
public class PhonesTest extends BaseTest {

    LoginPage login;
    PhonesPage phones;
    HomePage home;

    @BeforeMethod
    public void setupTest() {
        login = new LoginPage(driver);
        phones = new PhonesPage(driver);
        home = new HomePage(driver);

        home.openLoginPage();
    }


    @Test()
    @Story("Verify phone sorting")
    public void verifySorting() {


        login.login(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );

        phones.openPhonesPage();


        phones.sortByNameAsc();

        List<String> actualAsc = phones.getProductNames();
        Assert.assertFalse(actualAsc.isEmpty(), "Product list is empty");

        List<String> expectedAsc = new ArrayList<>(actualAsc);
        expectedAsc.sort(String.CASE_INSENSITIVE_ORDER);

        Assert.assertEquals(actualAsc, expectedAsc, "Products not sorted A-Z");


        phones.sortByNameDesc();

        List<String> actualDesc = phones.getProductNames();

        List<String> expectedDesc = new ArrayList<>(actualDesc);
        expectedDesc.sort(String.CASE_INSENSITIVE_ORDER.reversed());

        Assert.assertEquals(actualDesc, expectedDesc, "Products not sorted Z-A");


        home.clickLogout();

        Assert.assertEquals(home.getHeadingText(), "Account Logout");
    }
}