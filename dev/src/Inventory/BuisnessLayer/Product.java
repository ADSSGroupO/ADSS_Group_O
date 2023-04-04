package dev.src.Inventory.BuisnessLayer;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Product {
    private String name;
    private int minAmount;
    private int currentAmount;
    private int amountInStore;
    private int amountInInventory;
    private int categoryID;
    private int makat;
    static int supplierID = 0;


    float discount;
    LocalDate startDiscount;
    LocalDate endDiscount;




    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Product(String name, int minAmount, int categoryID, int makat , int supplierID) {
        this.name = name;
        this.minAmount = minAmount;
        this.currentAmount = 0;
        this.amountInStore = 0;
        this.amountInInventory = 0;
        this.categoryID = categoryID;
        this.makat = makat;
        this.supplierID = supplierID;

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
        this.currentAmount = currentAmount;
    }

    public int getAmountInStore() {
        return amountInStore;
    }

    public void setAmountInStore(int amountInStore) {
        this.amountInStore = amountInStore;
    }

    public int getAmountInInventory() {
        return amountInInventory;
    }

    public void setAmountInInventory(int amountInInventory) {
        this.amountInInventory = amountInInventory;
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
    }
    public void reduceItemsFromInventory(int amount){
        if(amount <= this.amountInInventory)
            this.amountInInventory -= amount;
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
        LocalDate now = LocalDate.now();
        if(now.isBefore(startDiscount) && now.isAfter(endDiscount))
            return discount;
        else
            return 0;

    }
}
