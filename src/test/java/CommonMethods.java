
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * Common functions used in other test classes
 */
public class CommonMethods {
    
    /**
     * Waits for the provided element to be visible with a timout of 10 seconds  
     * @param driver provided webdriver
     * @param by element to wait for
     * @return WebElement object of the element if the element is found
     */
    public WebElement waitUntil(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    
    /**
     * Finds the table by table id and count amount of rows in it
     * @param driver provided webdriver
     * @param tableId provided table id
     * @return amount of rows in the table
     */
    public int countRowsIntable(WebDriver driver, String tableId) {
        WebElement table = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id(tableId)));
        List<WebElement> rows_table = table.findElements(By.tagName("tr"));
        return rows_table.size();
    }
}
