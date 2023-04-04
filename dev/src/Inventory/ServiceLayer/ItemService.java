package dev.src.Inventory.ServiceLayer;

import dev.src.Inventory.BuisnessLayer.Item;
import dev.src.Inventory.BuisnessLayer.ItemController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ItemService {
    //connect to item controller
    ItemController itemController;

    public ItemService() {
        this.itemController = ItemController.getInstance();
    }
    // AddItem(Place:string,Manufacturer:string ,Integer c )
    public void addItem(String manufacturer , Integer barcode, String name, LocalDate expirationDate, float costPrice , float sellingPrice, int category, int productID) {
        itemController.addItem( manufacturer, barcode, name, expirationDate, costPrice, sellingPrice ,category,productID);
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
     public List<Item> getItemsInStock(int categoryID) {
         return itemController.itemsInStock(categoryID);
     }


}
