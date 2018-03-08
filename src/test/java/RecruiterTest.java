
import java.net.URI;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test Class used to test recruiter functonality 
 */
public class RecruiterTest extends CommonMethods {
    
    /**
     * Test links by redirecting to them and checking if correct content is shown when logged in as recruiter 
     * @param driver provided webdriver to test in
     * @throws Exception if test fails
     */
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
    
    /**
     * Test application search page with different search parameters
     * @param driver provided webdriver to test in
     * @param firstname provided CORRECT firstname
     * @param submissionDate provided CORRECT submission date
     * @param avFrom provided CORRECT availability date from
     * @param avTo provided CORRECT availability date to
     * @throws Exception if test fails
     */
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

    /**
     * Checks if the application overview page has the correct content and later tests status change functionality
     * @param driver provided webdriver
     * @param firstname provided applicant firstname
     * @param surname provided applicant surname
     * @param email provided applicant email
     * @param ssn provided applicant ssn
     * @param submissionDate provided applicants application submission date
     * @param password provided recruiter password
     * @throws Exception if test fails
     */
    @Test
    public void testAppOverviewPage(WebDriver driver,
            String firstname,
            String surname,
            String email,
            String ssn,
            String submissionDate,
            String password) throws Exception {
        waitUntil(driver, By.xpath("//h1[contains(.,'Job Application Overview')]"));
        Assert.assertEquals(driver.findElement(By.id("jsfRepeat:0:regDate")).getText(), submissionDate);
        Assert.assertEquals(driver.findElement(By.id("firstName")).getText(), firstname);
        Assert.assertEquals(driver.findElement(By.id("surname")).getText(), surname);
        Assert.assertEquals(driver.findElement(By.id("email")).getText(), email);
        Assert.assertEquals(driver.findElement(By.id("ssn")).getText(), ssn);

        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            int chooseBtn = rand.nextInt(2);
            testStatusChange(driver, "", "AcceptBtn");
            waitUntil(driver, By.id("jsfRepeat:0:changeStatusForm:pwdErrorMsg"));
            testStatusChange(driver, "0", "RejectBtn");
            waitUntil(driver, By.xpath("//span[@data-notify='message']"));

            if (chooseBtn == 0) {
                testStatusChange(driver, password, "AcceptBtn");
                Assert.assertEquals(waitUntil(driver, By.id("currentStatus")).getText(), "Accepted");

            } else {
                testStatusChange(driver, password, "RejectBtn");
                Assert.assertEquals(waitUntil(driver, By.id("currentStatus")).getText(), "Rejected");
            }
        }

    }

    private void testStatusChange(WebDriver driver, String password, String btnId) throws InterruptedException {
        driver.findElement(By.id("jsfRepeat:0:changeStatusForm:password")).clear();
        driver.findElement(By.id("jsfRepeat:0:changeStatusForm:password")).sendKeys(password);
        Thread.sleep(500);
        driver.findElement(By.id("jsfRepeat:0:changeStatusForm:" + btnId)).click();

    }

    private void searchForApp(WebDriver driver,
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
}
