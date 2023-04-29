package Inventory.BusinessLayer;

import Inventory.DataAccessLayer.Mapper.ItemDAO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//controller for items as singleton
public class ItemController {
    private static HashMap<Integer, ArrayList<Item>> soldItems = new HashMap<>(); //sold items by category ID
    private static HashMap<Integer, ArrayList<Item>> storageItems = new HashMap<>(); //storage items by category ID
    private static HashMap<Integer, ArrayList<Item>> inStoreItems = new HashMap<>(); //in store items by category ID
    private static HashMap<Integer, ArrayList<Item>> defectiveItems = new HashMap<>(); //defective items by category ID
    private static final ArrayList<Item> items = new ArrayList<>(); //all items
    private static final HashMap<Integer, Item> expiredItems = new HashMap<>();
    private final ProductController productController;
    private final CategoryController categoryController;
    private static HashMap<Integer, Item> itemById = new HashMap<>();
    private static ItemController instance = null;
    private LocalDate lastReportDate;
    private static int DAYS_TO_Report = 7;
    private static final HashMap<Integer, ArrayList<Item>> storageItemsByProductID = new HashMap<>(); //storage items by Product ID
    private static final HashMap<Integer, ArrayList<Item>> inStoreItemsByProductId = new HashMap<>(); //in store items by Product ID
    private final ItemDAO itemDAO;
    private boolean connection_opened = false;



    private ItemController() {
        productController = ProductController.getInstance();
        categoryController = CategoryController.getInstance();
        this.lastReportDate = LocalDate.now();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        //check for expired items
        scheduler.scheduleAtFixedRate(this::checkForExpiredItems, 0, 1, TimeUnit.DAYS);
        itemDAO = new ItemDAO();
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
        removeExpired(inStoreItems);
        removeExpired(storageItems);
        if(expiredItems.isEmpty()){
            System.out.println("No expired items");
            return;
        }
        ArrayList<Item> expiredItemsList = new ArrayList<>(expiredItems.values());
        for (Item item : expiredItemsList) {
            System.out.println(item.toString());
        }
    }

    private void removeExpired(HashMap<Integer, ArrayList<Item>> inStoreItems) {
        if(inStoreItems.isEmpty()){
            return;
        }
        for (int i = 0; i< inStoreItems.size(); i++) {
            for(int j = 0; j< inStoreItems.get(i).size(); j++) {
                if(inStoreItems.get(i).get(j).checkDate()){
                    expiredItems.put(i, inStoreItems.get(i).get(j));
                    inStoreItems.get(i).remove(inStoreItems.get(i).get(j));
                    // update the item is expired in the DB
                    itemDAO.setExpired(inStoreItems.get(i).get(j).getBarcode());
                }
            }
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

    public void addItem(String manufacturer, Integer barcode, String name, String expirationDate, double costPrice, int category, int productID,String size) {
        if(itemById.containsKey(barcode)){
            throw new IllegalArgumentException("Item already exists");
        }
        if(!productController.getProductById().containsKey(productID)){
            throw new IllegalArgumentException("Product does not exist");
        }
        if(!categoryController.getCategoryById().containsKey(category)){
            throw new IllegalArgumentException("Category does not exist");
        }
        Item.Location locate = Item.Location.INVENTORY;
        Item item = null;
        if(size==null || size.equals("") || size.equals("null")){
            item = new Item(manufacturer,barcode, name, locate, expirationDate, costPrice, productID);
        }
        else {
            item = new Item(manufacturer,barcode, name, locate, expirationDate, costPrice, productID,size);
        }
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
        //add the amount of the product
        productController.addItem(productID);
        //add the item to the database
        itemDAO.addItem(manufacturer,barcode, name, expirationDate, costPrice,category, productID,size, item.getSellingPrice());
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
                if(soldItems.get(CategoryID)==null){
                    soldItems.put(CategoryID, new ArrayList<>());
                }
                soldItems.get(CategoryID).add(item);
                //remove from in store items
                inStoreItems.get(CategoryID).remove(item);
                //remove from product amount
                productController.reduceAmountOfProductByID(item.getMakat(), 1, Item.Location.STORE);
                item.setCurrentLocation("SOLD");
                // update the item is sold in the DB
                itemDAO.setSold(item.getBarcode());
            }
        }
        else{
            soldItems.get(CategoryID).add(item);
            //remove from storage items
            storageItems.get(CategoryID).remove(item);
            //remove from product amount
            productController.reduceAmountOfProductByID(item.getMakat(), 1, Item.Location.INVENTORY);
            item.setCurrentLocation("SOLD");
            // update the item is sold in the DB
            itemDAO.setSold(item.getBarcode());
        }
        double thePriceBeenSoldAt = getDiscount(ItemID);
        //update the price been sold
        item.setThePriceBeenSoldAt(thePriceBeenSoldAt);
        //update the price the item been sold at in the DB
        itemDAO.setThePriceBeenSoldAt(item.getBarcode(), thePriceBeenSoldAt);
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
        if(inStoreItems.get(categoryID).contains(itemById.get(itemID))){
            throw new IllegalArgumentException("Item already in store");
        }
        else if(storageItems.get(categoryID).contains(itemById.get(itemID))){
            //add the item to in store items
            inStoreItems.get(categoryID).add(itemById.get(itemID));
            //remove from storage items
            storageItems.get(categoryID).remove(itemById.get(itemID));
            //update the location in the DB
            itemDAO.moveItemToStore(itemID);
        }
        else{
            throw new IllegalArgumentException("Item not found");
        }
        Item item = itemById.get(itemID);
        if (storageItems.get(categoryID).contains(item)) {
            //add the item to in store items
            inStoreItems.get(categoryID).add(item);
            //remove from storage items
            storageItems.get(categoryID).remove(item);
            //update the location in the DB
            itemDAO.moveItemToStore(itemID);
        }
    }

    //getDiscount
    public double getDiscount(int ItemID) {
        Item item = itemById.get(ItemID);
        double discountProduct = productController.getProductDiscount(item.getMakat());
        double pricePerDiscountProduct = item.getSellingPrice() * (1 - discountProduct) * (0.01);
        int productID = itemById.get(ItemID).getMakat();
        int categoryID = productController.getProductById(productID).getCategoryID();
        double categoryDiscount = categoryController.getCategoryDiscount(categoryID);
        double pricePerDiscountCategory = item.getSellingPrice() * (1 - categoryDiscount) * (0.01);
        return Math.min(pricePerDiscountCategory, pricePerDiscountProduct);
    }

    //change to one item at a time
    public void defective(Integer DefItem, int CategoryId, String reason) {
        Item item = itemById.get(DefItem);
            if (inStoreItems.get(CategoryId).contains(item)) {
                //add the item to sold items
                defectiveItems.get(CategoryId).add(item);
                //remove from in store items
                inStoreItems.get(CategoryId).remove(item);
                //remove from product amount
                productController.reduceAmountOfProductByID(item.getMakat(), 1, Item.Location.STORE);
                //update the defctive description
                item.setDefectiveDescription(reason);
                //update is_defective in the DB
                itemDAO.defective(item.getBarcode(), reason);
            }
            if (storageItems.get(CategoryId).contains(item)) {
                //add the item to sold items
                defectiveItems.get(CategoryId).add(item);
                //remove from in store items
                storageItems.get(CategoryId).remove(item);
                //remove from product amount
                productController.reduceAmountOfProductByID(item.getMakat(), 1, Item.Location.INVENTORY);
                //update is_defective in the DB
                itemDAO.defective(item.getBarcode(), reason);

            }
    }

    //get all defective items
    public ArrayList<Item> getDefectiveItems(int CategoryID) {
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
        for (Item item : itemById.values()) {
            System.out.println(item.toString());
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
        double discountProduct = productController.getProductDiscount(item.getMakat());
        double pricePerDiscountProduct = 0;
        if(discountProduct != 0){
            pricePerDiscountProduct = item.getSellingPrice() * (1 - (discountProduct * 0.01));
        }

        //check if category has discount
        int productID = item.getMakat();
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

    public void setLocation(int barcode, String currentLocation){
        if(itemById.containsKey(barcode)){
            itemById.get(barcode).setCurrentLocation(currentLocation);
        }
        else{
            throw new IllegalArgumentException("Item does not exist");
        }
    }

    public void startConnection() {
        try{
            if (!connection_opened){
            itemDAO.startConnection();
            connection_opened = true;
            soldItems = itemDAO.getSoldItems();
            itemById = itemDAO.getItemById();
            int categoryId;
            for(Item item : itemById.values()){
                if(item.getLocation()!= Item.Location.SOLD){
                    productController.updateAmount(item.getMakat());
                    categoryId = productController.getProductById(item.getMakat()).getCategoryID();
                    if(item.getLocation() == Item.Location.INVENTORY){
                        if(!storageItems.containsKey(categoryId)){
                            storageItems.put(categoryId, new ArrayList<>());
                        }
                        storageItems.get(categoryId).add(item);
                    }
                    else{
                        if(!inStoreItems.containsKey(categoryId)){
                            inStoreItems.put(categoryId, new ArrayList<>());
                        }
                        inStoreItems.get(categoryId).add(item);
                    }
                }
            }
            }
        } catch (Exception e){
            System.out.println("Error connecting to DB");
        }
    }
}
