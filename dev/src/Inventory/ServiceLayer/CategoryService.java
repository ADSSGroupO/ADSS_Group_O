package dev.src.Inventory.ServiceLayer;

import dev.src.Inventory.BuisnessLayer.CategoryController;

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
