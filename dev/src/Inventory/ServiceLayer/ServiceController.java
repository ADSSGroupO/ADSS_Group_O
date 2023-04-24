package Inventory.ServiceLayer;

import Inventory.BusinessLayer.Item;
import Inventory.BusinessLayer.Product;
import Inventory.ServiceLayer.CategoryService;
import Inventory.ServiceLayer.ItemService;
import Inventory.ServiceLayer.ProductService;
import java.util.ArrayList;

public class ServiceController {
    private final Inventory.ServiceLayer.CategoryService categoryService;
    private final ProductService productService;
    private final ItemService itemService;
    private static ServiceController serviceController = null;

    private ServiceController() {
        categoryService = new CategoryService();
        productService = new ProductService();
        itemService = new ItemService();
    }

    public static ServiceController getInstance() {
        if(serviceController == null) {
            serviceController = new ServiceController();
        }
        return serviceController;
    }

    ///////////////////////////ItemService/////////////////////////////
    public Boolean addItem(String manufacturer, Integer barcode, String name, String expirationDate, double costPrice,int category, int productID ,String size) {
        try{
            itemService.addItem(manufacturer, barcode, name, expirationDate, costPrice, category, productID ,size);
        } catch (Exception e) {
            return false;
        }
        return true;

    }
    public void getItem(int CategoryID, int ItemID) {
        itemService.getItem(CategoryID, ItemID);
    }
    public void moveItemToStore(int CategoryID, int ItemID) {
        itemService.moveItemToStore(CategoryID, ItemID);
    }
    public Boolean itemSold(int CategoryID, int ItemID) {
        try{
            itemService.ItemSold(CategoryID, ItemID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public double getPrice(int barcode) {
        return itemService.getPrice(barcode);
    }
    public ArrayList<Item> getItemsInStock(int categoryID) {
        return itemService.getItemsInStock(categoryID);
    }

    ///////////////////////////ProductService/////////////////////////////
    public boolean addProduct(String name, int minAmount, int categoryID,String sub_category, int makat , int supplierID) {
        try{
            productService.addProduct(name, minAmount, categoryID, sub_category,makat , supplierID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public void setMinimum(int deliveryTime, int demand ,int productID) {
        productService.setMinimum(deliveryTime, demand, productID);
    }
    public void setDiscountByProduct(int productID, float discount , String start, String end) {
        productService.setDiscountByProduct(productID, discount, start, end);
    }
    public ArrayList<Product> getProductsByCategory(int categoryID) {

        return productService.getProductsByCategory(categoryID);
    }

    ///////////////////////////CategoryService/////////////////////////////
    public boolean addCategory(String name, int id) {
        try{
            categoryService.addCategory(name, id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
//    public List<Product> getProductsInCategory(int id) {
//        categoryService.getProductsInCategory(id);
//    }
    public void setDiscountByCategory(int categoryID, float discount , String start, String end) {
        categoryService.setDiscountByCategory(categoryID, discount, start, end);
    }


    public void getExpiredReport() {
        itemService.getExpiredReport();
    }

    public void getToBeExpiredReport(int days) {
        itemService.getToBeExpiredReport(days);
    }

    public void getDefectiveReport() {
        itemService.getDefectiveReport();
    }

    public void setDefectiveReport(int days) {
        itemService.setDaysToReport(days);
    }

    public void getInventoryReport() {
        itemService.getInventoryReport();
    }

    public ArrayList<Item> getItemsInStore() {
        return itemService.getItemsInStore();
    }

    public int getAmountOfProduct(int productID) {
        return productService.getAmountOfProduct(productID);
    }

    public void setDiscountBySupplier(int supplierID, int productID, Double discount) {
        productService.setDiscountBySupplier(supplierID, productID, discount);
    }

    public ArrayList<Double> getDiscountsByProductId(int productID) {
        return productService.getDiscountsByProductId(productID);
    }
/*
    this function is for testing purposes only
 */
    public void addData() {
        String expirationDateStr = "2023-12-31";
        this.addCategory("Dairy products", 0);
        this.addCategory("Meat products", 1);
        this.addCategory("Housewares", 2);
        this.addProduct("Milk", 5, 0, "milk",0, 0);
        this.addProduct("Cheese", 2, 0, "cheese",1, 0);
        this.addProduct("Salami", 2, 1, "salami",2, 0);
        this.addProduct("Beef Fillet ", 2, 1, "beef fillet",3, 0);
        this.addProduct("Broom", 2, 2, "broom",4, 0);
        this.addProduct("Pot", 2, 2,"pot" ,5, 0);
        this.addItem("Tnuva", 0, "Milk 3%", expirationDateStr, 6.9, 0, 0 ,"1L");
        this.addItem("Tnuva", 1, "Milk 3%", expirationDateStr, 6.9, 0, 0,"1L");
        this.addItem("Tnuva", 2, "Milk 3%", expirationDateStr, 6.9, 0, 0,"1L");
        this.addItem("Tnuva", 3, "Milk 3%", expirationDateStr, 6.9, 0, 0,"1L");
        this.addItem("Tnuva", 4, "Milk 3%", expirationDateStr, 6.9, 0, 0,"1L");
        this.addItem("Tnuva", 5, "Milk 3%", expirationDateStr, 6.9, 0, 0,"1L");
        this.addItem("Tnuva", 6, "Milk 3%", expirationDateStr, 6.9, 0, 0,"1L");
        this.addItem("Tnuva", 7, "Milk 3%", expirationDateStr, 6.9, 0, 0,"1L");
        this.addItem("Tnuva", 8, "Cheese 3%", expirationDateStr, 10, 0, 1,"250g");
        this.addItem("Tnuva", 9, "Cheese 3%", expirationDateStr, 10, 0, 1,"250g");
        this.addItem("Tnuva", 10, "Cheese 3%", expirationDateStr, 10, 0, 1,"250g");
        this.addItem("Tnuva", 11, "Cheese 3%", expirationDateStr, 10, 0, 1,"250g");
        this.addItem("Tnuva", 12, "Cheese 3%", expirationDateStr, 10, 0, 1,"250g");
        this.addItem("Tnuva", 13, "Cheese 3%", expirationDateStr, 10, 0, 1,"250g");
        this.addItem("Zoglobek", 14, "Salami 5%", expirationDateStr, 15, 1, 2,"500g");
        this.addItem("Zoglobek", 15, "Salami 5%", expirationDateStr, 15, 1, 2 ,"500g");
        this.addItem("Zoglobek", 16, "Salami 5%", expirationDateStr, 15, 1, 2,"500g");
        this.addItem("Zoglobek", 17, "Salami 5%", expirationDateStr, 15, 1, 2, "500g");
        this.addItem("Zoglobek", 18, "Salami 5%", expirationDateStr, 15, 1, 2, "500g");
        this.addItem("Zoglobek", 19, "Salami 5%", expirationDateStr, 15, 1, 2 ,"500g");
        this.addItem("Havat HaBokrim", 20, "Beef Fillet", expirationDateStr, 100, 1, 3,"1k");
        this.addItem("Havat HaBokrim", 21, "Beef Fillet", expirationDateStr, 100, 1, 3 ,"1k");
        this.addItem("Havat HaBokrim", 22, "Beef Fillet", expirationDateStr, 100, 1, 3, "1k");
        this.addItem("Havat HaBokrim", 23, "Beef Fillet", expirationDateStr, 100, 1, 3, "1k");
        this.addItem("Havat HaBokrim", 24, "Beef Fillet", expirationDateStr, 100, 1, 3 ,"1k");
        this.addItem("Havat HaBokrim", 25, "Beef Fillet", expirationDateStr, 100, 1, 3, "1k");
        this.addItem("Havat HaBokrim", 26, "Beef Fillet", expirationDateStr, 100, 1, 3,"1k");
        this.addItem("Soltam", 27, "Pot 1L", null, 55, 2, 4 ,"");
        this.addItem("Soltam", 28, "Pot 1L", null, 55, 2, 4,"");
        this.addItem("Soltam", 29, "Pot 0.5L", null, 55, 2, 4,"");
        this.addItem("Soltam", 30, "Pot 0.5L", null, 55, 2, 4,"");
        this.addItem("Soltam", 31, "Pot 0.5L", null, 55, 2, 4,"");
        this.addItem("Ruhama", 32, "Broom", null, 15, 2, 5,"");
        this.addItem("Ruhama", 33, "Broom", null, 15, 2, 5,"");
        this.addItem("Ruhama", 34, "Broom", null, 15, 2, 5,"");
        this.addItem("Ruhama", 35, "Broom", null, 15, 2, 5,"");
        this.addItem("Ruhama", 36, "Broom", null, 15, 2, 5,"");
        this.addItem("Ruhama", 37, "Broom", null, 15, 2, 5,"");
        this.addItem("Ruhama", 38, "Broom", null, 15, 2, 5,"");
        this.addItem("Ruhama", 39, "Broom", null, 15, 2, 5,"");
    }

    public ArrayList<StringBuilder> getInventoryReportByCategory(ArrayList<Integer> categoryList) {
        return categoryService.getReportOfItemsInStockByCategory(categoryList);
    }
}
