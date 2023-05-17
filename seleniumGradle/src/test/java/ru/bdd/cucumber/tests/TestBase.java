package ru.bdd.cucumber.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import training.selenium.pages.CheckoutPage;
import training.selenium.pages.HomePage;
import training.selenium.pages.ProductPage;

import java.time.Duration;

public class TestBase {

    public WebDriver driver;
    public WebDriverWait wait;

    public HomePage homePage;
    public ProductPage productPage;
    public CheckoutPage checkoutPage;



    public void start(String browser) {
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
        homePage = new HomePage(driver);
        checkoutPage = new CheckoutPage(driver);
        productPage = new ProductPage(driver);


    }

    public void stop() {
        driver.quit();
        driver = null;
    }
}
