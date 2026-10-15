package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    By searchBox = By.name("search");
    By searchBtn = By.cssSelector("button.btn-default");
    By loginBtn = By.linkText("Login");
    By myAccount = By.linkText("My Account");
    By logout = By.linkText("Logout");
    By currencyDropdown = By.cssSelector("button.btn-link.dropdown-toggle");
    By euroCurrency = By.name("EUR");
    By heading = By.cssSelector("#content h1");
    By registerBtn = By.linkText("Register");
    By homePageHeader = By.cssSelector("#content h2");
    By desktops = By.linkText("Desktops");
    By showAllDesktops = By.linkText("Show AllDesktops");
    By prices = By.cssSelector(".price");
    By myAccountMenu = By.cssSelector("a[title='My Account']");
    By miniCartBtn = By.id("cart-total");
    By cartItemsList = By.cssSelector("ul.dropdown-menu li");

    By laptopsMenu = By.linkText("Laptops & Notebooks");
    By showAllLaptops = By.cssSelector(".dropdown-menu a[href$='path=18']");
    By tabletsLink = By.linkText("Tablets");
    By mp3Menu = By.linkText("MP3 Players");
    By showAllMp3 = By.partialLinkText("Show AllMP3 Players");

    By productLayout = By.cssSelector(".product-layout");
    By addToCartBtn = By.cssSelector("button[onclick*='cart.add']");
    By dateField = By.cssSelector("input[class*='date'], .form-group input[id*='input-option']");
    By successAlert = By.cssSelector(".alert-success");
    By buttonCart = By.id("button-cart");

    By miniCartTableRows = By.cssSelector("table.table-striped tr");
    By miniCartDetails = By.cssSelector("table.table-striped td.text-left small");
    By viewCartLink = By.cssSelector("#cart .dropdown-menu a[href*='checkout/cart']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void searchForProduct(String keyword) {
        wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(keyword);
        driver.findElement(searchBtn).click();
    }

    public String getAllPricesText() {
        return driver.findElement(prices).getText();
    }

    public void openDesktops() {
        wait.until(ExpectedConditions.elementToBeClickable(desktops)).click();
        wait.until(ExpectedConditions.elementToBeClickable(showAllDesktops)).click();
    }

    public void changeCurrencyToEuro() {
        wait.until(ExpectedConditions.elementToBeClickable(currencyDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(euroCurrency)).click();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(myAccount)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logout)).click();
    }

    public String getHeadingText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(heading)).getText();
    }

    public String getHomePageHeaderText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(homePageHeader)).getText();
    }

    public void openLoginPage() {
        wait.until(ExpectedConditions.elementToBeClickable(myAccount)).click();
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }

    public void goToRegisterPage() {
        wait.until(ExpectedConditions.elementToBeClickable(myAccount)).click();
        wait.until(ExpectedConditions.elementToBeClickable(registerBtn)).click();
    }

    public void openAdvancedSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
    }

    public void openTablets() {
        wait.until(ExpectedConditions.elementToBeClickable(tabletsLink)).click();
    }

    public void openLaptops() {
        wait.until(ExpectedConditions.elementToBeClickable(laptopsMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(showAllLaptops)).click();
    }

    public void addProductToCart(String productName, String productDate) {

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productLayout));
        List<WebElement> products = driver.findElements(productLayout);

        for (WebElement product : products) {
            if (product.getText().contains(productName)) {
                WebElement addBtn = product.findElement(addToCartBtn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
                break;
            }
        }

        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(buttonCart),
                    ExpectedConditions.presenceOfElementLocated(dateField)));

            if (!driver.findElements(dateField).isEmpty()) {
                WebElement dateInput = driver.findElement(dateField);
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].setAttribute('value', '" + productDate + "')", dateInput);

                driver.findElement(buttonCart).click();
            }
        } catch (Exception e) {
            System.out.println("Product added directly via catalog.");
        }
    }

    public void openMP3Players() {
        Actions actions = new Actions(driver);
        WebElement mp3 = wait.until(ExpectedConditions.visibilityOfElementLocated(mp3Menu));
        actions.moveToElement(mp3).perform();

        WebElement showAll = wait.until(ExpectedConditions.elementToBeClickable(showAllMp3));
        showAll.click();
    }

    public boolean isProductPriceCorrectInMiniCart(String productName, String expectedPrice) {

        WebElement miniCart = wait.until(ExpectedConditions.elementToBeClickable(miniCartBtn));
        miniCart.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(miniCartTableRows));

        List<WebElement> rows = driver.findElements(miniCartTableRows);

        for (WebElement row : rows) {
            if (row.getText().contains(productName) && row.getText().contains(expectedPrice)) {
                return true;
            }
        }
        return false;
    }

    public boolean isProductDetailCorrectInMiniCart(String productName, String expectedDetail) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert));

           
            WebElement miniCart = wait.until(ExpectedConditions.elementToBeClickable(miniCartBtn));
            miniCart.click();

           
            wait.until(ExpectedConditions.visibilityOfElementLocated(miniCartTableRows));

            
            wait.until(driver -> {
                List<WebElement> details = driver.findElements(miniCartDetails);
                return details.stream().anyMatch(d -> !d.getText().trim().isEmpty());
            });

            List<WebElement> details = driver.findElements(miniCartDetails);

            for (WebElement d : details) {
                String actual = d.getText().trim();
                System.out.println("DEBUG: Found in Site: " + actual);
                System.out.println("DEBUG: Expected from Excel: " + expectedDetail.trim());

                if (actual.contains(expectedDetail.trim())) {
                    return true;
                }
            }

            
            System.out.println("Final Result: Detail not found in any <small> element.");
            System.out.println("All details found:");
            driver.findElements(miniCartDetails)
                    .forEach(d -> System.out.println("  -> [" + d.getText().trim() + "]"));

            return false;

        } catch (Exception e) {
            System.out.println("isProductDetailCorrectInMiniCart error: " + e.getMessage());
            return false;
        }
    }
    public void goToViewCartPage() {

        WebElement miniCart = wait.until(ExpectedConditions.elementToBeClickable(miniCartBtn));
        miniCart.click();

        try {
            WebElement viewCart = wait.until(ExpectedConditions.elementToBeClickable(viewCartLink));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCart);
        } catch (Exception e) {
            miniCart.click();
            WebElement viewCart = wait.until(ExpectedConditions.elementToBeClickable(viewCartLink));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCart);
        }
    }

    public String getSuccessMessageText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert)).getText();
    }

    public String getMiniCartText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(miniCartBtn)).getText();
    }
}