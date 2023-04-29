package Suppliers.DataAccessLayer;

import Suppliers.BusinessLayer.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderDAO {
    private final Suppliers.DataAccessLayer.ConnectDB connectDB = ConnectDB.getInstance();

    public OrderDAO() {
    }

    public HashMap<Integer, Order> loadData() {
        HashMap<Integer, Order> orders = new HashMap<>();
        try {
            connectDB.createTables();
            // create query for extracting data of orders
            String query = "SELECT * FROM Order";
            ArrayList<HashMap<String, Object>> resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                String[] dateString = row.get("order_date").toString().split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateString[2]), Integer.parseInt(dateString[1]), Integer.parseInt(dateString[0]));
                Order order = new Order(date, (int) row.get("branch_code"));
                order.setOrderDiscount((float) row.get("orderDiscount"));
                order.setOrderStatus((String) row.get("order_status"));
                // add order details for each order
                query = "SELECT * FROM OrderDetailsByProduct WHERE order_number = " + (int) row.get("order_number");
                ArrayList<HashMap<String, Object>> details = connectDB.executeQuery(query);
                for (HashMap<String, Object> row2 : details) {
                    order.addProducts((int) row2.get("makat"), (String) row2.get("product_name"), (int) row2.get("amount"), (double)row2.get("list_price"), (double)row2.get("discount"), (double)row2.get("final_price"));
                }
                orders.put(order.getOrderNumber(), order);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
        return orders;
    }

    public HashMap<ShipmentDays, ArrayList<FixedPeriodOrder>> getFixedPeriodOrders() {
        HashMap<ShipmentDays, ArrayList<FixedPeriodOrder>> fixed_orders = new HashMap<>();
        try {
            connectDB.createTables();
            // create query for extracting data
            String query = "SELECT * FROM FixedPeriodOrder";
            ArrayList<HashMap<String, Object>> resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                ShipmentDays day = ShipmentDays.values()[(int) row.get("ship_day")];
                FixedPeriodOrder order = new FixedPeriodOrder((int) row.get("supplier_id"), (int) row.get("branch_code"), (int) row.get("makat"), (int) row.get("amount"));
                if (fixed_orders.containsKey(day)) {
                    fixed_orders.get(day).add(order);
                } else {
                    ArrayList<FixedPeriodOrder> orders = new ArrayList<>();
                    orders.add(order);
                    fixed_orders.put(day, orders);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
        return fixed_orders;
    }

    public String setOrderStatus(int order_number, String status) {
        try {
            connectDB.createTables();
            String query = "UPDATE Order SET order_status = " + status + " WHERE order_number = " + order_number;
            connectDB.executeUpdate(query);
            return "Status updated successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Order not found";
        } finally {
            connectDB.close_connect();
        }
    }

    public String setOrderDiscount(int order_number, double discount) {
        try {
            connectDB.createTables();
            String query = "UPDATE Supplier SET orderDiscount = " + discount + " WHERE order_number = " + order_number;
            connectDB.executeUpdate(query);
            return "Discount updated successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Order not found";
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
