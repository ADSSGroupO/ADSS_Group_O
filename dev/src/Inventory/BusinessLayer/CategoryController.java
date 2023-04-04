package dev.src.Inventory.BusinessLayer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Dictionary;
//Controller for category as singleton
public class CategoryController {
    private static HashMap<Integer, List<Product>> productByCategory = new HashMap<Integer,List<Product>>();//dictionary contains the product by category
    // Itay: we need to consider moiving this to the category controller^^^
    //tamar : been moved -  what do you say?
    //dictionary contains the category by Id
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
        Category category = new Category(name,id);
        //add to category by id dictionary
        categoryById.put(id,category);
    }

    //add productByCategory
    public void addProductByCategory(List<Product> products,Integer categoryId) {
        productByCategory.put(categoryId,products);
    }
    public List<Product> getProductsByCategory(int categoryID){
        return productByCategory.get(categoryID);
    }

    //set discount by category
    //start format: yyyy-mm-dd
    //end format: yyyy-mm-dd
    public void setDiscountByCategory(int categoryID, float discount, String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        categoryById.get(categoryID).setDiscount( startDate, endDate, discount);
    }
    public float getCategoryDiscount(int categoryID){
        return categoryById.get(categoryID).getDiscount();
    }



}
