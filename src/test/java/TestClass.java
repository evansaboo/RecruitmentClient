/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URI;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 *
 * @author Evan
 */
public class TestClass {

    WebDriver driver;
    AuthenticationTest authTest = new AuthenticationTest();
    ApplyTest applyTest = new ApplyTest();
    ApplicationsTest appTest = new ApplicationsTest();

    String firstname;
    String surname;
    String email;
    String ssn;
    String username;
    String password;
    String submissionDate;
    String avFrom = "12-12-2019";
    String avTo = "12-01-2020";

    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty(
                "webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/RecruitmentClient/");

        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        firstname = RandomStringUtils.random(length, useLetters, useNumbers);
        surname = RandomStringUtils.random(length, useLetters, useNumbers);
        ssn = RandomStringUtils.random(length, false, true);
        email = RandomStringUtils.random(5, useLetters, useNumbers);
        email = email + "@" + email + ".com";
        username = RandomStringUtils.random(length, useLetters, useNumbers);
        password = RandomStringUtils.random(length, useLetters, true);

    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.close();
        driver.quit();
    }

    @Test
    public void testRegister() throws Exception {
        authTest.register(driver, "", "test", "test@gg.se", "test2", "1234", "1234", "1234567853", By.id("register-form:reg_name_errMsg"), false);
        authTest.register(driver, "test", "", "test@gg.se", "test2", "1234", "1234", "1234567853", By.id("register-form:reg_surname_errMsg"), false);
        authTest.register(driver, "test", "test", "", "", "1234", "1234", "1234567853", By.id("register-form:reg_email_errMsg"), false);
        authTest.register(driver, "test", "test", "test@gg.se", "", "1234", "1234", "1234567853", By.id("register-form:reg_uname_errMsg"), false);
        authTest.register(driver, "test", "test", "test@gg.se", "test2", "", "1234", "1234567853", By.id("register-form:reg_pwd_errMsg"), false);
        authTest.register(driver, "test", "test", "test@gg.se", "test2", "1234", "", "1234567853", By.id("register-form:reg_pwd2_errMsg"), false);
        authTest.register(driver, "test", "test", "test@gg.se", "test2", "1234", "1234", "", By.id("register-form:reg_ssn_errMsg"), false);

        authTest.register(driver, "test", "test", "test@gg.se", "test2", password, password + "1", "123456789", By.xpath("//span[@data-notify='message']"), false);
        authTest.register(driver, "test", "test", "test@gg.se", "test2", password + "1", password, "123456789", By.xpath("//span[@data-notify='message']"), false);

        authTest.register(driver, firstname, surname, email, username, password, password, ssn, By.id("user_menu"), true);
    }

    @Test
    public void testLogout() throws Exception {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.
                        visibilityOfElementLocated(By.id("user_menu"))).click();

        driver.findElement(By.linkText("Logout")).click();
    }

    @Test
    public void testRegisterLink() throws Exception {
        driver.findElement(By.linkText("Login/Register")).click();
    }

    @Test
    public void testLogin() throws Exception {

        authTest.login(driver, "", password, By.id("login-form:errorUsername"));
        authTest.login(driver, username, "", By.id("login-form:errorPassword"));
        authTest.login(driver, username + "a", password + "a", By.xpath("//span[@data-notify='message']"));

        authTest.login(driver, username, password, By.id("user_menu"));
    }

    @Test
    public void testParticipantLinks() throws Exception {
        driver.get(new URI(driver.getCurrentUrl()).resolve("apply.xhtml").toString());
        authTest.waitUntil(driver, By.id("applyForm"));

        driver.get(new URI(driver.getCurrentUrl()).resolve("index.xhtml").toString());
        authTest.waitUntil(driver, By.className("index-content"));

        driver.get(new URI(driver.getCurrentUrl()).resolve("applications.xhtml").toString());
        driver.findElement(By.xpath("//h1[contains(.,'403')]"));

        driver.get(new URI(driver.getCurrentUrl()).resolve("application_overview.xhtml").toString());
        driver.findElement(By.xpath("//h1[contains(.,'403')]"));
    }

    @Test
    public void testApplyPage() throws Exception {
        driver.get(new URI(driver.getCurrentUrl()).resolve("apply.xhtml").toString());

        applyTest.addCompetence(driver, true, true);
        int count_table1 = countRowsIntable("applyForm:competenceTable");
        applyTest.addCompetence(driver, false, true);
        int count_table2 = countRowsIntable("applyForm:competenceTable");
        Assert.assertEquals(count_table2, count_table1);
        applyTest.addCompetence(driver, true, false);
        int count_table3 = countRowsIntable("applyForm:competenceTable");
        Assert.assertEquals(count_table3, ++count_table1);
        applyTest.addCompetence(driver, true, true);
        int count_table4 = countRowsIntable("applyForm:competenceTable");
        Assert.assertEquals(count_table4, ++count_table1);

        applyTest.addAvailablity(driver, "", "12-12-2017", By.id("applyForm:dateFromErrMsg"));
        applyTest.addAvailablity(driver, "12-12-2017", "", By.id("applyForm:dateToErrMsg"));
        applyTest.addAvailablity(driver, "12-3", "12-12-2017", By.id("applyForm:dateFromErrMsg"));
        applyTest.addAvailablity(driver, "12-12-2017", "illegal", By.id("applyForm:dateToErrMsg"));

        applyTest.addAvailablity(driver, "12-12-2017", "12-12-2018", By.id("applyForm:availabilityTable"));
        applyTest.addAvailablity(driver, "12-12-2018", "12-12-2019", By.id("applyForm:availabilityTable"));
        applyTest.addAvailablity(driver, avFrom, avTo, By.id("applyForm:availabilityTable"));
        int count_tableAv = countRowsIntable("applyForm:availabilityTable");
        Assert.assertEquals(count_tableAv, 4);

        WebElement appSubmitBtn = driver.findElement(By.id("applyForm:submitAppBtn"));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView();", appSubmitBtn);
        appSubmitBtn.click();
        DateFormat dateFormat = new SimpleDateFormat("d-MM-yyyy");
        Date date = new Date();
        submissionDate = dateFormat.format(date);

        applyTest.waitUntil(driver, By.xpath("//span[@data-notify='message']"));

    }

    private int countRowsIntable(String tableId) {
        WebElement table = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id(tableId)));
        List<WebElement> rows_table = table.findElements(By.tagName("tr"));
        return rows_table.size();
    }

    @Test
    public void testRecruiterLogin() throws Exception {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.
                        visibilityOfElementLocated(By.className("close"))).click();
        Thread.sleep(1000);
        testLogout();

        authTest.login(driver, "recruiter", "1234", By.id("user_menu"));
    }

    @Test
    public void testRecruiterLinks() throws Exception {

        driver.get(new URI(driver.getCurrentUrl()).resolve("apply.xhtml").toString());
        authTest.waitUntil(driver, By.xpath("//h1[contains(.,'403')]"));

        driver.get(new URI(driver.getCurrentUrl()).resolve("index.xhtml").toString());
        authTest.waitUntil(driver, By.className("index-content"));

        driver.get(new URI(driver.getCurrentUrl()).resolve("applications.xhtml").toString());
        authTest.waitUntil(driver, By.id("searchForm"));

        driver.get(new URI(driver.getCurrentUrl()).resolve("application_overview.xhtml").toString());
        Assert.assertEquals(driver.findElement(By.id("errorMsg")).getText(), "400 BAD REQUEST");
    }

    @Test
    public void testApplicationsPage() throws Exception {

        driver.get(new URI(driver.getCurrentUrl()).resolve("applications.xhtml").toString());
        authTest.waitUntil(driver, By.id("searchForm"));
        String tbId = "appListingTable";
        int tbCount1 = countRowsIntable(tbId);

        appTest.searchForApp(driver, "12", avFrom, avTo, true, firstname);
        appTest.waitUntil(driver, By.id("searchForm:dateErrMsg"));

        appTest.searchForApp(driver, submissionDate, "12", avTo, true, firstname);
        appTest.waitUntil(driver, By.id("searchForm:dateFromErrMsg"));

        appTest.searchForApp(driver, submissionDate, avFrom, "12", true, firstname);
        appTest.waitUntil(driver, By.id("searchForm:dateToErrMsg"));

        int tbCount2 = countRowsIntable(tbId);
        Assert.assertEquals(tbCount2, tbCount1);

        appTest.searchForApp(driver, "", "", "", false, "");
        int tbCount3 = countRowsIntable(tbId);
        Assert.assertEquals(tbCount3, tbCount1);

        appTest.searchForApp(driver, submissionDate, avFrom, avTo, false, firstname);
        int tbCount4 = countRowsIntable(tbId);

        Assert.assertEquals(tbCount4, 2);

        appTest.waitUntil(driver, By.linkText("View Application")).click();
    }

    @Test
    public void testAppOverviewPage() throws Exception {
        appTest.waitUntil(driver, By.xpath("//h1[contains(.,'Job Application Overview')]"));
        Assert.assertEquals(driver.findElement(By.id("jsfRepeat:0:regDate")).getText(), submissionDate);
        Assert.assertEquals(driver.findElement(By.id("firstName")).getText(), firstname);
        Assert.assertEquals(driver.findElement(By.id("surname")).getText(), surname);
        Assert.assertEquals(driver.findElement(By.id("email")).getText(), email);
        Assert.assertEquals(driver.findElement(By.id("ssn")).getText(), ssn);
        
        Random rand = new Random();
        for(int i = 0; i < 10; i++){
            int chooseBtn = rand.nextInt(2);
            if(chooseBtn == 0){
                driver.findElement(By.id("jsfRepeat:0:changeStatusForm:AcceptBtn")).click();
                Assert.assertEquals(appTest.waitUntil(driver, By.id("currentStatus")).getText(), "Accepted");
            } else {
                driver.findElement(By.id("jsfRepeat:0:changeStatusForm:RejectBtn")).click();
                Assert.assertEquals(appTest.waitUntil(driver, By.id("currentStatus")).getText(), "Rejected");
            }
        }
                
    }
}
