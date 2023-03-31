package Suppliers;

public abstract class Discount {
    // description of class: this class is the parent class of discount classes. it's abstract and therefore a discount's type must be
    // one of the inheritors
    protected double value; // the value of the discount
    protected double condition; // the condition of getting the discount

    public Discount(double val, double conditionForDiscount) {value = val; condition = conditionForDiscount; } // constructor

    // an abstract method that should be implemented in inheritors. it takes in a price, and applies the discount to the given price
    public abstract double applyDiscount(double price);

    // an abstract method that should be implemented in inheritors. it takes in a number of products and price of order, and checks if order is eligible for discount
    // based on the condition for discount
    public abstract boolean isEligibleForDiscount(int numOfProducts, double totalPrice);

    // getters for class attributes
    public double getValue() {return value;}
    public double getCondition() {return condition;}

    // setters for class attributes
    public void setValue(double newval) {value=newval;}
    public void setCondition(double newcond) {condition=newcond;}
}
