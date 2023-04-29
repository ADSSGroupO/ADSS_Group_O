package Inventory.DataAccessLayer.Mapper;

import Inventory.BusinessLayer.Category;
import Inventory.BusinessLayer.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDAO {
    private final ConnectDB connectDB = ConnectDB.getInstance();
    private static ProductDAO instance = null;
    public ProductDAO() {
    }

    public static ProductDAO getInstance() {
        if (instance == null)
            instance = new ProductDAO();
        return instance;
    }

    public HashMap<Integer, Product> loadData() {
        HashMap<Integer, Product> products = new HashMap<>();
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Product";
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                String name = (String) row.get("name");
                int id = (int) row.get("makat");
                int minAmount = (int) row.get("minAmount");
                int categoryID = (int) row.get("category_id");
                String sub_category = (String) row.get("sub_category");
                int supplierID = (int) row.get("supplier_id");
                Product product = new Product(name, minAmount, categoryID, sub_category, id, supplierID);
                String startDiscount = (String) row.get("Start_Discount");
                String endDiscount = (String) row.get("End_Discount");
                Double discount = (Double) row.get("Discount");
                if (startDiscount != null && endDiscount != null && discount != 0.0) {
                    product.setDiscount(startDiscount, endDiscount, discount.floatValue());
                }
                products.put(id, product);
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
            String query = "INSERT INTO Product (name, currentAmount, minAmount, category_ID, sub_category, makat, supplier_id) VALUES ('" + name + "'," + 0 + ", " + minAmount + ", " + categoryID + ", '" + sub_category + "', " + makat + ", " + supplierID + ")";
            connectDB.executeUpdate(query);
            return "Product added successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Product already exists";
        } finally {
            connectDB.close_connect();
        }
    }


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
    public String addItemToProduct(int productID, int amount){
        try {
            connectDB.createTables();
            String query = "UPDATE Product SET currentAmount = currentAmount + " + amount + " WHERE makat = " + productID;
            connectDB.executeUpdate(query);
            return "Item added successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Product not found";
        } finally {
            connectDB.close_connect();
        }
    }

    public void reduceAmountOfProductByID(int productID, int amount) {
        try {
            connectDB.createTables();
            String query = "UPDATE Product SET currentAmount = currentAmount - " + amount + " WHERE makat = " + productID;
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }

    public void setDiscountByProduct(int productID, float discount, String start, String end) {
        try {
            connectDB.createTables();
            String query = "UPDATE Product SET Start_Discount = '" + start + "', End_Discount = '" + end + "', Discount = " + discount + " WHERE makat = " + productID;
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }

    public void addItem(int productID) {
        try {
            connectDB.createTables();
            String query = "UPDATE Product SET currentAmount = currentAmount + 1 WHERE makat = " + productID;
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }

    public void startConnection() throws SQLException {
        connectDB.createTables();
        loadData();
    }

    public void removeSampleData() {
        try {
            connectDB.createTables();
            connectDB.resetTables();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }


    public HashMap<Integer, Product> getProducts() {
        try{
            connectDB.createTables();
            String query = "SELECT * FROM Product";
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            HashMap<Integer, Product> products = new HashMap<>();
            for (HashMap<String, Object> row : resultSet) {
                Product product = new Product((String) row.get("name"), (int) row.get("minAmount"), (int) row.get("category_id"), (String) row.get("sub_category"), (int) row.get("makat"), (int) row.get("supplier_id"));
                if(row.get("Start_Discount")!=null && row.get("End_Discount")!=null && row.get("Discount")!=null)
                    product.setDiscount((String) row.get("Start_Discount"), (String) row.get("End_Discount"), (float) row.get("Discount"));
                products.put(product.getMakat(), product);
            }
            return products;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            connectDB.close_connect();
        }
    }
}
