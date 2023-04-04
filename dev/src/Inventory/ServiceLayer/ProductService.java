package dev.src.Inventory.ServiceLayer;
import dev.src.Inventory.BusinessLayer.ProductController;

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
    public void getProductsByCategory(int categoryID){
        productController.getProductsByCategory(categoryID);
    }
}
