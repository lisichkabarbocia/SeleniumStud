package training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class CheckoutPage extends Page {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    protected Integer orderProdQuantity;

    //на вход число - тогда сравнение с ним и проверка что равно;
    //на вход +-\d - тогда проверка что текущее число строк равно сохраненному orderProdQuantity +-\d
    public Boolean checkOrderProductQuantity(String stepNum) {
        Integer step;
        Integer staleQuantity;
        staleQuantity = orderProdQuantity;
        String move = "";
        if (stepNum.matches("([+-])(\\d+)")) {
            move = stepNum.substring(0, 1);
            step = Integer.parseInt(stepNum.replaceFirst("([+-])", ""));
        } else {
            step = Integer.parseInt(stepNum);
        }

        if (step == 0) {
            wait.until(presenceOfElementLocated(By.xpath("//div[@id='checkout-cart-wrapper']//em[contains(text(), 'There are no items in your cart.')]")));
            System.out.println("Корзина пуста!");
            return true;
        }
        if (move.equals("-")) {
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
        System.out.println(orderProdQuantity);
        return true;
    }

    public void cleanCart() {
        if (orderProdQuantity.equals(0)) {
            System.out.println("Корзина пуста!");
        } else {
            while (orderProdQuantity > 1) {
                System.out.println("В корзине " + orderProdQuantity + " наименований!");
                clickButton("Remove");
                //assert checkOrderProductQuantity("-1") : "Удаление не сработало! осталось " + orderProdQuantity + " наименований";
                checkOrderProductQuantity("-1");
            }
            clickButton("Remove");
            //assert checkOrderProductQuantity("0") : "Удаление не сработало! осталось " + orderProdQuantity + " наименований";
            checkOrderProductQuantity("0");
        }
    }

}
