package Suppliers;

public class DiscountByProductsAmount extends Discount {

    // description of class: this discount is represented in money amount, meaning its value is the amount of money
    // that should be reduced from the price. condition is minimal number of products needed in order to be eligible for discount
    public DiscountByProductsAmount(double val, double conditionForDiscount) { // constructor
        super(val, conditionForDiscount);
    }

    // function that takes in the price of the order before discount, applies the discount and returns the final price
    public double applyDiscount(double price) {
        return (price - value);
    }

    // function that checks if an order is eligible for discount
    public boolean isEligibleForDiscount(int numOfProducts, double totalPrice) {
        return (numOfProducts >= condition);
    }
}
