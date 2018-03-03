/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

/**
 *
 * @author Evan
 */
public class TestClass {

    WebDriver driver;
    AuthenticationTest test = new AuthenticationTest();

    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty(
                "webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/RecruitmentClient/");
    }

    @BeforeMethod
    public void beforeTest() throws Exception {
        driver.findElement(By.linkText("Login/Register")).click();
    }

    @AfterMethod
    public void afterTest() throws Exception {
        driver.findElement(By.id("user_menu")).click();
        driver.findElement(By.linkText("Logout")).click();
    }

    @Test
    public void testLogin() throws Exception {

        test.login(driver, "", "1234", By.id("login-form:errorUsername"));
        test.login(driver, "evan", "", By.id("login-form:errorPassword"));
        test.login(driver, "evan", "1234", By.id("user_menu"));
    }

    @Test
    public void testRegister() throws Exception {
        driver.findElement(By.linkText("Login/Register")).click();

        test.login(driver, "", "1234", By.id("login-form:errorUsername"));
        test.login(driver, "evan", "", By.id("login-form:errorPassword"));
        test.login(driver, "evan", "1234", By.id("user_menu"));
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.close();
        driver.quit();
    }
}
