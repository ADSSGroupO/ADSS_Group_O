package Suppliers;

public class DiscountByPercentage extends Discount {

    // description of class: this discount is represented in percentage, meaning its value is the percentage
    // that should be reduced from the price. condition is minimal number of products needed in order to be eligible for discount
    public DiscountByPercentage(double val, double conditionForDiscount) {
        super(val, conditionForDiscount);
    }

    // function that takes in the price of the order before discount, applies the discount and returns the final price
    public double applyDiscount(double price) {
        return (price - price*value/100);
    }

    // function that checks if an order is eligible for discount
    public boolean isEligibleForDiscount(int numOfProducts, double totalPrice) {
        return (numOfProducts >= condition);
    }
}
