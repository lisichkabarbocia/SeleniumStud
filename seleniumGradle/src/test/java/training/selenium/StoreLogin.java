package training.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class StoreLogin {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static WebElement menuBlock;
    private static Integer menuItemsCount;
    private static Integer menuItemsChildCount;


    @BeforeAll
    public static void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void myFirstTest() {

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.className("fa-sign-out")).click();

    }

    @Test
    public void browseMenu() {

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        menuBlock = driver.findElement(By.id("box-apps-menu"));
        menuItemsCount = menuBlock.findElements(By.cssSelector("li#app-")).size();
        for (int i = 1; i <= menuItemsCount; i++) {
            menuBlock = driver.findElement(By.id("box-apps-menu"));
            menuBlock.findElement(By.xpath("./li["+i+"]")).click();
            menuItemsChildCount = driver.findElements(By.xpath("//ul[contains(@class, 'docs')]/li")).size();
            for (int j = 1; j <= menuItemsChildCount; j++) {
                driver.findElement(By.xpath("//ul[contains(@class, 'docs')]/li["+j+"]")).click();
                assert driver.findElements(By.cssSelector("h1")).size()>0 : "Заголовок страницы не найден!";
            }
        }


        driver.findElement(By.className("fa-sign-out")).click();

    }

    @AfterAll
    public static void stop() {
        driver.quit();
        driver = null;
    }
}
