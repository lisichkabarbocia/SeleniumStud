package training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends Page{

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get("http://localhost/litecart/en/");
        return this;
    }

    public void pickProduct() {
        driver.findElement(By.xpath("//li[contains(@class, 'product')]")).click();

    }
}
