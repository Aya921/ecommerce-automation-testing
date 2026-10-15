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
import pages.RegisterPage;
import utils.ExcelReader;

import static java.lang.Thread.sleep;
@Listeners({ io.qameta.allure.testng.AllureTestNg.class })
@Epic("E-Commerce Store")
@Feature("Register Test")
public class RegisterTest extends BaseTest {

    RegisterPage register;
    HomePage home;

    @BeforeMethod
    public void setupTest(){
        register = new RegisterPage(driver);
        home = new HomePage(driver);
        home.goToRegisterPage();
    }
    @DataProvider(name = "registerData")
    public Object[][] getRegisterData() {
        String sheet = "Register";
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
    @Test(dataProvider = "registerData")
    @Story("register test")
    public void registerTest(String firstName, String lastName, String email,
                             String phone, String password, String scenario) throws InterruptedException {

        register.fillBasicData(firstName, lastName);

        if(scenario.equals("valid")){

            register.fillFullData(email, phone, password);

            register.clickAgree();
            register.clickContinue();

            Assert.assertEquals(home.getHeadingText(), "Your Account Has Been Created!");

            home.clickLogout();
            Assert.assertEquals(home.getHeadingText(), "Account Logout");

        }

        else if(scenario.equals("empty")){

            register.clickContinue();

            Assert.assertTrue(register.getPageSource().contains("E-Mail Address does not appear"));
            Assert.assertTrue(register.getPageSource().contains("Password must be"));

        }
        else if(scenario.equals("noPassword")){
            register.fillFullData(email, phone, password);

            register.clickContinue();
            Assert.assertTrue(register.getPageSource().contains("Password must be"));

        }

        else if(scenario.equals("invalidPassword")){

            register.fillFullData(email, phone, password);
            register.clickAgree();
            register.clickContinue();

            Assert.assertTrue(register.getPageSource().contains("Password must be between 4 and 20"));

        }
    }
}