package Inventory.BusinessLayer;

import java.time.LocalDate;

public class Item {
    String size=null;
    private String producerID;
    private String name;
    enum location {STORE, INVENTORY ,SOLD};
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
    private final int makat;
    private final int barcode;
    private String defDescription;



    public Item(String producer,int barcode, String name, location currentLocation, String expirationDate, double costPrice, int productID) {
        this.producerID = producer;
        this.name = name;
        this.currentLocation = currentLocation;
        try {
            if(expirationDate == null || expirationDate.equals("null"))
                this.expirationDate = null;
            else
                this.expirationDate = LocalDate.parse(expirationDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format");
        }
        this.costPrice = costPrice;
        this.makat = productID;
        this.barcode = barcode;
        this.sellingPrice = costPrice*(1.17+0.2);
    }

    public Item(String producer,int barcode, String name, location currentLocation, String expirationDate, double costPrice, int productID , String size) {
        this.producerID = producer;
        this.name = name;
        this.size=size;
        this.currentLocation = currentLocation;
        try {
            if(expirationDate == null || expirationDate.equals("null"))
                this.expirationDate = null;
            else
                this.expirationDate = LocalDate.parse(expirationDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format");
        }
        this.costPrice = costPrice;
        this.makat = productID;
        this.barcode = barcode;
        this.sellingPrice = costPrice*(1.17+0.2);
    }
@Override
    public String toString(){
        String categoryString = "";
        String productString = "";
        try {
            Category category = CategoryController.getInstance().getCategoryByProductID(makat);
            categoryString = category.toString();
            Product product = ProductController.getInstance().getProductById(makat);
            productString = product.toString();
        } catch (IllegalArgumentException e) {
            // handle the exception or log an error message
        }
        if(isDefective)
            return "producer: " + producerID + " name: " + name + " currentLocation: " + currentLocation + " isExpired: " + isExpired + " expirationDate: " + expirationDate + " costPrice: " + costPrice + " sellingPrice: " + sellingPrice + " isDefective: " + isDefective + " defDescription: " + defDescription + " productID: " + makat + " category: " + categoryString + " product: " + productString;
        return "producer: " + producerID + " name: " + name + " currentLocation: " + currentLocation + " isExpired: " + isExpired + " expirationDate: " + expirationDate + " costPrice: " + costPrice + " sellingPrice: " + sellingPrice + " isDefective: " + isDefective + " productID: " + makat + " category: " + categoryString + " product: " + productString;
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

    public void setCurrentLocation(String currentLocation) {
        switch (currentLocation) {
            case "STORE":
                this.currentLocation = location.STORE;
                break;
            case "INVENTORY":
                this.currentLocation = location.INVENTORY;
                break;
            case "SOLD":
                this.currentLocation = location.SOLD;
                break;
            default:
                throw new IllegalArgumentException("Invalid location");
        }
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
        if(expirationDate == null)
            return false;
        LocalDate today = LocalDate.now();
        if (today.isAfter(expirationDate)){
            isExpired = true;
        }
        return isExpired;
    }

    public int getMakat(){
        return this.makat;
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
