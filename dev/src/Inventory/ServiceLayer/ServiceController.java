package dev.src.Inventory.ServiceLayer;

import dev.adss_inventory.src.BuisnessLayer.Category;
import dev.adss_inventory.src.BuisnessLayer.Product;

import java.time.LocalDate;
import java.util.List;

public class ServiceController {
    CategoryService categoryService;
    ProductService productService;
    ItemService itemService;

    public ServiceController() {
        categoryService = new CategoryService();
        productService = new ProductService();
        itemService = new ItemService();
    }
    ///////////////////////////ItemService/////////////////////////////
    public void addItem(String manufacturer, Integer barcode, String name, LocalDate expirationDate, float costPrice, float sellingPrice,int category, int productID) {
        itemService.addItem(manufacturer, barcode, name, expirationDate, costPrice, sellingPrice, category, productID);
    }
    public void getItem(int CategoryID, int ItemID) {
        itemService.getItem(CategoryID, ItemID);
    }
    public void moveItemToStore(int CategoryID, int ItemID) {
        itemService.moveItemToStore(CategoryID, ItemID);
    }
    public void ItemSold(int CategoryID, int ItemID) {
        itemService.ItemSold(CategoryID, ItemID);
    }
    public void getItemsInStock(int categoryID) {
        itemService.getItemsInStock(categoryID);
    }

    ///////////////////////////ProductService/////////////////////////////
    public void addProduct(String name, int minAmount, int categoryID, int makat , int supplierID) {
        productService.addProduct(name, minAmount, categoryID, makat , supplierID);
    }
    public void setMinimum(int deliveryTime, int demand ,int productID) {
        productService.setMinimum(deliveryTime, demand, productID);
    }
    public void setDiscountByProduct(int productID, float discount , String start, String end) {
        productService.setDiscountByProduct(productID, discount, start, end);
    }
    public void getProductsByCategory(int categoryID) {
        productService.getProductsByCategory(categoryID);
    }

    ///////////////////////////CategoryService/////////////////////////////
    public void addCategory(String name, int id) {
        categoryService.addCategory(name, id);
    }
//    public List<Product> getProductsInCategory(int id) {
//        categoryService.getProductsInCategory(id);
//    }
    public void setDiscountByCategory(int categoryID, float discount , String start, String end) {
        categoryService.setDiscountByCategory(categoryID, discount, start, end);
    }



}
