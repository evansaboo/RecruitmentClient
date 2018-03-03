/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
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
    String username;
    String password = "1234";

    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty(
                "webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/RecruitmentClient/");

        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        username = RandomStringUtils.random(length, useLetters, useNumbers);
    }

    @Test
    public void testRegister() throws Exception {
        test.register(driver, "", "test", "test@gg.se", "test2", "1234", "1234", "1234567853", By.id("register-form:reg_name_errMsg"), false);
        test.register(driver, "test", "", "test@gg.se", "test2", "1234", "1234", "1234567853", By.id("register-form:reg_surname_errMsg"), false);
        test.register(driver, "test", "test", "", "", "1234", "1234", "1234567853", By.id("register-form:reg_email_errMsg"), false);
        test.register(driver, "test", "test", "test@gg.se", "", "1234", "1234", "1234567853", By.id("register-form:reg_uname_errMsg"), false);
        test.register(driver, "test", "test", "test@gg.se", "test2", "", "1234", "1234567853", By.id("register-form:reg_pwd_errMsg"), false);
        test.register(driver, "test", "test", "test@gg.se", "test2", "1234", "", "1234567853", By.id("register-form:reg_pwd2_errMsg"), false);
        test.register(driver, "test", "test", "test@gg.se", "test2", "1234", "1234", "", By.id("register-form:reg_ssn_errMsg"), false);

        test.register(driver, "test", "test", "test@gg.se", "test2", password, password + "1", "123456789", By.xpath("//span[@data-notify='message']"), false);

        test.register(driver, "test", "test", "test@gg.se", username, "1234", "1234", "12345678", By.id("user_menu"), true);

    }

    @Test
    public void testLogout() throws Exception {
        driver.findElement(By.id("user_menu")).click();
        driver.findElement(By.linkText("Logout")).click();
    }

    @Test
    public void testRegisterLink() throws Exception {
        driver.findElement(By.linkText("Login/Register")).click();
    }

    @Test
    public void testLogin() throws Exception {

        test.login(driver, "", password, By.id("login-form:errorUsername"));
        test.login(driver, username, "", By.id("login-form:errorPassword"));
        test.login(driver, username + "a", password + "a", By.xpath("//span[@data-notify='message']"));

        test.login(driver, username, password, By.id("user_menu"));
    }
    
    @Test
    public void testParticipantLinks() throws Exception {
        driver.get(new URI(driver.getCurrentUrl()).resolve("apply.xhtml").toString());
        test.waitUntil(driver, By.id("applyForm"));
        
        driver.get(new URI(driver.getCurrentUrl()).resolve("index.xhtml").toString());
        test.waitUntil(driver, By.className("index-content"));
        
        driver.get(new URI(driver.getCurrentUrl()).resolve("applications.xhtml").toString());
        driver.findElement(By.xpath("//h1[contains(.,'403')]"));
        
        driver.get(new URI(driver.getCurrentUrl()).resolve("application_overview.xhtml").toString());
        driver.findElement(By.xpath("//h1[contains(.,'403')]"));
        
        
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.close();
        driver.quit();
    }
}
