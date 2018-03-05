
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Evan
 */
public class ApplicationsTest {
    @Test
    public void test() {
        System.out.println("hello");
    }
    public void searchForApp(WebDriver driver,
            String subDate,
            String avFrom,
            String avTo,
            boolean selectComp,
            String applicantName) {

        WebElement subDateElem = driver.findElement(By.id("searchForm:date"));
        WebElement avFromElem = driver.findElement(By.id("searchForm:dateFrom"));
        WebElement avToElem = driver.findElement(By.id("searchForm:dateTo"));
        WebElement applicantNameElem = driver.findElement(By.id("searchForm:name"));

        subDateElem.clear();
        subDateElem.sendKeys(subDate);

        avFromElem.clear();
        avFromElem.sendKeys(avFrom);

        avToElem.clear();
        avToElem.sendKeys(avTo);

        applicantNameElem.clear();
        applicantNameElem.sendKeys(applicantName);

        Select selectorComp = new Select(driver.findElement(By.id("searchForm:sel1")));
        selectorComp.selectByIndex(0);
        if (selectComp) {
            int optionIndex = (new Random()).nextInt(selectorComp.getOptions().size() - 1);
            selectorComp.selectByIndex(optionIndex + 1);
        }
        
        driver.findElement(By.id("searchForm:searchBtn")).click();
    }

    public WebElement waitUntil(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
