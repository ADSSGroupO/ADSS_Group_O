package Inventory.BusinessLayer;

import java.time.LocalDate;
import java.util.ArrayList;

public class Product {
    private String name;
    private int minAmount;
    private int currentAmount;
    private int amountInStore;
    private int amountInInventory;
    private int categoryID;
    private int makat;
    private static int supplierID = 0;
    private ArrayList<Double> discounts;
    private String sub_category;


    private float discount;
    private LocalDate startDiscount;
    private LocalDate endDiscount;




    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Product(String name, int minAmount, int categoryID,String sub_category, int makat , int supplierID) {
        this.name = name;
        this.minAmount = minAmount;
        this.currentAmount = 0;
        this.amountInStore = 0;
        this.amountInInventory = 0;
        this.categoryID = categoryID;
        this.sub_category = sub_category;
        this.makat = makat;
        this.supplierID = supplierID;
        this.discounts = new ArrayList<>();

    }

    public int getMakat() {
        return makat;
    }

    public void setMakat(int makat) {
        this.makat = makat;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int deliveryTime, int demand ) {
        int storeState=demand*deliveryTime;
        if(minAmount>=storeState)
            this.minAmount = storeState;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount =- currentAmount;
    }

    public int getAmountInStore() {
        return amountInStore;
    }

    public void setAmountInStore(int amountInStore) {
        this.amountInStore =- amountInStore;
    }

    public int getAmountInInventory() {
        return amountInInventory;
    }

    public void setAmountInInventory(int amountInInventory) {
        this.amountInInventory =- amountInInventory;
    }

    public int getCategory() {
        return categoryID;
    }

    public void setCategory(int category) {
        this.categoryID = category;
    }

    //this method is used to reduce items from inventory and
    // raise a warning if the amount is below the minimum amount
    public void reduceItems(int amount){
        if(amount <= this.currentAmount)
            this.currentAmount -= amount;
        if(minAmount >= currentAmount)
            System.out.println("Warning: The amount of " + name + " is below the minimum amount");
            //TODO: send jason about the amount of product needed to order
    }
    public void addItems(int amount){
        this.currentAmount += amount;
    }
    public void addItemsToStore(int amount){
        this.amountInStore += amount;
    }
    public void addItemsToInventory(int amount){
        this.amountInInventory += amount;
    }
    public void reduceItemsFromStore(int amount){
        if(amount <= this.amountInStore)
            this.amountInStore -= amount;
            reduceItems(amount);
    }
    public void reduceItemsFromInventory(int amount){
        if(amount <= this.amountInInventory)
            this.amountInInventory -= amount;
            reduceItems(amount);
    }
    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }
    public int getSupplierID() {
        return supplierID;
    }


    public void setDiscount(LocalDate start, LocalDate end, float discount) {
        this.discount = discount;
        this.startDiscount = start;
        this.endDiscount = end;
    }


    public float getDiscount() {
        if(startDiscount == null || endDiscount == null)
            return 0;
        LocalDate now = LocalDate.now();
        if(now.isBefore(endDiscount) && now.isAfter(startDiscount))
            return discount;
        else
            return 0;

    }

    public String toString(){
        return "Product name: "+name+" Product id: "+makat+" Product sub category: "+sub_category;
    }

    public void setDiscountBySupplier(int supplierID, Double discount) {
        discounts.add(discount);
    }

    public ArrayList<Double> getDiscountsBySupplier() {
        return discounts;
    }
}
