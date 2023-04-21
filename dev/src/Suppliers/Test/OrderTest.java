package Suppliers.Test;

import Suppliers.*;
import Suppliers.BusinessLayer.DiscountOfPriceByOrder;
import Suppliers.BusinessLayer.FixedDaysSupplier;
import org.junit.jupiter.api.Test;
import org.threeten.bp.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    // initialize objects for order
    FixedDaysSupplier supplier = new FixedDaysSupplier("lior", 209259993, 100100, Payment.Credit);
    Order order = new Order(supplier, 4);

    @Test
    void getOrderNumber() {
        // because created 8 orders before, this should be the number of orders
        assertEquals(9, order.getOrderNumber());
        Order order2 = new Order(supplier, 3);
        assertEquals(10, order2.getOrderNumber());
    }

    @Test
    void addProducts() { // also tests getOrderDetailsOfProduct
        OrderDetailsByProduct details = order.addProducts(1, "popcorn", 4, 5, 10, 10);
        assertEquals(details, order.getOrderDetailsOfProduct(1));
    }

    @Test
    void cancelProduct() {
        // add product and test it was added
        OrderDetailsByProduct details = order.addProducts(1, "popcorn", 4, 5, 10, 10);
        assertEquals(details, order.getOrderDetailsOfProduct(1));
        // remove listing and test it was deleted
        order.cancelProduct(1);
        assertNull(order.getOrderDetailsOfProduct(1));
    }

    @Test
    void cancelOrder() {
        // initialize new order
        Order order2 = new Order(supplier, 3);
        // cancel order
        order2.cancelOrder();
        // test the status changed to cancelled
        assertEquals(Status.Canceled, order2.getOrderStatus());
    }

    @Test
    void confirmDelivery() {
        // initialize new order
        Order order2 = new Order(supplier, 3);
        // confirm order
        order2.confirmDelivery();
        // test the status changed to completed
        assertEquals(Status.Completed, order2.getOrderStatus());
    }

    @Test
    void getBranch() {
        assertEquals(4, order.getBranch());
    }

    @Test
    void getDateOfOrder() {
        // adds sunday as ship day
        supplier.addShipDay(ShipmentDays.Sunday);
        // initialize new order
        Order order2 = new Order(supplier, 3);
        // assuming today is saturday 8/4, delivery date should be 9/4
        assertEquals(LocalDate.of(2023, 4, 9), order2.getDateOfOrder());
    }

    @Test
    void getOrderStatus() {
        // initialize new order
        Order order2 = new Order(supplier, 3);
        // check status is InProcess
        assertEquals(Status.InProcess, order2.getOrderStatus());
        // confirm order
        order2.confirmDelivery();
        // test the status changed to completed
        assertEquals(Status.Completed, order2.getOrderStatus());
    }

    @Test
    void getTotalPrice() {
        order.addProducts(1, "popcorn", 4, 5, 10, 10);
        assertEquals(10, order.getTotalPrice());
    }

    @Test
    void applyOrderDiscount() {
        order.addProducts(1, "popcorn", 4, 5, 10, 10);
        // initialize new discount and add to supplier
        DiscountOfPriceByOrder discount = new DiscountOfPriceByOrder(5, 5);
        supplier.addOrderDiscount(discount);
        // apply discount
        order.applyOrderDiscount(supplier.getDiscounts());
        // test discount was applied correctly
        assertEquals(5, order.getTotalPrice());
    }
}