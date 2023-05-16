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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;


public class AddToCart {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private Integer orderProdQuantity;


    @Test
    public void cartScenario() {
        start("Chrome");
        driver.get("http://localhost/litecart/en/");

        for (int i = 1; i<11; i++){
            goHome();
            pickProduct();
            addProductToCart();
            checkCartQuantity(i);
        }


        goToCheckout();
        checkOrderProductQuantity();
        while (orderProdQuantity>1) {
            System.out.println("В корзине "+orderProdQuantity+" наименований!");
            clickButton("Remove");
            assert checkOrderProductQuantity("-1"): "Удаление не сработало! осталось " +orderProdQuantity+ " наименований";
        }

        clickButton("Remove");
        assert checkOrderProductQuantity("0"): "Удаление не сработало! осталось " +orderProdQuantity+ " наименований";


    }






    public void customerLogout() {

        WebElement loginForm = driver.findElement(By.id("box-account"));
        loginForm.findElement(By.linkText("Logout")).click();

    }

    public void adminLogin() {

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

    }

    public void clickButtonAddNewProduct() {

        driver.findElement(By.xpath("//a[@class='button' and contains(text(), ' Add New Product')]")).click();

    }

    public void clickButton(String visualName) {
        WebElement button = wait.until(presenceOfElementLocated(By.xpath("//button[@type='submit' and contains(text(),'"+visualName+"')]")));
        button.click();
        System.out.println("Нажата кнопка " +visualName+ "!");

    }

    public void searchCatalog(String query) {
        WebElement search = driver.findElement(By.xpath("//input[@type='search']"));
        search.clear();
        search.sendKeys("query" + Keys.ENTER);

    }
    public Integer searchResult() {
        WebElement catalogTable = driver.findElement(By.xpath("//form[@name='catalog_form']"));
        String result = catalogTable.findElement(By.xpath(".//tr[@class='footer']/td")).getText();
        result = result.replace("Products: ", "");
        return Integer.parseInt(result);

    }

    public void clickButtonSave() {

        driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();

    }

    public void goHome() {
        driver.findElement(By.xpath("//nav[@id = 'site-menu']//i[@title='Home']")).click();
        wait.until(ExpectedConditions.urlToBe("http://localhost/litecart/en/"));

    }

    public void moveTo(String menuItem) {
        WebElement menuBlock = driver.findElement(By.id("box-apps-menu"));
        menuBlock.findElement(By.xpath(".//span[@class='name' and contains(text(), '"+menuItem+"')]")).click();

    }

    public void pickProduct() {
        driver.findElement(By.xpath("//li[contains(@class, 'product')]")).click();

    }

    public void addProductToCart() {
        List<WebElement> selectList = driver.findElements(By.xpath("//form[@name='buy_now_form']//select"));
        if (selectList.size() > 0) {
            for (WebElement elem : selectList) {
                chooseSelectFieldItem(elem.findElement(By.xpath(".//../strong")).getText());

            }
        }
        clickButton("Add To Cart");
        //driver.findElement(By.xpath("//button[contains(text(), 'Add To Cart')]")).click();
    }

    //на вход число - тогда сравнение с ним и проверка что равно;
    //на вход +-\d - тогда проверка что текущее число строк равно сохраненному orderProdQuantity +-\d
    public Boolean checkOrderProductQuantity(String stepNum) {
        Integer step;
        Integer staleQuantity;
        staleQuantity = orderProdQuantity;
        String move="";
        if (stepNum.matches("([+-])(\\d+)")) {
            move = stepNum.substring(0, 1);
            step = Integer.parseInt(stepNum.replaceFirst("([+-])", ""));
        } else { step = Integer.parseInt(stepNum);}

        if (step==0) {
            wait.until(presenceOfElementLocated(By.xpath("//div[@id='checkout-cart-wrapper']//em[contains(text(), 'There are no items in your cart.')]")));
            System.out.println("Корзина пуста!");
            return true;
        }
       if  (move.equals("-")) {
            wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[h2[contains(text(), 'Order Summary')]]//td[@class='item']"), staleQuantity - step));
            orderProdQuantity = staleQuantity - step;
            return true;
        } else if (move.equals("+")) {
           wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[h2[contains(text(), 'Order Summary')]]//td[@class='item']"), staleQuantity + step));
           orderProdQuantity = staleQuantity + step;
           return true;
        }
        return (orderProdQuantity.equals(step));

    }


    public Boolean checkOrderProductQuantity() {
        WebElement orderSummary = wait.until(presenceOfElementLocated(By.xpath("//div[h2[contains(text(), 'Order Summary')]]")));
        orderProdQuantity = orderSummary.findElements(By.xpath(".//td[@class='item']")).size();
        return true;
//
    }



    public void checkCartQuantity(Integer expected) {
        WebElement cart = driver.findElement(By.id("cart-wrapper"));
        wait.until(presenceOfNestedElementLocatedBy(cart, By.xpath(".//span[@class='quantity' and text()='"+expected+"']")));
        //String quantity = cart.findElement(By.xpath(".//span[@class='quantity']")).getText();
        //assert quantity.equals(Integer.toString(expected)) : "Количество товаров в корзине отлично от " +expected+ "!";

    }

    public void goToCheckout() {
        WebElement cart = driver.findElement(By.id("cart-wrapper"));
        cart.findElement(By.xpath(".//a[contains(text(), 'Checkout')]")).click();

    }

    public void moveToTab(String tabName) {
        WebElement tab = driver.findElement((By.xpath("//div[@class='tabs']//a[.='"+tabName+"']")));
        wait.until(elementToBeClickable(tab)).click();
        wait.until(attributeToBe(By.xpath("//div[@class='tabs']//a[.='"+tabName+"']/.."), "class", "active" ));



    }

    public void uploadFile(String fieldName,String path){
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();
        driver.findElement(By.xpath("//td[strong = \""+fieldName+ "\"]//input[@type= 'file']")).sendKeys(absolutePath);
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

    public void fillTable(String columnNum, String rowNum, String text) {
//        Integer column = 0;
//        WebElement table = wait.until(presenceOfElementLocated(By.xpath("//table[.//th[contains(text(),\""+columnHeader+"\")]]")));
//        List<WebElement> rows = table.findElements(By.xpath(".//th"));
//        for (WebElement n : rows) {
//            String header = n.getText();
//            System.out.println(header);
//            if (header.equals(columnHeader)) {
//                column = rows.indexOf(n)+1;
//            }
//        }
        int row = Integer.parseInt(rowNum)+1;
        WebElement table = wait.until(presenceOfElementLocated(By.xpath("//table[.//th[contains(text(),'Price')]]")));
        WebElement field = table.findElement(By.xpath(".//tr["+row+"]//td["+columnNum+"]//input"));
        String fieldType = field.getDomAttribute("type");
            switch (fieldType){
                case "checkbox":
                    clickCheckBox(field);
                    break;
                default:
                    fillTextField(field, text);
            }

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
        return "email" + generateRandomNumber() + "@gmail.com";

    }

    public static String generateRandomNumber() {
        String random = new SimpleDateFormat("dd.MM.yy.HH.mm.ss").format(new java.util.Date());
        random = random.replace("." , "");
        random = random.replace("0", "");
        return random;

    }





    public void login() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

    }
}
