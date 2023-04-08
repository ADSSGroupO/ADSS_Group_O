package dev.src.Inventory.BusinessLayer;

import java.time.LocalDate;

public class Item {
    private String producerID;
    private String name;
    enum location {STORE, INVENTORY};
    private location currentLocation;
    private boolean isExpired = false;
    private LocalDate expirationDate;
    //המחיר ששילמנו על המוצר מהספק
    private double costPrice;
    //המחיר שעלה המוצר
    private double sellingPrice;
    //המחיר שהמוצר נמכר בו!
    private double thePriceBeenSoldAt=-1 ;
    private boolean isDefective = false;
    private final int productID;
    private final int barcode;
    private String defDescription;



    public Item(String producer,int barcode, String name, location currentLocation, LocalDate expirationDate, double costPrice, int productID) {
        this.producerID = producer;
        this.name = name;
        this.currentLocation = currentLocation;
        this.expirationDate = expirationDate;
        this.costPrice = costPrice;
        this.productID = productID;
        this.barcode = barcode;
        this.sellingPrice = costPrice*(1.17+0.2);
    }
@Override
    public String toString(){
        String categoryString = "";
        String productString = "";
        try {
            Category category = CategoryController.getInstance().getCategoryByProductID(productID);
            categoryString = category.toString();
            Product product = ProductController.getInstance().getProductById(productID);
            productString = product.toString();
        } catch (IllegalArgumentException e) {
            // handle the exception or log an error message
        }
        if(isDefective)
            return "producer: " + producerID + " name: " + name + " currentLocation: " + currentLocation + " isExpired: " + isExpired + " expirationDate: " + expirationDate + " costPrice: " + costPrice + " sellingPrice: " + sellingPrice + " isDefective: " + isDefective + " defDescription: " + defDescription + " productID: " + productID + " category: " + categoryString + " product: " + productString;
        return "producer: " + producerID + " name: " + name + " currentLocation: " + currentLocation + " isExpired: " + isExpired + " expirationDate: " + expirationDate + " costPrice: " + costPrice + " sellingPrice: " + sellingPrice + " isDefective: " + isDefective + " productID: " + productID + " category: " + categoryString + " product: " + productString;
}

    public String getProducer() {
        return producerID;
    }

    public String getName() {
        return name;
    }

    public location getCurrentLocation() {
        return currentLocation;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public boolean isDefective() {
        return isDefective;
    }

    public void setProducer(String producer) {
        this.producerID = producer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentLocation(location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setDefective(String defDescription) {
        this.defDescription = defDescription;
        isDefective = true;
    }
    /**
     * check if the item is expired
     * @return true if the item is expired
     */
    public boolean checkDate(){
        LocalDate today = LocalDate.now();
        if (today.isAfter(expirationDate)){
            isExpired = true;
        }
        return isExpired;
    }

    public int getProductID(){
        return this.productID;
    }
    public double getThePriceBeenSoldAt() {
        return thePriceBeenSoldAt;
    }

    public void setThePriceBeenSoldAt(double thePriceBeenSoldAt) {
        this.thePriceBeenSoldAt = thePriceBeenSoldAt;
    }

    public int getBarcode(){
        return this.barcode;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getPrice(){
        return this.sellingPrice;
    }
}
