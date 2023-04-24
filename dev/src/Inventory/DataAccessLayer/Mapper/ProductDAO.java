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

}
