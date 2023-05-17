Feature: Add to Cart and clear cart

  Scenario: Add to Cart
    When customer enters shop
    And picks product
    And adds product to cart
    Then cart amount is 1
#    Then goes to homepage
#    And picks product
#    And adds product to cart
#    Then cart amount is 2
#    Then goes to homepage
#    And picks product
#    And adds product to cart
#    Then cart amount is 3
#    Then goes to homepage
#    And picks product
#    And adds product to cart
#    Then cart amount is 4
#    Then goes to homepage
#    And picks product
#    And adds product to cart
#    Then cart amount is 5
#    Then goes to homepage
#    And picks product
#    And adds product to cart
#    Then cart amount is 6
    Then goes to checkout
    And checks the order
    And cleans the cart
    And goes to homepage
    And cart amount is 0
    
    