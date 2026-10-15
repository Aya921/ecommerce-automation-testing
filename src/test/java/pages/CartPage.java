package pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage {

    WebDriver driver;
    WebDriverWait wait;

    By cartRows = By.cssSelector(".table-responsive tbody tr");
    By totalsRows = By.cssSelector("table.table-bordered tr");
    By checkoutBtn = By.cssSelector(".buttons .pull-right a.btn-primary");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isItemDisplayed(String item) {
        wait.until(ExpectedConditions.presenceOfElementLocated(cartRows));
        return !driver.findElements(By.linkText(item)).isEmpty();
    }

    public String getTotal() {
        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(totalsRows));

            List<WebElement> rows = driver.findElements(totalsRows);

            for (WebElement row : rows) {

                String text = row.getText();


                if (text.toLowerCase().contains("total")) {

                    String value = row.findElements(By.tagName("td"))
                            .get(1)
                            .getText();


                    return value;
                }
            }

        } catch (Exception e) {
            System.out.println("Could not get total");
            e.printStackTrace();
        }

        return "Total Not Found";
    }

    public void clickCheckout() {
        WebElement checkout = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));
        checkout.click();
    }
}