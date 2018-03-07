
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
public class RecruiterTest extends CommonMethods {

    @Test
    public void testRecruiterLinks(WebDriver driver) throws Exception {

        driver.get(new URI(driver.getCurrentUrl()).resolve("apply.xhtml").toString());
        waitUntil(driver, By.xpath("//h1[contains(.,'403')]"));

        driver.get(new URI(driver.getCurrentUrl()).resolve("index.xhtml").toString());
        waitUntil(driver, By.className("index-content"));

        driver.get(new URI(driver.getCurrentUrl()).resolve("applications.xhtml").toString());
        waitUntil(driver, By.id("searchForm"));

        driver.get(new URI(driver.getCurrentUrl()).resolve("application_overview.xhtml").toString());
        Assert.assertEquals(driver.findElement(By.tagName("h1")).getText(), "ERROR 406");
    }

    @Test
    public void testApplicationsPage(WebDriver driver,
            String firstname,
            String submissionDate,
            String avFrom,
            String avTo) throws Exception {

        driver.get(new URI(driver.getCurrentUrl()).resolve("applications.xhtml").toString());
        waitUntil(driver, By.id("searchForm"));
        String tbId = "appListingTable";
        int tbCount1 = countRowsIntable(driver, tbId);

        searchForApp(driver, "12", avFrom, avTo, true, firstname);
        waitUntil(driver, By.id("searchForm:dateErrMsg"));

        searchForApp(driver, submissionDate, "12", avTo, true, firstname);
        waitUntil(driver, By.id("searchForm:dateFromErrMsg"));

        searchForApp(driver, submissionDate, avFrom, "12", true, firstname);
        waitUntil(driver, By.id("searchForm:dateToErrMsg"));

        int tbCount2 = countRowsIntable(driver, tbId);
        Assert.assertEquals(tbCount2, tbCount1);

        searchForApp(driver, submissionDate, avFrom, avTo, false, firstname);
        int tbCount4 = countRowsIntable(driver, tbId);

        Assert.assertEquals(tbCount4, 2);

        waitUntil(driver, By.linkText("View Application")).click();
    }

    @Test
    public void testAppOverviewPage(WebDriver driver,
            String firstname,
            String surname,
            String email,
            String ssn,
            String submissionDate) throws Exception {
        waitUntil(driver, By.xpath("//h1[contains(.,'Job Application Overview')]"));
        Assert.assertEquals(driver.findElement(By.id("jsfRepeat:0:regDate")).getText(), submissionDate);
        Assert.assertEquals(driver.findElement(By.id("firstName")).getText(), firstname);
        Assert.assertEquals(driver.findElement(By.id("surname")).getText(), surname);
        Assert.assertEquals(driver.findElement(By.id("email")).getText(), email);
        Assert.assertEquals(driver.findElement(By.id("ssn")).getText(), ssn);

        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int chooseBtn = rand.nextInt(2);
            if (chooseBtn == 0) {
                driver.findElement(By.id("jsfRepeat:0:changeStatusForm:AcceptBtn")).click();
                Thread.sleep(500);

                Assert.assertEquals(waitUntil(driver, By.id("currentStatus")).getText(), "Accepted");
            } else {
                driver.findElement(By.id("jsfRepeat:0:changeStatusForm:RejectBtn")).click();
                Thread.sleep(500);
                Assert.assertEquals(waitUntil(driver, By.id("currentStatus")).getText(), "Rejected");
            }
        }

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
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

    }
        public String parseDateAfterLocale(String date) {
          
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH);
        Date tempDate = null;
        try {
            tempDate = df.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(RecruiterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(df.format(tempDate));
        return df.format(tempDate);
    }
}
