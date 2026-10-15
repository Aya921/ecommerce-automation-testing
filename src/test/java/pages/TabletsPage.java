package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TabletsPage {

    WebDriver driver;
    WebDriverWait wait;

    public TabletsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    By tabletsLink = By.linkText("Tablets");
    By breadcrumbLast = By.cssSelector(".breadcrumb li:last-child");
    By activeCategory = By.cssSelector("#column-left .list-group-item.active");



    public void openTabletsPage() {
        wait.until(ExpectedConditions.elementToBeClickable(tabletsLink)).click();
    }

    public String getBreadcrumbText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(breadcrumbLast)).getText();
    }

    public String getActiveCategoryText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(activeCategory)).getText();
    }
}