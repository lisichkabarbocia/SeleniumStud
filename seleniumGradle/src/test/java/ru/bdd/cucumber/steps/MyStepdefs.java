package ru.bdd.cucumber.steps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ru.bdd.cucumber.tests.TestBase;

public class MyStepdefs {


    private static TestBase testBase = new TestBase();

    @When("customer enters shop")
    public void customerEntersShop() {
        testBase.start("Chrome");
        testBase.homePage.open();

    }

    @And("picks product")
    public void picksProduct() {
        testBase.homePage.pickProduct();
    }

    @And("adds product to cart")
    public void addsProductToCart() {
        testBase.productPage.addProductToCart();
    }

    @Then("cart amount is {int}")
    public void cartAmountIs(int num) {
        testBase.productPage.checkCartQuantity(num);
    }

    @Then("goes to homepage")
    public void goesToHomepage() {
        testBase.productPage.goHome();
    }

    @Then("goes to checkout")
    public void goesToCheckout() {
        testBase.productPage.goToCheckout();
    }

    @And("cleans the cart")
    public void cleansTheCart() {
        testBase.checkoutPage.cleanCart();
    }

    @And("checks the order")
    public Boolean checksTheOrder() {
        testBase.checkoutPage.checkOrderProductQuantity();
        return true;
    }

    @AfterAll
    public static void stop(){
        testBase.stop();
    }
}
