package Inventory.DataAccessLayer.Mapper;

import Inventory.BusinessLayer.Category;
import Inventory.BusinessLayer.Product;

import java.sql.SQLException;
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
    public void addCategory(String name, int id){
        try {
            connectDB.createTables();
            String query = "INSERT INTO Category (name, id) VALUES ('" + name + "', " + id + ")";
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }

    public String setDiscountByCategory(Integer categoryId, String startDiscount, String endDiscount, float discount){
        try {
            connectDB.createTables();
            String query = "UPDATE Category SET Start_Discount = '" + startDiscount + "', End_Discount = '" + endDiscount + "', Discount = " + discount + " WHERE id = " + categoryId;
            connectDB.executeUpdate(query);
            return "Discount added successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Discount already exists";
        } finally {
            connectDB.close_connect();
        }
    }


    public void startConnection() throws SQLException {
        connectDB.createTables();
        loadData();
    }
}
