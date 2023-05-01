package Inventory.BusinessLayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private String name;
    private int minAmount;
    private HashMap<Branch, Integer> currentAmount;
    private int amountInStore;
    private int amountInInventory;
    private int categoryID;
    private int makat;
    private  int supplierID = -1;
    private final ArrayList<Double> discounts;
    private final String sub_category;


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
        this.currentAmount = new HashMap<>();
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

    public HashMap<Branch, Integer> getCurrentAmount() {
        return currentAmount;
    }
    public  Integer getCurrentAmount(String branch) {
        return currentAmount.get(Branch.valueOf(branch));
    }

    public void setCurrentAmount(Branch branch, int amount) {
        this.currentAmount.put(branch, amount);
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
    public void reduceItems(int amount, Branch branch){
        if(amount <= this.currentAmount.get(branch))
            this.currentAmount.put(branch, this.currentAmount.get(branch) - amount);
        if(minAmount >= this.currentAmount.get(branch))
            System.out.println("Warning: The amount of " + name + " is below the minimum amount");
    }
    //when we add items its only into the inventory
    public void addItems(int amount){
        this.amountInInventory += amount;
    }
    public void addItemsToStore(int amount){
        this.amountInStore += amount;
    }
    public void addItemsToInventory(int amount){
        this.amountInInventory += amount;
    }
    public void reduceItemsFromStore(int amount, Branch branch){
        if(amount <= this.amountInStore) {
            this.amountInStore -= amount;
            reduceItems(amount, branch);
        }
    }
    public void reduceItemsFromInventory(int amount){
        if(amount <= this.amountInInventory) {
            this.amountInInventory -= amount;
            //reduceItems(amount);
        }
    }
//    public void setSupplierID(int supplierID) {
//        this.supplierID = supplierID;
//    }
    public int getSupplierID() {
        return supplierID;
    }


    public void setDiscount(String start, String end, float discount) {
        try{
            LocalDate startDiscount = LocalDate.parse(start);
            LocalDate endDiscount = LocalDate.parse(end);
            this.discount=discount;
            this.startDiscount=startDiscount;
            this.endDiscount=endDiscount;
        }
        catch (Exception e){
            System.out.println("Error in date format");
        }
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
