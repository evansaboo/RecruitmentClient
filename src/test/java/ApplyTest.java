
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
public class ApplyTest {
    @Test
    public void test() {
        System.out.println("hello");
    }
    public void addCompetence(WebDriver driver, boolean selectComp, boolean selectYOE) {
        Random rand = new Random();
        Select selectorComp = new Select(driver.findElement(By.id("applyForm:t1")));
        Select selectorYOE = new Select(driver.findElement(By.id("applyForm:t2")));
        selectorComp.selectByIndex(0);
        selectorYOE.selectByIndex(0);
        if (selectComp) {
            int optionIndex = rand.nextInt(selectorComp.getOptions().size() - 1);
            selectorComp.selectByIndex(optionIndex + 1);

        }
        if (selectYOE) {
            int optionIndex = rand.nextInt(selectorYOE.getOptions().size());
            selectorYOE.selectByIndex(optionIndex);
        }

        driver.findElement(By.id("applyForm:addCompBtn")).click();
    }

    public void addAvailablity(WebDriver driver, String dateFrom, String dateTo, By by) {
        WebElement dateFromElem = driver.findElement(By.id("applyForm:t3"));
        WebElement dateToElem = driver.findElement(By.id("applyForm:t4"));
        dateFromElem.clear();
        dateFromElem.sendKeys(dateFrom);
        dateToElem.clear();
        dateToElem.sendKeys(dateTo);
        driver.findElement(By.id("applyForm:AddavailabilityButton")).click();
        waitUntil(driver, by);
    }

    public void waitUntil(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
