package training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage extends Page{

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void addProductToCart() {
        List<WebElement> selectList = driver.findElements(By.xpath("//form[@name='buy_now_form']//select"));
        if (selectList.size() > 0) {
            for (WebElement elem : selectList) {
                chooseSelectFieldItem(elem.findElement(By.xpath(".//../strong")).getText());

            }
        }
        clickButton("Add To Cart");
    }
}
