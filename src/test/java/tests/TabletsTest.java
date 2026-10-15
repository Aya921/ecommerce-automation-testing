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
import pages.TabletsPage;
import utils.ConfigReader;
import utils.ExcelReader;
@Listeners({ io.qameta.allure.testng.AllureTestNg.class })

@Epic("E-Commerce Store")
@Feature("Tablets Test")
public class TabletsTest extends BaseTest {

    LoginPage login;
    TabletsPage tablets;
    HomePage home;

    @BeforeMethod
    public void setupTest() {
        login = new LoginPage(driver);
        tablets = new TabletsPage(driver);
        home = new HomePage(driver);

        home.openLoginPage();
    }



    @Test()
    @Story("tablets Naviagtion")
    public void verifyTabletsNavigation() {

        login.login(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );
        tablets.openTabletsPage();
        Assert.assertEquals(tablets.getBreadcrumbText(), "Tablets");
        Assert.assertTrue(
                tablets.getActiveCategoryText().contains("Tablets")
        );
        home.clickLogout();
        Assert.assertEquals(home.getHeadingText(), "Account Logout");
    }
}