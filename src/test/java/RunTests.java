

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Test Class used to initialize webdriver and test all cases
 */
public class RunTests {

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
    String avFrom = "03/15/19";
    String avTo = "03/15/20";

    /**
     * Initialize webdriver and applicant credentials
     *
     * @param browser
     * @throws Exception
     */
    @BeforeTest
    @Parameters("browser")
    public void setUp(String browser) throws Exception {

        switch (browser) {
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
        ssn = String.valueOf(Long.parseLong(RandomStringUtils.random(15, false, true)));
        email = RandomStringUtils.random(5, useLetters, useNumbers);
        email = email + "@" + email + ".com";
        username = RandomStringUtils.random(length, useLetters, useNumbers);
        password = RandomStringUtils.random(length, useLetters, true);

    }

    /**
     * Close webdriver when all tests have finished
     *
     * @throws Exception
     */
    @AfterTest
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Test by redirecting to register link
     *
     * @throws Exception if test fails
     */
    @Test
    public void testRegisterLink() throws Exception {
        regTest.testRegisterLink(driver);
    }

    /**
     * Test register page by providing different register parameters and checks
     * the result
     *
     * @throws Exception if test fails
     */
    @Test
    public void testRegister() throws Exception {
        regTest.testRegister(driver, firstname, surname, email, username, password, password, ssn);
    }

    /**
     * Test logout functionality dependent of login test
     *
     * @throws Exception if test fails
     */
    @Test
    public void testLogout() throws Exception {
        loginTest.testLogout(driver);
    }

    /**
     * Test the login function by inserting different user credentials and
     * checking the result
     *
     * @throws Exception if test fails
     */
    @Test
    public void testLogin() throws Exception {
        loginTest.testLogin(driver, username, password);
    }

    /**
     * Testing different links when logged in as applicant
     *
     * @throws Exception If test fails
     */
    @Test
    public void testParticipantLinks() throws Exception {
        applTest.testParticipantLinks(driver);
    }

    /**
     * Test everything in the apply page by providing with wrong and correct
     * input parameters
     *
     * @throws Exception If test fails
     */
    @Test
    public void testApplyPage() throws Exception {
        submissionDate = applTest.testApplyPage(driver, avFrom, avTo, password);
    }

    /**
     * Special logout only for participant
     *
     * @throws Exception if test fails
     */
    @Test
    public void testParticipantLogout() throws Exception {
        loginTest.testParticipantLogout(driver);
    }

    /**
     * Test by doing a login with recruiter credentials
     *
     * @param recUserName provided username from testng.xml
     * @param recUserPassword provided password from testng.xml
     * @throws Exception if the test fails
     */
    @Test
    @Parameters({"recUserName", "recUserPassword"})
    public void testRecruiterLogin(String recUserName, String recUserPassword) throws Exception {
        loginTest.testRecruiterLogin(driver, recUserName, recUserPassword);
    }

    /**
     * Test by doing a login with recruiter credentials
     *
     * @throws Exception if the test fails
     */
    @Test
    public void testRecruiterLinks() throws Exception {
        recTest.testRecruiterLinks(driver);
    }

    /**
     * Test application search page with different search parameters
     *
     * @throws Exception if test fails
     */
    @Test
    public void testApplicationsPage() throws Exception {
        recTest.testApplicationsPage(driver, firstname, submissionDate, avFrom, avTo);
    }

    /**
     * Test application search page with different search parameters
     *
     * @param recUserPassword provided recruiter password from testng.xml
     * @throws Exception if test fails
     */
    @Test
    @Parameters("recUserPassword")
    public void testAppOverviewPage(String recUserPassword) throws Exception {
        recTest.testAppOverviewPage(driver, firstname, surname, email, ssn, submissionDate, recUserPassword);
    }
}
