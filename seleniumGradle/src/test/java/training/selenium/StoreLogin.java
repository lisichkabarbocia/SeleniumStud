package training.selenium;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class StoreLogin {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static WebElement menuBlock;
    private static WebElement productList;
    private static WebElement countriesList;
    private static WebElement product;
    private static WebElement country;
    private static Integer menuItemsCount;
    private static Integer menuItemsChildCount;


    @BeforeAll
    public static void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

   @Test
    public void checkZoneSort() {

        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        login();

        int countryNum = driver.findElements(By.xpath("//tr[contains(@class, 'row')]/td[3]/a")).size();


        for (int i = 0; i< countryNum; i++) {
            driver.findElements(By.xpath("//tr[contains(@class, 'row')]/td[3]/a")).get(i).click();
            WebElement dataTable = driver.findElement(By.className("dataTable"));
            List<WebElement> multZones = dataTable.findElements(By.xpath(".//select[contains(@name, 'zone_code')]/option[contains(@selected, 'selected')]"));
            String prevCountry = multZones.get(0).getText();
            System.out.println(prevCountry);
            multZones.remove(0);

            for (WebElement n : multZones) {
                String countryName = n.getText();
                System.out.println(countryName);
                assert prevCountry.compareTo(countryName) < 0 : "Алфавитный порядок не соблюден! " + prevCountry + " перед " + countryName;
                prevCountry = countryName;
                }
                driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");

            }

            driver.findElement(By.className("fa-sign-out")).click();

    }



    @Test
    public void browseCountries() {
        String countryName;
        ArrayList<String> multZones = new ArrayList<>();
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        login();

        menuItemsCount = driver.findElements(By.className("row")).size();
        List<WebElement> countryList = driver.findElements(By.xpath("//tr[contains(@class, 'row')]/td[5]/a"));
        String prevCountry = countryList.get(0).getText();
        countryList.remove(0);


        for (WebElement i : countryList) {
            countryName = i.getText();
            System.out.println(countryName);
            assert prevCountry.compareTo(countryName)<0 : "Алфавитный порядок не соблюден! "+prevCountry+" перед " +countryName;
            if ( Integer.parseInt(i.findElement(By.xpath(".//../../td[6]")).getText())>0 ){
                multZones.add(countryName);
            }
            prevCountry = countryName;

        }

        for (String n : multZones ) {
            System.out.println("*************************" +n);
            driver.findElement(By.linkText(n)).click();
            WebElement tableZones = driver.findElement(By.xpath("//table[contains(@id, 'table-zones')]"));
            List<WebElement> zoneList = tableZones.findElements(By.xpath(".//td[.//input[contains(@name, 'zones') and contains(@name, 'name')]]"));
            prevCountry = zoneList.get(0).getText();
            System.out.println(prevCountry);
            zoneList.remove(0);
            for (WebElement t : zoneList){
                countryName = t.getText();
                System.out.println(countryName);
                assert prevCountry.compareTo(countryName)<0 : "Алфавитный порядок не соблюден! "+prevCountry+" перед " +countryName;
                prevCountry = countryName;
            }
            driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        }

        driver.findElement(By.className("fa-sign-out")).click();

    }


    @Test
    public void checkLabels() {

        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.className("category-1")).click();
        productList = driver.findElement(By.className("products"));
        int size = productList.findElements(By.className("product")).size();

        for (int i = 1; i <= size; i++) {
            product = productList.findElement(By.xpath("./li["+i+"]"));
            assert product.findElements(By.xpath(".//div[contains(@class,'sticker')]")).size()==1 : "Стикер у товара " + product.getText() + " настроен неверно!";
        }
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




    public void login() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

    }
}
