package Suppliers.Test.DALTest;

import Suppliers.BusinessLayer.FixedPeriodOrder;
import Suppliers.BusinessLayer.Order;
import Suppliers.BusinessLayer.ShipmentDays;
import Suppliers.DataAccessLayer.ConnectDB;
import Suppliers.DataAccessLayer.OrderDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDAOTest {

    private final Suppliers.DataAccessLayer.ConnectDB connectDB = ConnectDB.getInstance();
    OrderDAO orderDAO = new OrderDAO();

    @BeforeEach
    void setUp() {
        try {
            connectDB.createTables();
//            connectDB.resetTables();
            // create tables
            String query = "INSERT INTO `Orders` (supplier_id, order_number, branch_code, order_date, order_status, total_price, orderDiscount) VALUES"
                    + "(201, 1, 2, '2022-04-28', 'InProcess', 500.0, 50.0),"
                    + "(202, 2, 3, '2022-04-27', 'InProcess', 700.0, 30.0),"
                    + "(203, 3, 5, '2022-04-26', 'Completed', 320.0, 80.0);"
                    + "INSERT INTO OrderDetailsByProduct (order_number, makat, product_name, amount, list_price, discount, final_price) VALUES"
                    + "(1, 101, 'Product 1', 10, 100.0, 10.0, 1000.0),"
                    + "(1, 102, 'Product 2', 5, 50.0, 20.0, 200.0),"
                    +"(2, 103, 'Product 3', 20, 10.0, 80.0, 180.0),"
                    + "(2, 104, 'Product 4', 15, 20.0, 30.0, 285.0),"
                    + "(3, 105, 'Product 5', 30, 5.0, 10.0, 150.0);"
                    + "INSERT INTO FixedPeriodOrder (branch, supplier_id, makat, amount, ship_day) VALUES"
                    + "(101, 201, 101, 50, 0),"
                    + "(102, 202, 102, 25, 0),"
                    + "(103, 203, 103, 100, 1),"
                    + "(104, 201, 104, 75, 3),"
                    + "(105, 202, 105, 200, 4);";
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }

    @Test
    void testLoadData() {
        HashMap<Integer, Order> orders = orderDAO.loadData();
        // assert that orders is not null and has expected size and values
        assertNotNull(orders);
        assertEquals(3, orders.size());
        assertTrue(orders.containsKey(1));
        assertTrue(orders.containsKey(2));
        assertEquals(2, orders.get(1).getBranch());
        assertEquals(3, orders.get(2).getBranch());
    }

    @Test
    void testGetFixedPeriodOrders() {
        HashMap<ShipmentDays, ArrayList<FixedPeriodOrder>> fixed_orders = orderDAO.getFixedPeriodOrders();
        // assert that fixed_orders is not null and has expected size and values
        assertNotNull(fixed_orders);
        assertEquals(5, fixed_orders.size());
        assertTrue(fixed_orders.containsKey(ShipmentDays.Sunday));
        assertTrue(fixed_orders.containsKey(ShipmentDays.Monday));
        assertEquals(2, fixed_orders.get(ShipmentDays.Sunday).size());
        assertEquals(1, fixed_orders.get(ShipmentDays.Monday).size());
    }

    @Test
    void testSetOrderStatus() {
        String result = orderDAO.setOrderStatus(1, "InProcess");
        // assert that result is as expected
        assertEquals("Status updated successfully", result);
    }

    @Test
    void testSetOrderDiscount() {
        String result = orderDAO.setOrderDiscount(1, 50.0);
        // assert that result is as expected
        assertEquals("Discount updated successfully", result);
    }

}

