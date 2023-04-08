package dev.src.Inventory.ServiceLayer;
import dev.src.Inventory.BusinessLayer.Product;
import dev.src.Inventory.BusinessLayer.ProductController;

import java.util.ArrayList;

public class ProductService {
    //connect to ProductController controller

    ProductController productController;
    //constructor
    public ProductService() {
        productController = ProductController.getInstance();
    }

    //addProduct
    public void addProduct(String name, int minAmount, int categoryID, int makat , int supplierID) {
        productController.addProduct(name, minAmount, categoryID, makat, supplierID);
    }

    // SetMinimum-product
    public void setMinimum(int deliveryTime, int demand ,int productID){
        productController.setMinimum(deliveryTime, demand, productID);
    }


    //setDiscountByProduct
    public void setDiscountByProduct(int productID, float discount , String start, String end){
        productController.setDiscountByProduct(productID, discount, start, end);
    }

    //getProductsByCategory
    public ArrayList<Product> getProductsByCategory(int categoryID){
        ArrayList<Product> products = (ArrayList<Product>) productController.getProductsByCategory(categoryID);
        return products;
    }


}
