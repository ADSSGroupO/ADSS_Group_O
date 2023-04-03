package Suppliers;

public class DiscountOfPercentageByOrder extends DiscountByOrder {
    int numOfProducts;

    // description of class: this class represents a discount that should be applied to order by total amount of units in ordert,
    // meaning its number of products should be at least the minimal amount

    public DiscountOfPercentageByOrder(double val, int products) {
        super(val);
        numOfProducts = products;
    }

    // function that checks if an order is eligible for discount
    public boolean isEligibleForDiscount(int products, double totalPrice) {
        return (products >= numOfProducts);
    }

    // function that calculates the amount of money reduced in discount
    public double calculateDiscount(double price) {return (value*price/100);}

    // function that returns the number of products required for this discount
    public int getNumOfProducts() {return numOfProducts;}
}
