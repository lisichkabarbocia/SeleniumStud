package ru.bdd.cucumber.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AddToCartPO extends TestBase {

    @ParameterizedTest
    @ValueSource(strings = {"Chrome", "Firefox"})
    public void cartScenarioPO(String browser) {
        start(browser);

        homePage.open();

        homePage.pickProduct();
        productPage.addProductToCart();
        productPage.checkCartQuantity(1);
        productPage.goHome();

        homePage.pickProduct();
        productPage.addProductToCart();
        productPage.checkCartQuantity(2);
        productPage.goHome();


        homePage.pickProduct();
        productPage.addProductToCart();
        productPage.checkCartQuantity(3);


        productPage.goToCheckout();

        checkoutPage.checkOrderProductQuantity();
        checkoutPage.clickButton("Remove");
        checkoutPage.checkOrderProductQuantity("-1");
        checkoutPage.cleanCart();

        stop();

    }

}
