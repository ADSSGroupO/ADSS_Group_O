package dev.src.Inventory.BusinessLayer;

import java.time.LocalDate;
import java.util.*;

//Controller for category as singleton
public class CategoryController {
    private static HashMap<Integer, ArrayList<Product>> productByCategory = new HashMap<Integer,ArrayList<Product>>();//dictionary contains the product by category
    // Itay: we need to consider moving this to the category controller^^^
    //tamar : been moved -  what do you say?
    //dictionary contains the category by ID
    private static HashMap<Integer, Category> categoryById = new HashMap<Integer,Category>();
    private static CategoryController instance = null;

    public CategoryController() {
    }

    public static CategoryController getInstance() {
        if (instance == null) {
            instance = new CategoryController();
        }
        return instance;
    }
    //add category
    public void addCategory(String name, int id) {
        if(categoryById.containsKey(id))
            throw new IllegalArgumentException("Category ID already exists");
        Category category = new Category(name,id);
        //add to category by id dictionary
        categoryById.put(id,category);
    }

    //add productByCategory
    public void addProductByCategory(ArrayList<Product> products,Integer categoryId) {
        productByCategory.put(categoryId,products);
    }
    public ArrayList<Product> getProductsByCategory(int categoryID){
        return productByCategory.get(categoryID);
    }

    //set discount by category
    //start format: yyyy-mm-dd
    //end format: yyyy-mm-dd
    public void setDiscountByCategory(int categoryID, float discount, String start, String end) {
        categoryById.get(categoryID).setDiscount( start, end, discount);
    }
    public double getCategoryDiscount(int categoryID){
        return categoryById.get(categoryID).getDiscount();
    }

    //get report about items in stock by category
    public List<StringBuilder> getItemsInStockByCategory(List<Integer> categoryID){
        StringBuilder item= new StringBuilder("Category Id :");
        List<StringBuilder> report=new LinkedList<StringBuilder>();
     for (int i=0; i<categoryID.size(); i++){
         item.append(" ").append(i).append(" : ");
         List<Product> products = productByCategory.get(i);
         for (Product product : products) {
             //Do we need to check the repeatability of a product?
             item.append(product.getName()).append(" product makat - ").append(product.getMakat()).append(" : current amount : ").append(product.getCurrentAmount());
         }
         report.add(item);
         item = new StringBuilder("Category Id :");
     }
        return report;
    }


    public Category getCategoryByProductID(int productID) {
        for (int i = 0; i < productByCategory.size(); i++) {
            List<Product> products = productByCategory.get(i);
            for (Product product : products) {
                if (product.getMakat() == productID) {
                    return categoryById.get(i);
                }
            }
        }
        throw new IllegalArgumentException("Product not found");
    }

    public HashMap<Integer, Category> getCategoryById() {
        return categoryById;
    }
    public Category getCategoryById(int id) {
        return categoryById.get(id);
    }
}
