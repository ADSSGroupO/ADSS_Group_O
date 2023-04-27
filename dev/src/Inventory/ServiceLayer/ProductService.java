package Inventory.ServiceLayer;
import Inventory.BusinessLayer.Product;
import Inventory.BusinessLayer.ProductController;

import java.util.ArrayList;

public class ProductService {
    //connect to ProductController controller

    private final ProductController productController;
    //constructor
    public ProductService() {
        productController = ProductController.getInstance();
    }

    //addProduct
    public void addProduct(String name, int minAmount, int categoryID,String sub_category, int makat , int supplierID) {
        productController.addProduct(name, minAmount, categoryID, sub_category,makat, supplierID);
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


    public int getAmountOfProduct(int productID) {
        return productController.getAmountOfProduct(productID);
    }

    public void setDiscountBySupplier(int supplierID, int productID, Double discount) {
        productController.setDiscountBySupplier(supplierID, productID, discount);
    }

    public ArrayList<Double> getDiscountsByProductId(int productID) {
        return productController.getDiscountsByProductId(productID);
    }

    public void startConnection() {
        productController.startConnection();
    }
}
