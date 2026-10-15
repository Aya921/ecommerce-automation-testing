package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    By email = By.id("input-email");
    By password = By.id("input-password");
    By submit = By.cssSelector("input[type='submit']");
    By alertDanger = By.cssSelector(".alert-danger");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String mail, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).clear();
        driver.findElement(email).sendKeys(mail);

        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);

        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();
    }
    public String getAlertDangerText() {
        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(alertDanger)).getText();
    }
}