package dev.src.Inventory.BusinessLayer;

import java.time.LocalDate;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;
//product controller as a singleton
public class ProductController {

    CategoryController categoryController;
    private Dictionary<Integer,List<Category>> categoryByProduct;//dictionary contains the category by product

    public Product getProductById(Integer productId) {
        return ProductById.get(productId);
    }

    private Dictionary<Integer, Product> ProductById;
  //  private Dictionary<Integer, List<Item>> itemByProduct;
    private static ProductController instance = null;


    public ProductController() {
    }

    public static ProductController getInstance() {
        if (instance == null) {
            instance = new ProductController();
        }
        return instance;
    }

    public void setProductAmountById(int productID, int amount) {
        ProductById.get(productID).setCurrentAmount( ProductById.get(productID).getCurrentAmount()-amount);
    }
    //add product with category
    public void addProduct(String name, int minAmount, int categoryID, int makat , int supplierID) {
        Product product = new Product(name, minAmount, categoryID, makat , supplierID);
        List<Product> products = new LinkedList<Product>();
        products.add(product);
        Category category = new Category(name,categoryID);
        List<Category> categories = new LinkedList<Category>();
        //add to product by id dictionary
        ProductById.put(makat,product);
        //add to product by category dictionary
        categoryController.addProductByCategory(products,categoryID);
        //add to category by product dictionary
        categoryByProduct.put(makat,categories);
    }
    //get product
    public Product getProduct(int productID){
        return ProductById.get(productID);
    }
    //get product by category
    public List<Product> getProductsByCategory(int categoryID){
        return categoryController.getProductsByCategory(categoryID);
    }

    //set minimum amount
    public void setMinimum(int deliveryTime, int demand ,int productID){
        //set minimum amount
        ProductById.get(productID).setMinAmount(deliveryTime,demand);
    }



    public void setDiscountByProduct(int productID, float discount , String start, String end){
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        ProductById.get(productID).setDiscount(startDate,endDate,discount);
    }
    //get product discount
    public float getProductDiscount(int productID){
        return ProductById.get(productID).getDiscount();
    }
}
