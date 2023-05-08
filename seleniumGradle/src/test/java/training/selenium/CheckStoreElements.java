package training.selenium;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckStoreElements {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static WebElement menuBlock;
    private static WebElement productList;
    private static WebElement countriesList;
    private static WebElement product;
    private static WebElement country;
    private static Integer menuItemsCount;
    private static Integer menuItemsChildCount;



    @ParameterizedTest
    @ValueSource(strings = {"Chrome", "Firefox", "IE"})
    public void checkItemPage(String browser) {
        start(browser);
        ArrayList<String> productParams;
        ArrayList<String> productCardParams;
        driver.get("http://localhost/litecart/en/");

        WebElement campaignsBlock = driver.findElement(By.id("box-campaigns"));
        WebElement product = campaignsBlock.findElement(By.xpath(".//li[contains(@class, 'product')]"));

        productParams = getProductParams(product);

        System.out.println(productParams);

        product.click();

        WebElement productBlock = driver.findElement(By.id("box-product"));
        productCardParams = getProductParams(productBlock);

        System.out.println(productCardParams);

        assert productCardParams.equals(productParams) : "Условия не выполнены!";

        stop();

    }




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

    public ArrayList<String> getProductParams(WebElement product){
        ArrayList<String> productParams = new ArrayList<>();
        try {
            productParams.add(product.findElement(By.className("name")).getText());
        } catch (NoSuchElementException nsee){
            productParams.add(product.findElement(By.className("title")).getText());
        }


        WebElement regularPrice = product.findElement(By.className("regular-price"));
        WebElement campaignPrice = product.findElement(By.className("campaign-price"));

        productParams.add(regularPrice.getText());


        productParams.add(whatColorIsThis(regularPrice.getCssValue("text-decoration")));
        productParams.add(isLineThrough(regularPrice.getCssValue("text-decoration")));



        productParams.add(campaignPrice.getText());

        productParams.add(whatColorIsThis(campaignPrice.getCssValue("text-decoration")));
        productParams.add(isLineThrough(campaignPrice.getCssValue("text-decoration")));

        productParams.add(campaignPriceFontBigger(regularPrice, campaignPrice));

        return productParams;
    }

    public String isLineThrough(String textDecoration) {
        if (textDecoration.contains("line-through")) {
            return "line-through";
        }
        else {
            return "none";
        }
    }

    public String campaignPriceFontBigger(WebElement regularPrice, WebElement campaignPrice) {
        String regularSize = regularPrice.getCssValue("font-size").replace("px", "");
        String campaignSize = campaignPrice.getCssValue("font-size").replace("px", "");
        if (Double.parseDouble(regularSize) > Double.parseDouble(campaignSize)) {return "Fault! regular " +regularSize+" > campaign " +campaignSize;}
        else {return "True! campaign font price size > regular font price size";}
    }



    public String whatColorIsThis(String rgba) {
        System.out.println(rgba);
        String regexPattern = "\\d+";

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(rgba);

        ArrayList<String> colours = new ArrayList<>();

        while (matcher.find()) {
            colours.add(matcher.group());
        }

        String R = colours.get(0);
        String G = colours.get(1);
        String B = colours.get(2);

        if (R.equals(G)&&G.equals(B)) {
            return "grey";
        }
        else if (G.equals(B)&&B.equals("0")) {
            return "red";
        } else {
            return "some color";
        }

    }



    public void login() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

    }
}
