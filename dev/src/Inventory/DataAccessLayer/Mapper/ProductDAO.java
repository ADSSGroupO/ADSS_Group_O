package Inventory.DataAccessLayer.Mapper;

import Inventory.BusinessLayer.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductDAO {
    private final ConnectDB connectDB = ConnectDB.getInstance();
    public ProductDAO() {
    }
    public HashMap<Product, String> loadData() {
        HashMap<Product, String> products = new HashMap<>();
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Product";
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                Product product = new Product((String) row.get("name"), (int) row.get("minAmount"), (int) row.get("categoryID"), (String) row.get("sub_category"), (int) row.get("makat"), (int) row.get("supplierID"));
                if(row.get("Start_Discount")!=null && row.get("End_Discount")!=null && row.get("Discount")!=null)
                    product.setDiscount((String) row.get("Start_Discount"), (String) row.get("End_Discount"), (float) row.get("Discount"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
        return products;
    }
    public String addProduct(String name, int minAmount, int categoryID, String sub_category, int makat, int supplierID){
        try {
            connectDB.createTables();
            String query = "INSERT INTO Product (name, minAmount, categoryID, sub_category, makat, supplierID) VALUES ('" + name + "', " + minAmount + ", " + categoryID + ", '" + sub_category + "', " + makat + ", " + supplierID + ")";
            connectDB.executeUpdate(query);
            return "Product added successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Product already exists";
        } finally {
            connectDB.close_connect();
        }
    }

    public  String getProduct(int productID){
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Product WHERE makat = " + productID;
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            if(resultSet.size() == 0)
                return "Product not found";
            return "Product found";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Product not found";
        } finally {
            connectDB.close_connect();
        }
    }
    public  String getProductsByCategory(int categoryID){
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Product WHERE categoryID = " + categoryID;
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            String products = "";
            for (HashMap<String, Object> row : resultSet) {
                products += row.get("id") + ", ";
            }
            return products;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Product not found";
        } finally {
            connectDB.close_connect();
        }
    }

    public String getProductById(Integer productId){
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Product WHERE makat = " + productId;
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            if(resultSet.size() == 0)
                return "Product not found";
            return "Product found";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Product not found";
        } finally {
            connectDB.close_connect();
        }
    }
    //this function need to be updated from the product itself.
    public String setMinimum(int productID, int minAmount){
        try {
            connectDB.createTables();
            String query = "UPDATE Product SET minAmount = " + minAmount + " WHERE makat = " + productID;
            connectDB.executeUpdate(query);
            return "Minimum amount updated successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Product not found";
        } finally {
            connectDB.close_connect();
        }
    }
    //this function need to be updated from the product itself.(maybe)
    public String setDiscount(int productID, String start, String end, float discount){
        try {
            connectDB.createTables();
            String query = "UPDATE Product SET Start_Discount = '" + start + "', End_Discount = '" + end + "', Discount = " + discount + " WHERE makat = " + productID;
            connectDB.executeUpdate(query);
            return "Discount updated successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Product not found";
        } finally {
            connectDB.close_connect();
        }
    }
    public  String getProductDiscount(int productID){
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Product WHERE makat = " + productID;
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            if(resultSet.size() == 0)
                return "Product not found";
            return "Product found";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Product not found";
        } finally {
            connectDB.close_connect();
        }
    }
    public String addItemToProduct(int productID, int amount){
        try {
            connectDB.createTables();
            String query = "UPDATE Product SET amount = amount + " + amount + " WHERE makat = " + productID;
            connectDB.executeUpdate(query);
            return "Item added successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Product not found";
        } finally {
            connectDB.close_connect();
        }
    }
     public String getAmountOfProduct(int productID) {
         try {
             connectDB.createTables();
             String query = "SELECT amount FROM Product WHERE makat = " + productID;
             ArrayList<HashMap<String, Object>> resultSet = connectDB.executeQuery(query);
             if (resultSet.size() == 0)
                 return "Product not found";
             return "Product found";
         } catch (Exception e) {
             System.out.println(e.getMessage());
             return "Product not found";
         } finally {
             connectDB.close_connect();
         }
     }



}
