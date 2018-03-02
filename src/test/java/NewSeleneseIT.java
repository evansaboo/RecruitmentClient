/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Evan
 */
public class NewSeleneseIT {

    WebDriver driver;
    AuthenticationTest test = new AuthenticationTest();

    @Before
    public void setUp() throws Exception {
        System.setProperty(
                "webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testLogin() throws Exception {
        driver.get("http://localhost:8080/RecruitmentClient/");
        driver.findElement(By.linkText("Login/Register")).click();

        test.login(driver, "", "1234", By.id("login-form:errorUsername"));
        test.login(driver, "evan", "", By.id("login-form:errorPassword"));
        test.login(driver, "evan", "1234", By.id("user_menu"));
    }

    @Test
    public void testRegister() throws Exception {
        driver.get("http://localhost:8080/RecruitmentClient/");
        driver.findElement(By.linkText("Login/Register")).click();

        test.login(driver, "", "1234", By.id("login-form:errorUsername"));
        test.login(driver, "evan", "", By.id("login-form:errorPassword"));
        test.login(driver, "evan", "1234", By.id("user_menu"));
        driver.findElement(By.id("user_menu")).click();
        driver.findElement(By.linkText("Logout")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
        driver.quit();
    }
}
