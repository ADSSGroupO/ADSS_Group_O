package dev.adss_inventory.src.ServiceLayer;

import dev.adss_inventory.src.BuisnessLayer.Category;
import dev.adss_inventory.src.BuisnessLayer.CategoryController;

import java.util.Date;
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


}
