
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Evan
 */
public class AuthenticationTest {

    public void login(WebDriver driver, String username, String password, By by) {

        (new WebDriverWait(driver, 10)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
            
            WebElement usernameElem = d.findElement(By.id("login-form:lg_username"));
            usernameElem.clear();
            usernameElem.sendKeys(username);
            d.findElement(By.id("login-form:lg_password")).sendKeys(password);
            d.findElement(By.className("login-button")).click();
            return d.findElement(by).isDisplayed();
        });
    }
    
    public void register(WebDriver driver, 
            String firstname, 
            String Surname,
            String email,
            String username,
            String password,
            String ssn, 
            By by) {

        (new WebDriverWait(driver, 10)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
            
            WebElement usernameElem = d.findElement(By.id("login-form:lg_username"));
            WebElement l = d.findElement(By.id("login-form:lg_username"));
            usernameElem.clear();
            usernameElem.sendKeys(username);
            d.findElement(By.id("login-form:lg_password")).sendKeys(password);
            d.findElement(By.className("login-button")).click();
            return d.findElement(by).isDisplayed();
        });
    }
}
