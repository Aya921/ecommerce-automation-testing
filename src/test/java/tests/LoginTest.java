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
import utils.ExcelReader;
@Listeners({ io.qameta.allure.testng.AllureTestNg.class })
@Epic("E-Commerce Store")
@Feature("Login Test")
public class LoginTest extends BaseTest {

    LoginPage login;
    HomePage home;

    @BeforeMethod
    public void setupTest(){
        login = new LoginPage(driver);
        home = new HomePage(driver);
        home.openLoginPage();
    }


    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        String sheet = "Login";
        int totalRows = ExcelReader.getRowCount(sheet);
        int totalCols = ExcelReader.getColCount(sheet, 1);

        Object[][] data = new Object[totalRows][totalCols];

        for (int row = 1; row <= totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                String val = ExcelReader.getData(sheet, row, col);
                data[row - 1][col] = (val != null) ? val.trim() : "";
            }
        }
        return data;
    }

    @Test(dataProvider = "loginData")
    @Story("Login Test")
    public void loginTest(String email, String password, String expected){

        login.login(email, password);

        if(expected.equals("valid")){

            Assert.assertTrue(home.getHomePageHeaderText().contains("My Account"));

        } else {

            Assert.assertTrue(
                    login.getAlertDangerText().toLowerCase().contains("warning")
            );

        }
    }
}