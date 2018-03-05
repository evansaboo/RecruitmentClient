
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
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
public class RunTests extends CommonMethods {

    RegisterTest regTest = new RegisterTest();
    LoginTest loginTest = new LoginTest();
    ApplicantTest applTest = new ApplicantTest();
    RecruiterTest recTest = new RecruiterTest();

    WebDriver driver;
    String firstname;
    String surname;
    String email;
    String ssn;
    String username;
    String password;
    String submissionDate;
    String avFrom = "12-12-2019";
    String avTo = "12-01-2020";

    @BeforeTest
    @Parameters("browser")
    public void setUp(String browser) throws Exception {

        switch (browser) {
            case "chrome":
                System.setProperty(
                        "webdriver.chrome.driver", "chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", "MicrosoftWebDriver.exe");
                driver = new EdgeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "geckodriver.exe");

                driver = new FirefoxDriver();
                break;
            default:
                System.setProperty(
                        "webdriver.chrome.driver", "chromedriver.exe");
                driver = new ChromeDriver();
                break;

        }

        driver.get("http://localhost:8080/RecruitmentClient/");
        driver.manage().window().maximize();

        initVariables();
    }

    private void initVariables() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        firstname = RandomStringUtils.random(length, useLetters, useNumbers);
        surname = RandomStringUtils.random(length, useLetters, useNumbers);
        ssn = RandomStringUtils.random(length, 0, 8, false, true, "123456789".toCharArray());
        email = RandomStringUtils.random(5, useLetters, useNumbers);
        email = email + "@" + email + ".com";
        username = RandomStringUtils.random(length, useLetters, useNumbers);
        password = RandomStringUtils.random(length, useLetters, true);
    }

    @AfterTest
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testRegisterLink() throws Exception {
        regTest.testRegisterLink(driver);
    }

    @Test
    public void testRegister() throws Exception {
        regTest.testRegister(driver, firstname, surname, email, username, password, password, ssn);
    }

    @Test
    public void testLogout() throws Exception {
        loginTest.testLogout(driver);
    }

    @Test
    public void testLogin() throws Exception {
        loginTest.testLogin(driver, username, password);
    }

    @Test
    public void testParticipantLinks() throws Exception {
        applTest.testParticipantLinks(driver);
    }

    @Test
    public void testApplyPage() throws Exception {
        submissionDate = applTest.testApplyPage(driver, avFrom, avTo, submissionDate);
    }

    @Test
    public void testParticipantLogout() throws Exception {
        loginTest.testParticipantLogout(driver);
    }

    @Test
    public void testRecruiterLogin() throws Exception {
        loginTest.testRecruiterLogin(driver);
    }

    @Test
    public void testRecruiterLinks() throws Exception {
        recTest.testRecruiterLinks(driver);
    }

    @Test
    public void testApplicationsPage() throws Exception {
        recTest.testApplicationsPage(driver, firstname, submissionDate, avFrom, avTo);
    }

    @Test
    public void testAppOverviewPage() throws Exception {
        recTest.testAppOverviewPage(driver, firstname, surname, email, ssn, submissionDate);
    }
}
