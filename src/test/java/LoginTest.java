
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
public class LoginTest extends CommonMethods {

    @Test
    public void testRecruiterLogin(WebDriver driver, String username, String password) throws Exception {
        (new LoginTest()).login(driver, username, password, By.id("user_menu"));
    }

    @Test
    public void testLogin(WebDriver driver, String username, String password) throws Exception {

        login(driver, "", password, By.id("login-form:errorUsername"));
        login(driver, username, "", By.id("login-form:errorPassword"));
        login(driver, username + "a", password + "a", By.xpath("//span[@data-notify='message']"));
        login(driver, username, password, By.id("user_menu"));
    }

    @Test
    public void testLogout(WebDriver driver) throws Exception {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.
                        visibilityOfElementLocated(By.id("user_menu"))).click();

        driver.findElement(By.linkText("Logout")).click();
        waitUntil(driver, By.id("login-form"));
    }

    @Test
    public void testParticipantLogout(WebDriver driver) throws Exception {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.
                        visibilityOfElementLocated(By.className("close"))).click();
        Thread.sleep(1000);
        testLogout(driver);
    }

    public void login(WebDriver driver, String username, String password, By by) {

        WebElement usernameElem = driver.findElement(By.id("login-form:lg_username"));
        usernameElem.clear();
        usernameElem.sendKeys(username);
        driver.findElement(By.id("login-form:lg_password")).sendKeys(password);
        driver.findElement(By.className("login-button")).click();
        waitUntil(driver, by);
    }
}
