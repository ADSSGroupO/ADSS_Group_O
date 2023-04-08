package dev.src.Inventory.ServiceLayer;

import dev.src.Inventory.BusinessLayer.Item;
import dev.src.Inventory.BusinessLayer.ItemController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemService {
    //connect to item controller
    ItemController itemController;

    public ItemService() {
        this.itemController = ItemController.getInstance();
    }
    // AddItem(Place:string,Manufacturer:string ,Integer c )
    public void addItem(String manufacturer , Integer barcode, String name, LocalDate expirationDate, double costPrice , int category, int productID) {
        itemController.addItem( manufacturer, barcode, name, expirationDate, costPrice ,category,productID);
    }

    // getItem(ID: int ,barcode: int)
    public void getItem(int CategoryID,int ItemID) {
        itemController.getItem(CategoryID,ItemID);
    }

    //move item to store
    public void moveItemToStore(int CategoryID,int ItemID){
          itemController.moveItemToStore(CategoryID,ItemID);
    }

    //update Item has been sold
    public void ItemSold(int CategoryID,int ItemID){
        itemController.itemSold(CategoryID,ItemID);
        //need to update product amount
    }
    //return a list of items in stock (both at store and storage)
    /*I think that this function not needed any more. what do you say?*/
     public ArrayList<Item> getItemsInStock(int categoryID) {
         return itemController.itemsInStock(categoryID);
     }
    /*Make item a Defective */
    public void setDefectiveItems(Integer items,List<Integer> CategoryIds) {
        itemController.defective(items,CategoryIds);

    }

    public void setDaysToReport(int days){
        itemController.setDaysToReport(days);
    }


    public void getExpiredReport() {
        itemController.getExpiredReport();
    }

    public void getToBeExpiredReport(int days) {
        itemController.getToBeExpiredReport(days);
    }

    public void getDefectiveReport() {
        itemController.getDefectiveReport();
    }

    public void getInventoryReport() {
        itemController.getInventoryReport();
    }

    public ArrayList<Item> getItemsInStore() {
        return itemController.getItemsInStore();
    }

    public double getPrice(int barcode) {
        return itemController.getPrice(barcode);
    }
}
