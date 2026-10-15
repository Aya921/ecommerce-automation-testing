package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {

    WebDriver driver;
    WebDriverWait wait;

    By billingSection = By.id("collapse-payment-address");
    By newAddressRadio = By.cssSelector("input[value='new']");
    By firstNameInput = By.id("input-payment-firstname");
    By lastNameInput = By.id("input-payment-lastname");
    By addressInput = By.id("input-payment-address-1");
    By cityInput = By.id("input-payment-city");
    By postCodeInput = By.id("input-payment-postcode");
    By countrySelect = By.id("input-payment-country");
    By regionSelect = By.id("input-payment-zone");
    By billingContinueBtn = By.id("button-payment-address");

    By shippingContinueBtn = By.id("button-shipping-address");
    By commentBox = By.name("comment");
    By shippingMethodBtn = By.id("button-shipping-method");

    By agreeCheckbox = By.name("agree");
    By paymentMethodBtn = By.id("button-payment-method");

    By confirmBtn = By.id("button-confirm");
    By successTitle = By.cssSelector("#content h1");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void fillBillingDetails(String firstName, String lastName, String address, String city, String postCode,
                                   String country, String region) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(billingSection));

        WebElement radio = driver.findElement(newAddressRadio);
        if (!radio.isSelected()) {
            radio.click();
        }

        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(postCodeInput).sendKeys(postCode);

        new Select(driver.findElement(countrySelect)).selectByVisibleText(country);

        WebElement zoneElement = driver.findElement(regionSelect);
        wait.until(ExpectedConditions.textToBePresentInElement(zoneElement, region));
        new Select(zoneElement).selectByVisibleText(region);

        driver.findElement(billingContinueBtn).click();
    }

    public void confirmShippingAndDelivery(String comment) {

        wait.until(ExpectedConditions.elementToBeClickable(shippingContinueBtn)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(commentBox)).sendKeys(comment);
        driver.findElement(shippingMethodBtn).click();
    }

    public void confirmPaymentMethod() {

        wait.until(ExpectedConditions.elementToBeClickable(agreeCheckbox)).click();
        driver.findElement(paymentMethodBtn).click();
    }

    public String confirmOrder() {

        wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();
        wait.until(ExpectedConditions.urlContains("success"));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successTitle)).getText();
    }
}