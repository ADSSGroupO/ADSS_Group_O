package Suppliers.DataAccessLayer;

import Suppliers.BusinessLayer.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class SupplyAgreementDAO {

    private final Suppliers.DataAccessLayer.ConnectDB connectDB = ConnectDB.getInstance();

    public SupplyAgreementDAO() {
    }

    public HashMap<Integer, ArrayList<SupplyAgreement>> loadData() {
        HashMap<Integer, ArrayList<SupplyAgreement>> agreements = new HashMap<>();
        try {
            connectDB.createTables();
            // create query for extracting data of supply agreements
            String query = "SELECT * FROM SupplyAgreement";
            ArrayList<HashMap<String, Object>> resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                SupplyAgreement supplyagree = new SupplyAgreement((int) row.get("makat"), (double) row.get("list_price"), (int) row.get("catalog_code"), (int) row.get("amount"));
                query = "SELECT * FROM DiscountByProduct WHERE supplier_id = " + row.get("supplier_id") + " AND makat = " + row.get("makat");
                ArrayList<HashMap<String, Object>> resultSet2 = connectDB.executeQuery(query);
                for (HashMap<String, Object> row2 : resultSet2) {
                    DiscountByProduct discount;
                    if ((boolean)row2.get("ByPrice")) {
                        discount = new DiscountOfPriceByProduct((double) row2.get("val"), (int) row2.get("numOfProducts"));
                    }
                    else {
                        discount = new DiscountOfPercentageByProduct((double) row2.get("val"), (int) row2.get("numOfProducts"));
                    }
                    supplyagree.addDiscount(discount);
                }
                ArrayList<SupplyAgreement> current_supplier = agreements.get((int) row.get("supplier_id"));
                if (current_supplier == null) {
                    current_supplier = new ArrayList<>();
                }
                current_supplier.add(supplyagree);
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
        return agreements;
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
}
