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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;


public class AddNewProduct {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static String email;
    private static String password = "qwerty1";
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(15000)/*seconds*/);

    @Test
    public void addNewProduct() {
        String PsyDuck = "PsyDuck v" + generateRandomNumber();
        start("Chrome");
        adminLogin();
        moveTo("Catalog");
        clickButtonAddNewProduct();


        clickRadioButton("Status", "Enabled");
        fillTextField("Name", PsyDuck);
        fillTextField("Code", generateRandomNumber());
        clickCheckBox("Categories", "Rubber Ducks");
        chooseSelectFieldItem("Default Category", "Root");
        clickCheckBox("Product Groups", "Unisex");
        fillNumberField("Quantity", "100");
        chooseSelectFieldItem("Quantity Unit", "pcs");
        chooseSelectFieldItem("Delivery Status", "3-5 days");
        uploadFile("Upload Images", "src/test/resources/psyduck.png");
        fillDateField("Date Valid From", "11.06.2023");
        fillDateField("Date Valid To", "11/06/2025");



        moveToTab("Information");


        chooseSelectFieldItem("Manufacturer", "ACME Corp.");
        chooseSelectFieldItem("Supplier", "-- Select --");
        fillTextField("Keywords", "duck, pockemon, psycho" );
        fillTextField("Short Description", "Psyduck (Japanese: コダック Koduck) is a Water-type Pokémon introduced in Generation I.");
        fillTextArea("Description", "Psyduck is a yellow Pokémon resembling a duck or a bipedal platypus. On top of its head are three thick strands of black hair, and it has a wide, flat cream-colored beak. Psyduck's eyes seem vacant and have tiny pupils. Its legs and tail are stubby, and it has cream-colored webbed feet. There are three claws on each of its hands.");
        fillTextField("Head Title", "Psyduck" );
        fillTextField("Meta Description", "Meta Duck Description");


        moveToTab("Prices");

        chooseSelectFieldItem("Purchase Price", "US Dollars");
        fillNumberField("Purchase Price", "30.65");
        chooseSelectFieldItem("Tax Class","-- Select --");
        fillTable("2", "1", "35");
        fillTable("2", "2", "45");

        clickButtonSave();

        moveTo("Catalog");

        searchCatalog(PsyDuck);


        assert searchResult()==0 : "Создание продукта неуспешно!";


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

    public void moveTo(String menuItem) {
        WebElement menuBlock = driver.findElement(By.id("box-apps-menu"));
        menuBlock.findElement(By.xpath(".//span[@class='name' and contains(text(), '"+menuItem+"')]")).click();

    }

    public void moveToTab(String tabName) {
        WebElement tab = driver.findElement((By.xpath("//div[@class='tabs']//a[.='"+tabName+"']")));
        wait.until(elementToBeClickable(tab)).click();
        wait.until(attributeToBe(By.xpath("//div[@class='tabs']//a[.='"+tabName+"']/.."), "class", "active" ));



    }

    public void uploadFile(String fieldName,String path){
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();
        //System.out.println(absolutePath);
        driver.findElement(By.xpath("//td[strong = \""+fieldName+ "\"]//input[@type= 'file']")).sendKeys(absolutePath);
    }

    public void chooseSelectFieldItem(String selectFieldName, String option) {
        WebElement selectField = wait.until(presenceOfElementLocated(By.xpath("//td[strong = \""+selectFieldName+"\"]/select")));
        Select select = new Select(driver.findElement(By.xpath("//td[strong = \""+selectFieldName+"\"]/select")));
        select.selectByVisibleText(option);
        wait.until(attributeToBe(select.getFirstSelectedOption(), "text", option));

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
