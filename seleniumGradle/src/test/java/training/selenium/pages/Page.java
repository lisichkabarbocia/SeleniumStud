package training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Page {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    public HomePage goHome() {
        driver.findElement(By.xpath("//nav[@id = 'site-menu']//i[@title='Home']")).click();
        wait.until(ExpectedConditions.urlToBe("http://localhost/litecart/en/"));
        return new HomePage(driver);

    }

    public void goToCheckout() {
        WebElement cart = driver.findElement(By.id("cart-wrapper"));
        cart.findElement(By.xpath(".//a[contains(text(), 'Checkout')]")).click();

    }

    public void clickButton(String visualName) {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit' and contains(text(),'"+visualName+"')]")));
        //WebElement button = wait.until(presenceOfElementLocated(By.xpath("//button[@type='submit' and contains(text(),'"+visualName+"')]")));
        button.click();
        System.out.println("Нажата кнопка " +visualName+ "!");

    }

    public void chooseSelectFieldItem(String selectFieldName, String option) {
        WebElement selectField = wait.until(presenceOfElementLocated(By.xpath("//td[strong = \""+selectFieldName+"\"]/select")));
        Select select = new Select(driver.findElement(By.xpath("//td[strong = \""+selectFieldName+"\"]/select")));
        if (option == null) {select.selectByIndex(1);}
        else {select.selectByVisibleText(option);
            wait.until(attributeToBe(select.getFirstSelectedOption(), "text", option));}

    }

    public void chooseSelectFieldItem(String selectFieldName) {
        chooseSelectFieldItem(selectFieldName, null);

    }

    public void clickRadioButton(String field, String option) {
        WebElement zone = wait.until(presenceOfElementLocated(By.xpath("//td[strong = \"" +field+"\"]")));
        WebElement elem = zone.findElement(By.xpath(".//label[contains(text(), '" +option+ "')]//input[@type = 'radio']"));
        elem.click();

    }

    public void clickCheckBox(String field, String option) {
        WebElement zone = wait.until(presenceOfElementLocated(By.xpath("//td[strong = \"" +field+"\"]")));
        WebElement elem = zone.findElement(By.xpath(".//tr[./td[contains(text(), '" +option+ "')]]//input[@type = 'checkbox']"));
        elem.click();

    }

    public void clickCheckBox(WebElement field) {
        wait.until(elementToBeClickable(field));
        field.click();

    }

    public void fillTextField(String textFieldName, String text) {
        WebElement textField = wait.until(presenceOfElementLocated(By.xpath("//td[strong = \""+textFieldName+"\"]//input[@type='text']")));
        textField.sendKeys(text);

    }

    public void fillTextField(WebElement field, String text) {
        //wait.until(visibilityOfAllElements(field));
        field.clear();
        field.sendKeys(text);

    }

    public void fillDateField(String dateFieldName, String date) {
        WebElement dateField = wait.until(presenceOfElementLocated(By.xpath("//td[strong = \""+dateFieldName+"\"]//input[@type='date']")));
        dateField.sendKeys(date);

    }

    public void fillNumberField(String textFieldName, String number) {
        WebElement textField = wait.until(presenceOfElementLocated(By.xpath("//td[strong = \""+textFieldName+"\"]//input[@type='number']")));
        textField.clear();
        textField.sendKeys(number);

    }


    public void fillTextArea(String textFieldName, String text) {
        WebElement textArea = wait.until(presenceOfElementLocated(By.xpath("//td[strong = \""+textFieldName+"\"]//div[@class='trumbowyg-editor']")));
        textArea.clear();
        textArea.sendKeys(text);

    }

    public void checkCartQuantity(Integer expected) {
        WebElement cart = driver.findElement(By.id("cart-wrapper"));
        wait.until(presenceOfNestedElementLocatedBy(cart, By.xpath(".//span[@class='quantity' and text()='"+expected+"']")));

    }
}
