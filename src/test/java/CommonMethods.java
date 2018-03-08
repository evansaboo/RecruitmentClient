
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Evan
 */
public class CommonMethods {

    public WebElement waitUntil(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    public int countRowsIntable(WebDriver driver, String tableId) {
        WebElement table = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id(tableId)));
        List<WebElement> rows_table = table.findElements(By.tagName("tr"));
        return rows_table.size();
    }
}
