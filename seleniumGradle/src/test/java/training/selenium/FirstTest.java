package training.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class FirstTest {

    private static WebDriver driver;
    private static WebDriverWait wait;


    @BeforeAll
    public static void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void myFirstTest() {

        driver.get("http://www.google.com");
        //driver.navigate().to("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        wait.until(visibilityOfElementLocated(By.name("btnK")));
        driver.findElement(By.name("btnK")).click();
        wait.until(titleIs("webdriver - Поиск в Google"));

    }

    @AfterAll
    public static void stop() {
        driver.quit();
        driver = null;
    }
}
