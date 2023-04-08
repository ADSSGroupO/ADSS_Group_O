package dev.src.Inventory.ServiceLayer;
import dev.src.Inventory.BusinessLayer.Item;
import dev.src.Inventory.BusinessLayer.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServiceController {
    private final CategoryService categoryService;
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
    public Boolean addItem(String manufacturer, Integer barcode, String name, LocalDate expirationDate, double costPrice,int category, int productID) {
        try{
            itemService.addItem(manufacturer, barcode, name, expirationDate, costPrice, category, productID);
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
    public boolean addProduct(String name, int minAmount, int categoryID, int makat , int supplierID) {
        try{
            productService.addProduct(name, minAmount, categoryID, makat , supplierID);
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
        ArrayList<Product> products = productService.getProductsByCategory(categoryID);

        return products;
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
}
