package Suppliers;

public class DiscountOfPriceByOrder extends DiscountByOrder {
    // description of class: this class represents a discount that should be applied to order by total price,
    // meaning its cost should be at least the minimal price
    double minimalPrice;

    public DiscountOfPriceByOrder(double val, double minPrice) {
        super(val);
        minimalPrice = minPrice;
    }

    // function that checks if an order is eligible for discount
    public boolean isEligibleForDiscount(int products, double totalPrice) {
        return (totalPrice >= minimalPrice);
    }

    // function that calculates the amount of money reduced in discount
    public double calculateDiscount(double price) {return value;}

    // function that returns the minimal price required for this discount
    public double getMinimalPrice() {return minimalPrice;}
}
