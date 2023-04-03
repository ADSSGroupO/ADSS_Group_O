package Suppliers;

import java.util.ArrayList;

public class Supply_Agreement {
    // description of class: this class represents a supply agreement with specified supplier. it has the base price of the product, its number in
    // the supplier's catalog, and a discounts list that includes all the bills of quantities made with the supplier regarding this product.
    private int product_code; // product serial number
    private double list_price; // list price (before discounts)
    private int max_amount;
    private int catalog_code; // code in the supplier's catalog
    private ArrayList<DiscountByProduct> discounts; // list of possible discount

    public Supply_Agreement(int code, double price, int catalog, int amount) { // constructor
        product_code = code;
        list_price = price;
        catalog_code = catalog;
        max_amount = amount;
        discounts = new ArrayList<DiscountByProduct>();
    }

    // function that adds new possible discount to discounts list
    public void addDiscount(DiscountByProduct discount) {
        discounts.add(discount);
    }

    // function that removes discount of a product, based on condition and value
    public void removeDiscount(int products, double value) {
        // iterate list of discounts. when finds, delete
        for (int i = 0; i < discounts.size(); i++) {
            if (discounts.get(i).getValue() == value && discounts.get(i).getNumOfProducts() == products)
                discounts.remove(i);
        }
    }

    // getters of attributes
    public int getProductCode() {return product_code;}
    public double getListPrice() {return list_price;}
    public int getCatalogCode() {return catalog_code;}
    public int getMaxAmount() {return max_amount;}
    public ArrayList<DiscountByProduct> getDiscounts() {return discounts;}

}
