package Inventory.DataAccessLayer.Mapper;

import java.util.HashMap;

public class CategoryProductDAO {
    private final ConnectDB connectDB = ConnectDB.getInstance();

    public CategoryProductDAO() {

    }

    public void insert(int categoryID, int productID) {
        try {
            connectDB.createTables();
            String query = "INSERT INTO CategoryProduct (categoryID, productID) VALUES (" + categoryID + ", " + productID + ")";
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }

    public void delete(int categoryID, int productID) {
        try {
            connectDB.createTables();
            String query = "DELETE FROM CategoryProduct WHERE categoryID = " + categoryID + " AND productID = " + productID;
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }

}
