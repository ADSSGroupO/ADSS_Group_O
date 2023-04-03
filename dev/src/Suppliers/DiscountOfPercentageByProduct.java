package Suppliers;

public class DiscountOfPercentageByProduct extends DiscountByProduct {

    // description of class: an order will be eligible for discount when ordering more units of same product than minimal amount of units.
    // class is represented in percentage, meaning its value is the percentage of money reduced from original price
    public DiscountOfPercentageByProduct(double val, int products) {
        super(val, products);
    }

    // function that checks if an order is eligible for discount
    public boolean isEligibleForDiscount(int products, double totalPrice) {
        return (products >= numOfProducts);
    }

    // function that calculates the amount of money reduced in discount
    public double calculateDiscount(double price) {return (price*value/100);}
}
