package Suppliers.DataAccessLayer;

import Suppliers.BusinessLayer.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class SupplierDAO {
    private final Suppliers.DataAccessLayer.ConnectDB connectDB = ConnectDB.getInstance();

    public SupplierDAO() {
    }

    public HashMap<Integer, Supplier> loadData() {
        HashMap<Integer, Supplier> suppliers = new HashMap<>();
        try {
            connectDB.createTables();
            // create query for extracting data of fixed days suppliers
            String query = "SELECT * FROM Supplier WHERE supplier_id IN (SELECT supplier_id FROM FixedSupplierDays)";
            ArrayList<HashMap<String, Object>> resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                int supplier_id = (int) row.get("supplier_id");
                Supplier fixed_supplier = new FixedDaysSupplier((String) row.get("supplier_name"), supplier_id, (int) row.get("bank_account"), Payment.valueOf((String) row.get("payment_method")));
                // get days of fixed days supplier from DB fixeddays table
                query = "SELECT * FROM FixedSupplierDays WHERE supplier_id = " + (int) row.get("supplier_id");
                ArrayList<HashMap<String, Object>> days = connectDB.executeQuery(query);
                for (HashMap<String, Object> day : days) {
                    ((FixedDaysSupplier) fixed_supplier).addShipDay((int) day.get("day"));
                }
                suppliers.put(supplier_id, fixed_supplier);
            }

            // create query for extracting data of on order suppliers
            query = "SELECT * FROM Supplier WHERE supplier_id IN (SELECT supplier_id FROM OnOrderSupplier)";
            resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                int supplier_id = (int) row.get("supplier_id");
                Supplier on_order_supplier = new OnOrderSupplier((String) row.get("supplier_name"), supplier_id, (int) row.get("bank_account"), Payment.valueOf((String) row.get("payment_method")));
                query = "SELECT numberOfDaysToNextOrder FROM Supplier WHERE supplier_id = " + (int) row.get("supplier_id");
                int days = (int) connectDB.executeQuery(query).get(0).get("numberOfDaysToNextOrder");
                ((OnOrderSupplier) on_order_supplier).setNumberOfDaysToNextOrder(days);
                suppliers.put(supplier_id, on_order_supplier);
            }
            // create query for extracting data of suppliers of no transport suppliers
            query = "SELECT * FROM Supplier WHERE supplier_id IN (SELECT supplier_id FROM NoTransportSupplier)";
            resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                String query2 = "SELECT * FROM NoTransportSupplier WHERE supplier_id = " + (int) row.get("supplier_id");
                String address = (String) connectDB.executeQuery(query2).get(0).get("address");
                Supplier no_transport_supplier = new NoTransportSupplier((String) row.get("supplier_name"), (int) row.get("supplier_id"), (int) row.get("bank_account"), Payment.valueOf((String) row.get("payment_method")), address);
                int supplier_id = (int) row.get("supplier_id");
                if (connectDB.executeQuery(query2).get(0).get("nextDeliveryDate") != null) {
                    String[] date = connectDB.executeQuery(query2).get(0).get("nextDeliveryDate").toString().split("-");
                    ((NoTransportSupplier) no_transport_supplier).setNextDeliveryDate(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
                }
                suppliers.put(supplier_id, no_transport_supplier);
            }

            // get contacts of each supplier
            query = "SELECT * FROM Contact";
            resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                Supplier supplier = suppliers.get((int) row.get("supplier_id"));
                supplier.addContact((String) row.get("contact_name"), (String) row.get("cellphone"));
            }

            // get discounts of each supplier
            query = "SELECT * FROM DiscountByOrder";
            resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                Supplier supplier = suppliers.get((int) row.get("supplier_id"));
                if ((boolean)row.get("ByPrice")) {
                    supplier.addOrderDiscount(new DiscountOfPriceByOrder((double)row.get("val"), (double)row.get("minimalPrice")));
                }
                else {
                    supplier.addOrderDiscount(new DiscountOfPercentageByOrder((double)row.get("val"), (double)row.get("minimalPrice")));
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
        return suppliers;
    }


/*
    public HashMap<Supplier, String> loadData() {
        HashMap<Supplier, String> suppliers = new HashMap<>();
        try {
            connectDB.createTables();
            // create query for extracting data of fixed days suppliers
            String query = "SELECT * FROM Supplier WHERE supplier_id IN (SELECT supplier_id FROM FixedSupplierDays)";
            ArrayList<HashMap<String, Object>> resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                Supplier fixed_supplier = new FixedDaysSupplier((String) row.get("supplier_name"), (int) row.get("supplier_id"), (int) row.get("bank_account"), Payment.valueOf((String) row.get("payment_method")));
                // get days of fixed days supplier from DB fixeddays table
                query = "SELECT * FROM FixedSupplierDays WHERE supplier_id = " + (int) row.get("supplier_id");
                ArrayList<HashMap<String, Object>> days = connectDB.executeQuery(query);
                for (HashMap<String, Object> day : days) {
                    ((FixedDaysSupplier) fixed_supplier).addShipDay((int) day.get("day"));
                }
                suppliers.put(fixed_supplier, "FixedDaysSupplier");
            }

            // create query for extracting data of on order suppliers
            query = "SELECT * FROM Supplier WHERE supplier_id IN (SELECT supplier_id FROM OnOrderSupplier)";
            resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                Supplier on_order_supplier = new OnOrderSupplier((String) row.get("supplier_name"), (int) row.get("supplier_id"), (int) row.get("bank_account"), Payment.valueOf((String) row.get("payment_method")));
                query = "SELECT numberOfDaysToNextOrder FROM Supplier WHERE supplier_id = " + (int) row.get("supplier_id");
                int days = (int) connectDB.executeQuery(query).get(0).get("numberOfDaysToNextOrder");
                ((OnOrderSupplier) on_order_supplier).setNumberOfDaysToNextOrder(days);
                suppliers.put(on_order_supplier, "OnOrderSupplier");
            }
            // create query for extracting data of suppliers of no transport suppliers
            query = "SELECT * FROM Supplier WHERE supplier_id IN (SELECT supplier_id FROM NoTransportSupplier)";
            resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                String query2 = "SELECT * FROM NoTransportSupplier WHERE supplier_id = " + (int) row.get("supplier_id");
                String address = (String) connectDB.executeQuery(query2).get(0).get("address");
                Supplier no_transport_supplier = new NoTransportSupplier((String) row.get("supplier_name"), (int) row.get("supplier_id"), (int) row.get("bank_account"), Payment.valueOf((String) row.get("payment_method")), address);
                if (connectDB.executeQuery(query2).get(0).get("nextDeliveryDate") != null) {
                    String[] date = connectDB.executeQuery(query2).get(0).get("nextDeliveryDate").toString().split("-");
                    ((NoTransportSupplier) no_transport_supplier).setNextDeliveryDate(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
                }
                suppliers.put(no_transport_supplier, "NoTransportSupplier");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
        return suppliers;
    }

    // function that adds a supplier to Supplier table
    public String addSupplier(String name, int id, int bank, Payment pay) {
        try {
            connectDB.createTables();
            String query = "INSERT INTO Supplier (supplier_name, supplier_id, bank_account, payment) VALUES ('" + name + "'," + id + ", " + bank + ", " + pay.toString() + ")";
            connectDB.executeUpdate(query);
            return "Supplier added successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Suppplier already exists";
        } finally {
            connectDB.close_connect();
        }
    } */

    // function that adds shipping days for fixed days supplier
    public String addDaysForFixedSupplier(int id, ArrayList<Integer> days) {
        try {
            connectDB.createTables();
            for (int day : days) {
                String query = "INSERT INTO FixedSupplierDays (supplier_id, day) VALUES ('" + id + "'," + day + ")";
                connectDB.executeUpdate(query);
            }
            return "Days were added successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Days already exists";
        } finally {
            connectDB.close_connect();
        }
    }

    // function that adds information of no transport supplier
    public String addInformationForNoTransport(int id, String address, LocalDate date) {
        try {
            connectDB.createTables();
            String query = "INSERT INTO NoTransportSupplierInfo (supplier_id, address, nextDeliveryDate) VALUES ('" + id + "'," + address + "'," + date.toString() + ")";
            connectDB.executeUpdate(query);
            return "Address and date were added successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Supplier info already exists";
        } finally {
            connectDB.close_connect();
        }
    }

    // function that adds number of days for on order supplier
    public String addNumOfDaysForOnOrder(int id, int numOfDays) {
        try {
            connectDB.createTables();
            String query = "INSERT INTO OnOrderSupplierNumOfDays (supplier_id, numberOfDaysToNextOrder) VALUES ('" + id + "'," + numOfDays + ")";
            connectDB.executeUpdate(query);
            return "Number of days was added successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Number of days already exists";
        } finally {
            connectDB.close_connect();
        }
    }

    public String setName(int id, String name) {
        try {
            connectDB.createTables();
            String query = "UPDATE Supplier SET supplier_name = " + name + " WHERE supplier_id = " + id;
            connectDB.executeUpdate(query);
            return "Name updated successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Supplier not found";
        } finally {
            connectDB.close_connect();
        }
    }

    public String setBankAccount(int id, int bank) {
        try {
            connectDB.createTables();
            String query = "UPDATE Supplier SET bank_account = " + bank + " WHERE supplier_id = " + id;
            connectDB.executeUpdate(query);
            return "Bank account updated successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Supplier not found";
        } finally {
            connectDB.close_connect();
        }
    }

    public String setPayment(int id, String payment) {
        try {
            connectDB.createTables();
            String query = "UPDATE Supplier SET payment = " + payment + " WHERE supplier_id = " + id;
            connectDB.executeUpdate(query);
            return "Payment method updated successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Supplier not found";
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
}

