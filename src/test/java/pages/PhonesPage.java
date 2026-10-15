package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PhonesPage {

    WebDriver driver;
    WebDriverWait wait;

    By phonesLink = By.linkText("Phones & PDAs");
    By sortDropdown = By.id("input-sort");
    By productNames = By.cssSelector(".product-thumb h4 a");

    public PhonesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPhonesPage() {
        wait.until(ExpectedConditions.elementToBeClickable(phonesLink)).click();
    }

    public void sortByNameAsc() {
        Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(sortDropdown)));
        select.selectByVisibleText("Name (A - Z)");
    }

    public void sortByNameDesc() {
        Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(sortDropdown)));
        select.selectByVisibleText("Name (Z - A)");
    }

    public List<String> getProductNames() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNames));

        List<String> names = new ArrayList<>();

        driver.findElements(productNames).forEach(el -> {
            names.add(el.getText());
        });

        return names;
    }
}