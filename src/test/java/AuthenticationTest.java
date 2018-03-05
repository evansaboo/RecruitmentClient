
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
public class AuthenticationTest {

    public void login(WebDriver driver, String username, String password, By by) {

        WebElement usernameElem = driver.findElement(By.id("login-form:lg_username"));
        usernameElem.clear();
        usernameElem.sendKeys(username);
        driver.findElement(By.id("login-form:lg_password")).sendKeys(password);
        driver.findElement(By.className("login-button")).click();
        waitUntil(driver, by);
    }

    public void waitUntil(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void register(WebDriver driver,
            String firstname,
            String surname,
            String email,
            String username,
            String password,
            String password2,
            String ssn,
            By by, boolean regSuccess) {

        driver.findElement(By.id("dropdownMenuButton")).click();
        WebElement nameElem = driver.findElement(By.id("register-form:name"));
        WebElement snameElem = driver.findElement(By.id("register-form:surname"));
        WebElement emailElem = driver.findElement(By.id("register-form:email"));
        WebElement unameElem = driver.findElement(By.id("register-form:username"));
        WebElement pwdElem = driver.findElement(By.id("register-form:password"));
        WebElement pwd2Elem = driver.findElement(By.id("register-form:password2"));
        WebElement ssnElem = driver.findElement(By.id("register-form:ssn"));

        nameElem.clear();
        nameElem.sendKeys(firstname);

        snameElem.clear();
        snameElem.sendKeys(surname);

        emailElem.clear();
        emailElem.sendKeys(email);

        unameElem.clear();
        unameElem.sendKeys(username);

        pwdElem.clear();
        pwdElem.sendKeys(password);

        pwd2Elem.clear();
        pwd2Elem.sendKeys(password2);

        ssnElem.clear();
        ssnElem.sendKeys(ssn);

        WebElement regSubmitBtn = driver.findElement(By.id("register-form:regSubmitBtn"));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView();", regSubmitBtn);

        regSubmitBtn.click();
        if (!regSuccess) {
            driver.findElement(By.id("dropdownMenuButton")).click();
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView();", driver.findElement(By.id("register-form:regSubmitBtn")));
        }
        waitUntil(driver, by);
    }
}
