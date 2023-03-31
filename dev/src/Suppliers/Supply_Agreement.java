package Suppliers;

import java.util.ArrayList;

public class Supply_Agreement {
    // description of class: this class represents a supply agreement with specified supplier. it has the supplier's id, the base price of the product, its number in
    // the supplier's catalog, and a discounts list that includes all the bills of quantities made with the supplier regarding this product.
    private int supplier_id; // supplier's id
    private int product_code; // product serial number
    private double list_price; // list price (before discounts)
    private int catalog_code; // code in the supplier's catalog
    private ArrayList<Discount> discounts; // list of possible discount

    public Supply_Agreement(int id, int code, double price, int catalog) { // constructor
        supplier_id = id;
        product_code = code;
        list_price = price;
        catalog_code = catalog;
        discounts = new ArrayList<>();
    }

    // function that adds new possible discount to discounts list
    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    // function that removes discount of a product, based on condition and value
    public void removeDiscount(double condition, double value) {
        // iterate list of discounts. when finds, delete
        for (int i = 0; i < discounts.size(); i++) {
            if (discounts.get(i).getValue() == value && discounts.get(i).getCondition() == condition)
                discounts.remove(i);
        }
    }

    // getters of attributes
    public int getSupplierID() {return supplier_id;}
    public int getProductCode() {return product_code;}
    public double getListPrice() {return list_price;}
    public int getCatalogCode() {return catalog_code;}
    public ArrayList<Discount> getDiscounts() {return discounts;}

}
