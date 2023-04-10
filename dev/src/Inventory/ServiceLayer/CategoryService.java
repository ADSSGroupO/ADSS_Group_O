package dev.src.Inventory.ServiceLayer;

import dev.src.Inventory.BusinessLayer.CategoryController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    //connect to CategoryController controller
    CategoryController categoryController;
    //constructor
    public CategoryService() {
        categoryController = CategoryController.getInstance();
    }
    //addCategory
    public void addCategory(String name, int id) {
        categoryController.addCategory(name, id);
    }

    //setDiscountByCategory
    public void setDiscountByCategory(int categoryID, float discount , String start, String end) {
        categoryController.setDiscountByCategory(categoryID, discount, start, end);
    }
    //get report about items in stock by category
    /*how would we send back items*/
    public ArrayList<StringBuilder> getReportOfItemsInStockByCategory(List<Integer> categoryID){
        try {
            return categoryController.getItemsInStockByCategory(categoryID);
        }
        catch (Exception e){
            StringBuilder warningMassage = new StringBuilder(e.getMessage());
            ArrayList<StringBuilder> massage = new ArrayList<StringBuilder>();
            massage.add(warningMassage);
            return massage;
        }
    }



}
