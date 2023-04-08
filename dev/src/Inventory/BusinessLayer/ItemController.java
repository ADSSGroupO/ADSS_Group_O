package dev.src.Inventory.BusinessLayer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//controller for items as singleton
public class ItemController {
    private static final HashMap<Integer, ArrayList<Item>> soldItems = new HashMap<>(); //sold items by category ID
    private static final HashMap<Integer, ArrayList<Item>> storageItems = new HashMap<>(); //storage items by category ID
    private static final HashMap<Integer, ArrayList<Item>> inStoreItems = new HashMap<>(); //in store items by category ID
    private static final HashMap<Integer, ArrayList<Item>> defectiveItems = new HashMap<>(); //defective items by category ID
    private static final ArrayList<Item> items = new ArrayList<>(); //all items
    private static final HashMap<Integer, Item> expiredItems = new HashMap<>();
    private final ProductController productController;
    private final CategoryController categoryController;
    private static final HashMap<Integer, Item> itemById = new HashMap<>();
    private static ItemController instance = null;
    private LocalDate lastReportDate;
    private static int DAYS_TO_Report = 7;


    private ItemController() {
        productController = ProductController.getInstance();
        categoryController = CategoryController.getInstance();
        this.lastReportDate = LocalDate.now();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        //check for expired items
        scheduler.scheduleAtFixedRate(this::checkForExpiredItems, 0, 1, TimeUnit.DAYS);
    }

    private void checkForExpiredItems() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        if (today == DayOfWeek.SUNDAY) { // or any other day of the week
            // Calculate the number of days between the last report date and today
            long daysSinceLastReport = ChronoUnit.DAYS.between(lastReportDate, LocalDate.now());
            if (daysSinceLastReport >= DAYS_TO_Report) {
                // Publish the report
                System.out.println("Publishing report...");
                publishExpiredReport();
                // Update the last report date to today
                lastReportDate = LocalDate.now();
            }
        }
    }

    private void publishExpiredReport() {
        if(expiredItems.isEmpty()){
            System.out.println("No expired items");
            return;
        }
        ArrayList<Item> expiredItemsList = new ArrayList<>(expiredItems.values());
        for (Item item : expiredItemsList) {
            System.out.println(item.toString());
        }
    }

    public void setDaysToReport(int days) {
        DAYS_TO_Report = days;
    }

    public static ItemController getInstance() {
        if (instance == null) {
            instance = new ItemController();
        }
        return instance;
    }

    public void addItem(String manufacturer, Integer barcode, String name, LocalDate expirationDate, double costPrice, int category, int productID) {
        if(itemById.containsKey(barcode)){
            throw new IllegalArgumentException("Item already exists");
        }
        if(!productController.getProductById().containsKey(productID)){
            throw new IllegalArgumentException("Product does not exist");
        }
        if(!categoryController.getCategoryById().containsKey(category)){
            throw new IllegalArgumentException("Category does not exist");
        }
        Item.location locate = Item.location.INVENTORY;
        Item item = new Item(manufacturer,barcode, name, locate, expirationDate, costPrice, productID);
        if(storageItems.containsKey(category)){//every new item added goes straight to storage
            storageItems.get(category).add(item);
        }
        else{
            ArrayList<Item> items = new ArrayList<>();
            items.add(item);
            storageItems.put(category, items);
        }
        items.add(item);
        //add to item by id dictionary
        itemById.put(barcode, item);
    }

    //sold item
    public void itemSold(int CategoryID, int ItemID) {
        //check if there are sold item from the same category
        soldItems.computeIfAbsent(CategoryID, k -> new ArrayList<>());
        //get item from storage
        //add to sold items
        //remove from storage
        Item item = itemById.get(ItemID);
        if(inStoreItems.get(CategoryID)!=null){
            if (inStoreItems.get(CategoryID).contains(item)) {
                //add the item to sold items
                soldItems.get(CategoryID).add(item);
                //remove from in store items
                inStoreItems.get(CategoryID).remove(item);
                //remove from product amount
                productController.setProductAmountById(item.getProductID(), 1, Item.location.STORE);
            }
        }
        else{
            soldItems.get(CategoryID).add(item);
            //remove from storage items
            storageItems.get(CategoryID).remove(item);
            //remove from product amount
            productController.setProductAmountById(item.getProductID(), 1, Item.location.INVENTORY);
        }
        //update the price been sold
        item.setThePriceBeenSoldAt(getDiscount(ItemID)); //todo: check if this is the right way to do it
    }

    //get item
    public Item getItem(int CategoryID, int ItemID) {
        Item item = itemById.get(ItemID);
        //get item from storage
        if (inStoreItems.get(CategoryID).contains(item)) {
            return inStoreItems.get(CategoryID).get(ItemID);
        }
        throw new IllegalArgumentException("Item not found");
    }

    //+ItemsInStock()
    public ArrayList<Item> itemsInStock(int CategoryID) {
        //get all items from storage
        ArrayList<Item> items = new ArrayList<>();
        if(storageItems.get(CategoryID)!=null){
            items.addAll(storageItems.get(CategoryID));
        }
        if(inStoreItems.get(CategoryID)!=null){
            items.addAll(inStoreItems.get(CategoryID));
        }
        return items;
    }


    public void moveItemToStore(int categoryID, int itemID) {
        //check if there are items in this category in store
        inStoreItems.computeIfAbsent(categoryID, k -> new ArrayList<>());
        Item item = itemById.get(itemID);
        if (storageItems.get(categoryID).contains(item)) {
            //add the item to in store items
            inStoreItems.get(categoryID).add(item);
            //remove from storage items
            storageItems.get(categoryID).remove(item);
        }
    }

    //getDiscount
    public double getDiscount(int ItemID) {
        Item item = itemById.get(ItemID);
        double discountProduct = productController.getProductDiscount(item.getProductID());
        double pricePerDiscountProduct = item.getSellingPrice() * (1 - discountProduct) * (0.01);
        int productID = itemById.get(ItemID).getProductID();
        int categoryID = productController.getProductById(productID).getCategoryID();
        double categoryDiscount = categoryController.getCategoryDiscount(categoryID);
        double pricePerDiscountCategory = item.getSellingPrice() * (1 - categoryDiscount) * (0.01);
        return Math.min(pricePerDiscountCategory, pricePerDiscountProduct);
    }

    //change to one item at a time
    public void defective(Integer DefItem, List<Integer> CategoryIds) {

        Item item = itemById.get(DefItem);
        item.setDefective(true);
        for (int j = 0; j < CategoryIds.size(); j++) {
            if (inStoreItems.get(j).contains(item)) {
                //add the item to sold items
                defectiveItems.get(j).add(item);
                //remove from in store items
                inStoreItems.get(j).remove(item);
                //remove from product amount
                productController.setProductAmountById(item.getProductID(), 1, Item.location.STORE);
            }
            if (storageItems.get(j).contains(item)) {
                //add the item to sold items
                defectiveItems.get(j).add(item);
                //remove from in store items
                storageItems.get(j).remove(item);
                //remove from product amount
                productController.setProductAmountById(item.getProductID(), 1, Item.location.INVENTORY);

            }
        }
    }

    //get all defective items
    public List<Item> getDefectiveItems(int CategoryID) {
        return defectiveItems.get(CategoryID);
    }

    public void getToBeExpiredReport(int days) {
        ArrayList<Item> allItems = new ArrayList<>();
        if(inStoreItems.isEmpty() && storageItems.isEmpty()){
            System.out.println("No items in Inventory");
            return;
        } else if (inStoreItems.isEmpty()) {
            for (int i = 0; i < storageItems.size(); i++) {
                allItems.addAll(storageItems.get(i));
            }
        } else if (storageItems.isEmpty()) {
            for (int i = 0; i < inStoreItems.size(); i++) {
                allItems.addAll(inStoreItems.get(i));
            }
        }
        for (Item allItem : allItems) {
            if (allItem.getExpirationDate().isBefore(LocalDate.now().plusDays(days))) {
                System.out.println(allItem);
            }
        }

    }

    public void getExpiredReport() {
        // Publish the report
        System.out.println("Publishing report...");
        publishExpiredReport();
    }

    public void getDefectiveReport() {
        // Publish the report
        System.out.println("Publishing report...");
        publishDefectiveReport();
    }

    private void publishDefectiveReport() {
        if(defectiveItems.isEmpty()){
            System.out.println("No defective items");
        }
        else{
            Collection<ArrayList<Item>> defectiveItemsList = defectiveItems.values();
            for (ArrayList<Item> item : defectiveItemsList) {
                for (Item value : item) {
                    System.out.println(value.toString());
                }
            }
        }
    }

    public void getInventoryReport() {
        // Publish the report
        System.out.println("Publishing report...");
        publishInventoryReport();
    }

    private void publishInventoryReport() {
        Collection<ArrayList<Item>> storageItemsList = storageItems.values();
        for (ArrayList<Item> item : storageItemsList) {
            for (Item value : item) {
                System.out.println(value.toString());
            }
        }
        Collection<ArrayList<Item>> inStoreItemsList = inStoreItems.values();
        for (ArrayList<Item> item : inStoreItemsList) {
            for (Item value : item) {
                System.out.println(value.toString());
            }
        }
    }

    public ArrayList<Item> getItemsInStore() {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < inStoreItems.size(); i++) {
            items.addAll(inStoreItems.get(i));
        }
        return items;
    }

    public double getPrice(int ItemID) {
        Item item = itemById.get(ItemID);
        //check if product has discount
        double discountProduct = productController.getProductDiscount(item.getProductID());
        double pricePerDiscountProduct = 0;
        if(discountProduct != 0){
            pricePerDiscountProduct = item.getSellingPrice() * (1 - (discountProduct * 0.01));
        }

        //check if category has discount
        int productID = item.getProductID();
        int categoryID = productController.getProductById(productID).getCategoryID();
        double categoryDiscount = categoryController.getCategoryDiscount(categoryID);
        double pricePerDiscountCategory = 0;
        if(categoryDiscount != 0){
            pricePerDiscountCategory = item.getSellingPrice() * (1 - (categoryDiscount * 0.01));
        }
        if(pricePerDiscountCategory == 0 && pricePerDiscountProduct == 0)
        {
            if(item.isDefective())
                return item.getSellingPrice() * 0.5;
            return item.getSellingPrice();
        }
        else{
            if(pricePerDiscountCategory == 0){
                if(item.isDefective())
                    return Math.min(pricePerDiscountProduct, item.getSellingPrice() * 0.5);
                return pricePerDiscountProduct;
            }
            else if(pricePerDiscountProduct == 0){
                if(item.isDefective())
                    return Math.min(pricePerDiscountCategory, item.getSellingPrice() * 0.5);
                return pricePerDiscountCategory;
            }
            else{
                if(item.isDefective())
                    return Math.min(Math.min(pricePerDiscountCategory, pricePerDiscountProduct), item.getSellingPrice() * 0.5);
                return Math.min(pricePerDiscountCategory, pricePerDiscountProduct);
            }
        }
    }
}
