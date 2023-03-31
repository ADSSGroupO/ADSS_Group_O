package Suppliers;

public class DiscountByPriceAmount extends Discount {

    // description of class: this discount is represented in money amount, meaning its value is the amount of money
    // that should be reduced from the price. condition is minimal total price needed in order to be eligible for discount
    public DiscountByPriceAmount(double val, double conditionForDiscount) { // constructor
        super(val, conditionForDiscount);
    }

    // function that takes in the price of the order before discount, applies the discount and returns the final price
    public double applyDiscount(double price) {
        return (price - value);
    }

    // function that checks if an order is eligible for discount
    public boolean isEligibleForDiscount(int numOfProducts, double totalPrice) {
        return (totalPrice >= condition);
    }
}
