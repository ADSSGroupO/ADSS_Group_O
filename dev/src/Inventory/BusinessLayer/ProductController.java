package Inventory.BusinessLayer;

import Inventory.DataAccessLayer.Mapper.ProductDAO;

import java.time.LocalDate;
import java.util.*;

//product controller as a singleton
public class ProductController {

    private final CategoryController categoryController;
    private static HashMap<Integer,List<Category>> categoryByProduct = new HashMap<Integer,List<Category>>();
    private static HashMap<Integer, Product> ProductById = new HashMap<Integer, Product>();
  //  private Dictionary<Integer, List<Item>> itemByProduct;
    private static ProductController instance = null;
    private static ArrayList<Product> products = new ArrayList<Product>();
    private static ArrayList<Category> categories = new ArrayList<Category>();
    private final ProductDAO productDAO;
    private boolean opened_connection = false;

    private ProductController() {
        categoryController = CategoryController.getInstance();
        productDAO = new ProductDAO();
    }

    public static ProductController getInstance() {
        if (instance == null) {
            instance = new ProductController();
        }
        return instance;
    }
    public Product getProductById(Integer productId) {
        return ProductById.get(productId);
    }

    public void reduceAmountOfProductByID(int productID, int amount , Item.Location locale) {
        //ProductById.get(productID).setCurrentAmount( ProductById.get(productID).getCurrentAmount()-amount);
        Product product =ProductById.get(productID);
        product.reduceItems(amount);
        switch (locale){
            case STORE : {
                product.reduceItemsFromStore(1);
                break;
            }
            case INVENTORY : {
                product.reduceItemsFromInventory(1);
                break;
            }
        }
        // reduce the amount of the product in the productDAO
        productDAO.reduceAmountOfProductByID(productID,amount);
    }
    //add product with category
    public void addProduct(String name, int minAmount, int categoryID,String subCategory, int makat , int supplierID) {
        if(ProductById.containsKey(makat)){
            System.out.println("product ID already exist");
            throw new IllegalArgumentException("product ID already exist");
        }
        if(categoryController.getCategoryById(categoryID)==null){
            System.out.println("category ID not exist");
            throw new IllegalArgumentException("category ID not exist");
        }
        Product product = new Product(name, minAmount, categoryID,subCategory, makat , supplierID);
        products.add(product);
        Category category = new Category(name,categoryID);
        //add to product by id dictionary
        ProductById.put(makat,product);
        //add to product by category dictionary
        categoryController.addProductByCategory(products,categoryID);
        //add to category by product dictionary
        categoryByProduct.put(makat,categories);
        //add to productDAO
        productDAO.addProduct(name,minAmount,categoryID,subCategory,makat,supplierID);
    }
    //get product
    public Product getProduct(int productID){
        return ProductById.get(productID);
    }
    //get product by category
    public ArrayList<Product> getProductsByCategory(int categoryID){
        return categoryController.getProductsByCategory(categoryID);
    }

    //set minimum amount
    public void setMinimum(int deliveryTime, int demand ,int productID){
        //set minimum amount
        if(ProductById.containsKey(productID)){
            ProductById.get(productID).setMinAmount(deliveryTime,demand);
            int minAmount = ProductById.get(productID).getMinAmount();
            //update the product in the productDAO
            productDAO.setMinimum(productID,minAmount);
        }
        else{
            throw new IllegalArgumentException("product not exist");
        }
        }



    public void setDiscountByProduct(int productID, float discount , String start, String end){
        if(products.contains(ProductById.get(productID))){
        ProductById.get(productID).setDiscount(start,end,discount);
        //update the product in the productDAO'
        productDAO.setDiscountByProduct(productID,discount,start,end);
            }
            else{
        throw new IllegalArgumentException("product not exist");
        }
    }
    //get product discount
    public float getProductDiscount(int productID){
        return ProductById.get(productID).getDiscount();
    }
    //get the hash map of product by ID
    public HashMap<Integer, Product> getProductById() {
        return ProductById;
    }

    public void addItem(int productID){
        ProductById.get(productID).addItems(1);
        //update the product in the productDAO
        productDAO.addItem(productID);
    }

    public int getAmountOfProduct(int productID) {
        if(ProductById.containsKey(productID)){
            return ProductById.get(productID).getCurrentAmount();
        }
        else{
            throw new IllegalArgumentException("product not exist");
        }
    }

    public void setDiscountBySupplier(int supplierID, int productID, Double discount) {
        ProductById.get(productID).setDiscountBySupplier(supplierID, discount);
    }

    public ArrayList<Double> getDiscountsByProductId(int productID) {
        return ProductById.get(productID).getDiscountsBySupplier();
    }

    public void startConnection() {
        try {
            if(!opened_connection){
                productDAO.startConnection();
                opened_connection = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}