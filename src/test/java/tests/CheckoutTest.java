package tests;

import base.BaseTest;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;
import utils.ExcelReader;
import org.testng.annotations.Listeners;

import static java.lang.Thread.sleep;
@Epic("E-Commerce Store")
@Feature("CheckOut Test")
@Listeners({ io.qameta.allure.testng.AllureTestNg.class })
public class CheckoutTest extends BaseTest {


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


    @DataProvider(name = "checkoutData")
    public Object[][] checkoutData() {
        String sheetName = "CheckoutData";
        int totalRows = ExcelReader.getRowCount(sheetName);
        int totalCols = ExcelReader.getColCount(sheetName, 1);

        Object[][] data = new Object[totalRows][totalCols];

        for (int row = 1; row <= totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                String val = ExcelReader.getData(sheetName, row, col);
                data[row - 1][col] = (val != null) ? val.trim() : "";
            }
        }
        return data;
    }
    @Test(dataProvider = "checkoutData")
    @Story("Full Checkout Process")
    public void fullCheckoutProcessTest(
            String email, String password,
            String product, String productPrice,
            String fName, String lName,
            String address, String city, String postCode,
            String country, String region,
            String comment, String deliveryDate) {


        if (email == null || email.isBlank()) {
            login.login(ConfigReader.get("username"), ConfigReader.get("password"));
        } else {
            login.login(email, password);
        }


        boolean hasDeliveryDate = deliveryDate != null && !deliveryDate.trim().isEmpty();
        if (hasDeliveryDate) {
            home.openLaptops();
        } else {
            home.openMP3Players();
        }


        home.addProductToCart(product.trim(), deliveryDate);


        String actualSuccessMsg = home.getSuccessMessageText();
        Assert.assertTrue(actualSuccessMsg.contains("Success"),
                "Success message alert not displayed!");
        Assert.assertTrue(actualSuccessMsg.contains(product.trim()),
                "Success message does not contain the product name!");


        boolean isPriceOk = home.isProductPriceCorrectInMiniCart(product.trim(), productPrice);
        Assert.assertTrue(isPriceOk, "Product price is incorrect in mini cart!");


        home.goToViewCartPage();
        Assert.assertTrue(cart.isItemDisplayed(product.trim()),
                "Product not found in full cart page!");


        cart.clickCheckout();
        checkout.fillBillingDetails(fName, lName, address, city, postCode, country, region);
        checkout.confirmShippingAndDelivery(comment);
        checkout.confirmPaymentMethod();

        String successMsg = checkout.confirmOrder();
        Assert.assertEquals(successMsg, "Your order has been placed!", "Order failed!");


        String cartTotal = home.getMiniCartText();
        Assert.assertTrue(cartTotal.contains("0 item(s)"),
                "Cart is not empty after order! Found: " + cartTotal);

        home.clickLogout();
        Assert.assertEquals(home.getHeadingText(), "Account Logout");
    }
}
