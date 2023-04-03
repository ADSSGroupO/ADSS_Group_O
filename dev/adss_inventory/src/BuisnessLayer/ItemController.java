package dev.adss_inventory.src.BuisnessLayer;

import java.time.LocalDate;
import java.util.*;
//controller for items as singleton
public class ItemController {
    private Dictionary<Integer, List<Item>> soldItems; //sold items by category ID
    private Dictionary<Integer, List<Item>> storageItems; //storage items by category ID
    private Dictionary<Integer, List<Item>> inStoreItems; //in store items by category ID
    private Dictionary<Integer, List<Item>> defectiveItems; //defective items by category ID
    private ProductController productController;
    private Dictionary<Integer, Item> itemById;
    private static ItemController instance = null;


    public ItemController() {
    }

    public static ItemController getInstance() {
        if (instance == null) {
            instance = new ItemController();
        }
        return instance;
    }
     public void addItem(String manufacturer , Integer barcode, String name, LocalDate expirationDate, float costPrice, float sellingPrice , int category, int productID) {
        Item.location locate= Item.location.INVENTORY;
         Item item = new Item(manufacturer, name, locate, expirationDate, costPrice, sellingPrice,productID);
         List<Item> items = new LinkedList<Item>();
         items.add(item);
         //add to item by id dictionary
         itemById.put(barcode,item);
         //every new item added goes straight to storage
         storageItems.put(category,items);
     }
     //sold item
        public void itemSold(int CategoryID, int ItemID) {
            //get item from storage
            //add to sold items
            //remove from storage
            Item item = itemById.get(ItemID);
            if (inStoreItems.get(CategoryID).contains(item)){
                //add the item to sold items
                soldItems.get(CategoryID).add(item);
                //remove from in store items
                inStoreItems.get(CategoryID).remove(item);
                //remove from product amount
                productController.setProductAmountById(item.getProductID(),1);
            }
        }
        //get item
        public Item getItem(int CategoryID, int ItemID) {
            Item item = itemById.get(ItemID);
            //get item from storage
            if (inStoreItems.get(CategoryID).contains(item)){
                return inStoreItems.get(CategoryID).get(ItemID);
            }
            return null;
        }
        //+ItemsInStock()
        public List<Item> itemsInStock(int CategoryID) {
                //get all items from storage
             List<Item> items = new LinkedList<Item>();
             items.addAll(storageItems.get(CategoryID));
             items.addAll(inStoreItems.get(CategoryID));
                return items;
        }


    public void moveItemToStore(int categoryID, int itemID) {
        Item item = itemById.get(itemID);
        if (storageItems.get(categoryID).contains(item)){
            //add the item to in store items
            inStoreItems.get(categoryID).add(item);
            //remove from storage items
            storageItems.get(categoryID).remove(item);
        }
    }

    //getDiscount
    public float getDiscount(int ItemID) {
        Item item = itemById.get(ItemID);
        double discountProduct = productController.getProductDiscount(item.getProductID());
        double pricePerDiscountProduct = item.getSellingPrice()*(1-discountProduct)*(0.01);
       // double discountCategory = productController.getProductById(itemById.get(ItemID).getProductID()).getCategoryID();;

        return (float) pricePerDiscountProduct;
    }
}
