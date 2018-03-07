
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
public class RegisterTest extends CommonMethods {

    @Test
    public void testRegister(WebDriver driver,
            String firstname,
            String surname,
            String email,
            String username,
            String password,
            String password2,
            String ssn) throws Exception {
        register(driver, "", "test", "test@gg.se", "test2", "1234", "1234", "1234567853", By.id("register-form:reg_name_errMsg"), false);
        register(driver, "test", "", "test@gg.se", "test2", "1234", "1234", "1234567853", By.id("register-form:reg_surname_errMsg"), false);
        register(driver, "test", "test", "", username, "1234", "1234", "1234567853", By.id("register-form:reg_email_errMsg"), false);
        register(driver, "test", "test", "test@gg.se", "", "1234", "1234", "1234567853", By.id("register-form:reg_uname_errMsg"), false);
        register(driver, "test", "test", "test@gg.se", "test2", "", "1234", "1234567853", By.id("register-form:reg_pwd_errMsg"), false);
        register(driver, "test", "test", "test@gg.se", "test2", "1234", "", "1234567853", By.id("register-form:reg_pwd2_errMsg"), false);
        register(driver, "test", "test", "test@gg.se", "test2", "1234", "1234", "", By.id("register-form:reg_ssn_errMsg"), false);

        register(driver, "test", "test", "test@gg.se", "test2", password, password + "1", "123456789", By.xpath("//span[@data-notify='message']"), false);
        register(driver, "test", "test", "test@gg.se", "test2", password + "1", password, "123456789", By.xpath("//span[@data-notify='message']"), false);

        register(driver, firstname, surname, email, username, password, password, ssn, By.id("user_menu"), true);
    }

    @Test
    public void testRegisterLink(WebDriver driver) throws Exception {
        driver.findElement(By.linkText("Login/Register")).click();
        waitUntil(driver, By.id("dropdownMenuButton"));
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
        waitUntil(driver, By.id("register-form:regSubmitBtn"));
        regSubmitBtn.click();

        if (!regSuccess) {
            WebElement elem = waitUntil(driver, By.id("dropdownMenuButton"));
            elem.click();
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView();", waitUntil(driver, By.id("register-form:regSubmitBtn")));
        }
        waitUntil(driver, by);

    }
}
