/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.concurrent.TimeUnit;
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
public class RecruitmentClientTest {
    
    @Test
    public void testSimple() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new ChromeDriver();
        
        
        // And now use this to visit NetBeans
        driver.get("http://localhost:8080/RecruitmentClient/");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.netbeans.org");

        // Check the title of the page
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                
                d.findElement(By.linkText("Login/Register")).click();
                d.findElement(By.id("login-form:lg_username")).clear();
                d.findElement(By.id("login-form:lg_username")).sendKeys("emil");
                d.findElement(By.id("login-form:lg_password")).sendKeys("1234");
                d.findElement(By.className("login-button")).click();
                return d.findElement(By.id("user_menu")).isDisplayed();
            }
        });
        
       
        //Close the browser
        driver.quit();
    }
    
}
