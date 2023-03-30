package dev.adss_inventory.src.BuisnessLayer;

import java.util.Date;

public class Item {
    private String producerID;
    private String name;
    enum location {STORE, INVENTORY};
    private location currentLocation;
    private boolean isExpired = false;
    private Date expirationDate;
    private float costPrice;
    private float sellingPrice;
    private boolean isDefective = false;
    private int productID;


    public Item(String producer, String name, location currentLocation, Date expirationDate, float costPrice, float sellingPrice, int productID) {
        this.producerID = producer;
        this.name = name;
        this.currentLocation = currentLocation;
        this.expirationDate = expirationDate;
        this.costPrice = costPrice;
        this.productID = productID;
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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public float getSellingPrice() {
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

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setDefective(boolean defective) {
        isDefective = defective;
    }
    /**
     * check if the item is expired
     * @return true if the item is expired
     */
    public boolean checkDate(){
        Date today = new Date();
        if (today.after(expirationDate)){
            isExpired = true;
        }
        return isExpired;
    }

    public int getProductID(){
        return this.productID;
    }
}
