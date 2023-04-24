package Inventory.DataAccessLayer.Mapper;

import Inventory.BusinessLayer.Category;
import Inventory.BusinessLayer.Product;

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
    public String addCategory(String name, int id){
        try {
            connectDB.createTables();
            String query = "INSERT INTO Category (name, id) VALUES ('" + name + "', " + id + ")";
            connectDB.executeUpdate(query);
            return "Category added successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Category already exists";
        } finally {
            connectDB.close_connect();
        }
    }
    public String addProductByCategory(ArrayList<Product> products, Integer categoryId){
        try {
            connectDB.createTables();
            for (Product product : products) {
                String query = "INSERT INTO CategoryProduct (categoryID, productID) VALUES (" + categoryId + ", " + product.getMakat() + ")";
                connectDB.executeUpdate(query);
            }
            return "Products added successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Products already exists";
        } finally {
            connectDB.close_connect();
        }
    }
    public String getProductsByCategory(Integer categoryId){
        try {
            connectDB.createTables();
            String query = "SELECT * FROM CategoryProduct WHERE categoryID = " + categoryId;
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            String products = "";
            for (HashMap<String, Object> row : resultSet) {
                products += row.get("productID") + ", ";
            }
            return products;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Products already exists";
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

    public String getCategoryDiscount(Integer categoryId){
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Category WHERE id = " + categoryId;
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            String discount = "";
            for (HashMap<String, Object> row : resultSet) {
                discount += row.get("Start_Discount") + ", " + row.get("End_Discount") + ", " + row.get("Discount");
            }
            return discount;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Discount already exists";
        } finally {
            connectDB.close_connect();
        }
    }
    public String getItemsInStockByCategory(Integer categoryId){
        try {
            connectDB.createTables();
            String query = "SELECT * FROM CategoryProduct WHERE categoryID = " + categoryId;
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            String products = "";
            for (HashMap<String, Object> row : resultSet) {
                products += row.get("productID") + ", ";
            }
            return products;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Products already exists";
        } finally {
            connectDB.close_connect();
        }
    }

    public  String getCategoryByProductID(Integer productID){
        try {
            connectDB.createTables();
            String query = "SELECT * FROM CategoryProduct WHERE productID = " + productID;
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            String category = "";
            for (HashMap<String, Object> row : resultSet) {
                category += row.get("categoryID");
            }
            return category;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Products already exists";
        } finally {
            connectDB.close_connect();
        }
    }

    public String getCategoryById(Integer categoryId){
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Category WHERE id = " + categoryId;
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            String category = "";
            for (HashMap<String, Object> row : resultSet) {
                category += row.get("name");
            }
            return category;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Products already exists";
        } finally {
            connectDB.close_connect();
        }
    }

    public  String getCategoryById(){
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Category";
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            String category = "";
            for (HashMap<String, Object> row : resultSet) {
                category += row.get("id") + ", ";
            }
            return category;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Products already exists";
        } finally {
            connectDB.close_connect();
        }
    }

}
