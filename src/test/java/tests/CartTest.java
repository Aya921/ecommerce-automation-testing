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
import pages.*;
import utils.ExcelReader;


@Listeners({ io.qameta.allure.testng.AllureTestNg.class })
@Epic("E-Commerce Store")
@Feature("Shopping Cart")
public class CartTest extends  BaseTest {


    LoginPage login;
    TabletsPage tablets;
    HomePage home;
    CartPage cart;
    CheckoutPage checkout;


    @BeforeMethod
    public void setupTest() {
        login = new LoginPage(driver);
        tablets = new TabletsPage(driver);
        home = new HomePage(driver);

        cart = new CartPage(driver);
        checkout = new CheckoutPage(driver);

        home.openLoginPage();


    }
    @DataProvider(name = "cartData")
    public Object[][] cartData() {
        String sheet = "CartData";
        int totalRows = ExcelReader.getRowCount(sheet);
        int totalCols = 8;

        Object[][] data = new Object[totalRows][totalCols];

        for (int row = 1; row <= totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                String val = ExcelReader.getData(sheet, row, col);
                data[row - 1][col] = (val != null) ? val.trim() : "";
            }
        }
        return data;
    }
    @Story("addItemsToCartTest")
    @Test(dataProvider = "cartData")
    public void addItemsToCartTest( String email,String password,String tablet, String tabletPrice, String laptop,
                                    String laptopPrice, String date, String expectedTotal)
           {


        login.login(email, password);
        home.openTablets();
        home.addProductToCart(tablet, "");
               String actualSuccessMsg = home.getSuccessMessageText();
               Assert.assertTrue(actualSuccessMsg.contains("Success"), "Success message alert not displayed!");
               Assert.assertTrue(actualSuccessMsg.contains(tablet), "Success message does not contain the product name!");


               boolean isTabletPriceOk = home.isProductPriceCorrectInMiniCart(tablet, tabletPrice);
        Assert.assertTrue(isTabletPriceOk, "Tablet price is incorrect in mini cart!");

        home.openLaptops();


        home.addProductToCart(laptop, date);
               String actualSuccessMsg2 = home.getSuccessMessageText();
               Assert.assertTrue(actualSuccessMsg2.contains("Success"), "Success message alert not displayed!");
               Assert.assertTrue(actualSuccessMsg2.contains(laptop), "Success message does not contain the product name!");



        boolean isLaptopDateOk = home.isProductDetailCorrectInMiniCart(laptop, date);
        Assert.assertTrue(isLaptopDateOk, "Laptop delivery date is incorrect in mini cart!");



        String actualTotal = cart.getTotal();


               double actual = Double.parseDouble(actualTotal.replaceAll("[^0-9.]", ""));
               double expected = Double.parseDouble(expectedTotal.replaceAll("[^0-9.]", ""));

               Assert.assertEquals(actual, expected, 0.01, "Total price mismatch!");

               home.clickLogout();
               Assert.assertEquals(home.getHeadingText(), "Account Logout");
    }
}