package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExcelReader;

import static java.lang.Thread.sleep;

@Epic("E-Commerce Store")
@Feature("Currency Test")
public class CurrencyTest extends BaseTest {

    LoginPage login;
    HomePage home;

    @BeforeMethod
    public void setupTest() {
        home = new HomePage(driver);
        login = new LoginPage(driver);
        home.openLoginPage();
    }


    @Test()
    @Story("Change Currency Test")
    public void changeCurrencyTest() {


        login.login(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );


        home.openDesktops();
        Assert.assertTrue(
                home.getAllPricesText().contains("$")

        );

        home.changeCurrencyToEuro();

        Assert.assertTrue(
                home.getAllPricesText().contains("€")
        );

        home.clickLogout();

        Assert.assertEquals(home.getHeadingText(), "Account Logout");
    }
}