package Suppliers;

public class Order_Details_By_Product {

    // description of class: this class includes detailed information of a single product/multiple products of same type.
    // it has the product code & name, the amount ordered, the list price (before discounts), the discount given, and the final price.
    private int product_code; // the serial number of the product
    private String product_name; // product's name
    private int amount; // amount ordered
    private double list_price; // the base price of a unit
    private double discount; // discount given
    private double final_price; // the final price of the unit(s) of this product

    public Order_Details_By_Product(int code, String name, int amountofitems, double price, double discountgiven, double totalprice) { // constructor
        product_code = code;
        product_name = name;
        amount = amountofitems;
        list_price = price;
        discount = discountgiven;
        final_price = totalprice;
    }

    // getters for the details
    public int getProductCode() {return product_code;}
    public String getProductName() {return product_name;}
    public int getAmount() {return amount;}
    public double getListPrice() {return list_price;}
    public double getDiscountGiven() {return discount;}
    public double getFinalPrice() {return final_price;}

}
