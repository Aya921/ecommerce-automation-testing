package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {

    WebDriver driver;
    WebDriverWait wait;

    By products = By.cssSelector(".product-thumb h4 a");
    By searchInput = By.id("input-search");
    By categoryDropdown = By.name("category_id");
    By subCategoryCheckbox = By.name("sub_category");
    By searchBtn = By.id("button-search");
    By noResultMsg = By.cssSelector("#content p:not(:has(label))");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public List<WebElement> getAllProducts() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(products));
        return driver.findElements(products);
    }

    public void enterSearchKeyword(String keyword) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(keyword);
    }

    public void chooseCategory(String category) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(categoryDropdown));
        dropdown.sendKeys(category);
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
    }

    public boolean isNoResultDisplayed() {
        try {
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(noResultMsg));
            return msg.getText().contains("There is no product");
        } catch (Exception e) {
            return false;
        }
    }

    public void enableSubCategory() {
        wait.until(ExpectedConditions.elementToBeClickable(subCategoryCheckbox)).click();
    }

    public boolean isProductDisplayed(String productName) {
        return !driver.findElements(By.linkText(productName)).isEmpty();
    }

    public boolean allProductsMatchKeyword(String keyword) {
        List<WebElement> list = getAllProducts();

        for (WebElement product : list) {
            String name = product.getText().toLowerCase();
            if (!name.contains(keyword.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}