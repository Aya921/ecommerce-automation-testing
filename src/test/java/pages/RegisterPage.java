package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    WebDriver driver;
    WebDriverWait wait;


    By firstName = By.id("input-firstname");
    By lastName = By.id("input-lastname");
    By email = By.id("input-email");
    By phone = By.id("input-telephone");
    By password = By.id("input-password");
    By confirm = By.id("input-confirm");
    By agree = By.name("agree");
    By continueBtn = By.cssSelector("input[type='submit']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }





    public void fillBasicData(String fn, String ln) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(fn);
        driver.findElement(lastName).sendKeys(ln);
    }

    public void fillFullData(String em, String ph, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(em);
        driver.findElement(phone).sendKeys(ph);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(confirm).sendKeys(pass);
    }

    public void clickAgree() {
        wait.until(ExpectedConditions.elementToBeClickable(agree)).click();
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    }
    public String getPageSource(){
        return driver.getPageSource();
    }


}