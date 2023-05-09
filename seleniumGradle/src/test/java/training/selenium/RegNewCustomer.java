package training.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;

public class RegNewCustomer {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static String email;
    private static String password = "qwerty1";

    @Test
    public void regNewCustomers() {
        start("Chrome");
        driver.get("http://localhost/litecart/en/");

        email = generateNewEmail();
        System.out.println(email);

        WebElement loginForm = driver.findElement(By.name("login_form"));
        loginForm.findElement(By.linkText("New customers click here")).click();

        WebElement customerForm = driver.findElement(By.name("customer_form"));
        customerForm.findElement(By.name("firstname")).sendKeys("Firstname" + Keys.TAB);
        driver.switchTo().activeElement().sendKeys("Lastname" + Keys.TAB);
        driver.switchTo().activeElement().sendKeys("Address №1" + Keys.TAB + Keys.TAB);
        driver.switchTo().activeElement().sendKeys("12345" + Keys.TAB);
        driver.switchTo().activeElement().sendKeys("City" + Keys.TAB);
        driver.switchTo().activeElement().findElement(By.className("select2-selection__arrow")).click();
        driver.switchTo().activeElement().sendKeys("United States" + Keys.ENTER + Keys.TAB);
        driver.switchTo().activeElement().sendKeys(email + Keys.TAB);
        driver.switchTo().activeElement().sendKeys("+12345678" + Keys.TAB +Keys.TAB);
        driver.switchTo().activeElement().sendKeys(password + Keys.TAB);
        driver.switchTo().activeElement().sendKeys(password + Keys.TAB);
        driver.switchTo().activeElement().click();

        customerLogout();

        customerLogin();

        customerLogout();
    }






    public void customerLogout() {

        WebElement loginForm = driver.findElement(By.id("box-account"));
        loginForm.findElement(By.linkText("Logout")).click();

    }

    public void customerLogin() {

        WebElement loginForm = driver.findElement(By.name("login_form"));
        loginForm.findElement(By.name("email")).sendKeys(email);
        loginForm.findElement(By.name("password")).sendKeys(password);
        loginForm.findElement(By.name("login")).click();

    }



    @AfterAll
    public static void stop() {
        driver.quit();
        driver = null;
    }

    public static void start(String browser) {
        if (browser.equalsIgnoreCase("Chrome")) {
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
        else if (browser.equalsIgnoreCase("Firefox")) {
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
        else if (browser.equalsIgnoreCase("IE")) {
            driver = new InternetExplorerDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

    }

    public static String generateNewEmail() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        timeStamp = timeStamp.replace("." , "");
        return "email" + timeStamp + "@gmail.com";

    }





    public void login() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

    }
}
