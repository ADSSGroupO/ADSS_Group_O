package Inventory.DataAccessLayer.Mapper;

import Inventory.BusinessLayer.Category;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryDAO {
    private final ConnectDB connectDB = ConnectDB.getInstance();
    public CategoryDAO() {
    }
    public HashMap<Category, String> loadData() {
        HashMap<Category, String> categories = new HashMap<>();
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Category";
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                Category category = new Category((String) row.get("name"), (int) row.get("id"));
                if(row.get("Start_Discount")!=null && row.get("End_Discount")!=null && row.get("Discount")!=null)
                    category.setDiscount((String) row.get("Start_Discount"), (String) row.get("End_Discount"), (float) row.get("Discount"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
        return categories;
    }

}
