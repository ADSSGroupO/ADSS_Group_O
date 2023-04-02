package dev.adss_inventory.src.ServiceLayer;

import dev.adss_inventory.src.BuisnessLayer.CategoryController;

import java.util.Date;

public class CategoryService {
    //connect to CategoryController controller
    CategoryController categoryController;
    //addCategory
    public void addCategory(String name, int id) {
        categoryController.addCategory(name, id);
    }

    //setDiscountByCategory
    public void setDiscountByCategory(int categoryID, float discount , String start, String end) {
        categoryController.setDiscountByCategory(categoryID, discount, start, end);
    }
}
