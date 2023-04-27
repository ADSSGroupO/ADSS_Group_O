package Inventory.DataAccessLayer.Mapper;

import Inventory.BusinessLayer.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryProductDAO {
    private final ConnectDB connectDB = ConnectDB.getInstance();

    public CategoryProductDAO() {

    }

    public void insert(int categoryID, int productID) {
        try {
            connectDB.createTables();
            String query = "INSERT INTO Category_Product (categoryID, productID) VALUES (" + categoryID + ", " + productID + ")";
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }
    public String addProductByCategory(ArrayList<Product> products, Integer categoryId){
        try {
            connectDB.createTables();
            for (Product product : products) {
                String query = "INSERT INTO Category_Product (categoryID, productID) VALUES (" + categoryId + ", " + product.getMakat() + ")";
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
    public void delete(int categoryID, int productID) {
        try {
            connectDB.createTables();
            String query = "DELETE FROM Category_Product WHERE categoryID = " + categoryID + " AND productID = " + productID;
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }

}
