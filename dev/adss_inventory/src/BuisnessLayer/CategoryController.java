package dev.adss_inventory.src.BuisnessLayer;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Dictionary;

public class CategoryController {
    //dictionary contains the category by Id
    private Dictionary<Integer, Category> categoryById;

    public CategoryController() {
    }

    //add category
    public void addCategory(String name, int id) {
        Category category = new Category(name,id);
        //add to category by id dictionary
        categoryById.put(id,category);
    }

    //set discount by category
    //start format: yyyy-mm-dd
    //end format: yyyy-mm-dd
    public void setDiscountByCategory(int categoryID, float discount, String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        categoryById.get(categoryID).setDiscount( startDate, endDate, discount);
    }


}
