package dev.src.Inventory.test;

import dev.src.Inventory.BusinessLayer.Item;
import dev.src.Inventory.BusinessLayer.Product;
import dev.src.Inventory.ServiceLayer.ServiceController;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestServiceController {

//    @BeforeAll
//    public static void setupAll(){
//        System.out.println("Should print before all the tests");
//    }

    @BeforeEach
    void setUp() {
        LocalDate expirationDate = LocalDate.of(2023, 4, 25);
        ServiceController service = ServiceController.getInstance();
        service.addCategory("Dairy products", 0);
        service.addCategory("Meat products", 1);
        service.addCategory("Housewares", 2);
        service.addProduct("Milk", 5, 0, 0, 0);
        service.addProduct("Cheese", 2, 0, 1, 0);
        service.addProduct("Salami", 2, 1, 2, 0);
        service.addProduct("Beef Fillet ", 2, 1, 3, 0);
        service.addProduct("Broom", 2, 2, 4, 0);
        service.addProduct("Pot", 2, 2, 5, 0);
        service.addItem("Tnuva", 0, "Milk 3%", expirationDate, 6.9, 0, 0);
        service.addItem("Tnuva", 1, "Milk 3%", expirationDate, 6.9, 0, 0);
        service.addItem("Tnuva", 2, "Milk 3%", expirationDate, 6.9, 0, 0);
        service.addItem("Tnuva", 3, "Milk 3%", expirationDate, 6.9, 0, 0);
        service.addItem("Tnuva", 4, "Milk 3%", expirationDate, 6.9, 0, 0);
        service.addItem("Tnuva", 5, "Milk 3%", expirationDate, 6.9, 0, 0);
        service.addItem("Tnuva", 6, "Milk 3%", expirationDate, 6.9, 0, 0);
        service.addItem("Tnuva", 7, "Milk 3%", expirationDate, 6.9, 0, 0);
        service.addItem("Tnuva", 8, "Cheese 3%", expirationDate, 10, 0, 1);
        service.addItem("Tnuva", 9, "Cheese 3%", expirationDate, 10, 0, 1);
        service.addItem("Tnuva", 10, "Cheese 3%", expirationDate, 10, 0, 1);
        service.addItem("Tnuva", 11, "Cheese 3%", expirationDate, 10, 0, 1);
        service.addItem("Tnuva", 12, "Cheese 3%", expirationDate, 10, 0, 1);
        service.addItem("Tnuva", 13, "Cheese 3%", expirationDate, 10, 0, 1);
        service.addItem("Zoglobek", 14, "Salami 5%", expirationDate, 15, 1, 2);
        service.addItem("Zoglobek", 15, "Salami 5%", expirationDate, 15, 1, 2);
        service.addItem("Zoglobek", 16, "Salami 5%", expirationDate, 15, 1, 2);
        service.addItem("Zoglobek", 17, "Salami 5%", expirationDate, 15, 1, 2);
        service.addItem("Zoglobek", 18, "Salami 5%", expirationDate, 15, 1, 2);
        service.addItem("Zoglobek", 19, "Salami 5%", expirationDate, 15, 1, 2);
        service.addItem("Havat HaBokrim", 20, "Beef Fillet", expirationDate, 100, 1, 3);
        service.addItem("Havat HaBokrim", 21, "Beef Fillet", expirationDate, 100, 1, 3);
        service.addItem("Havat HaBokrim", 22, "Beef Fillet", expirationDate, 100, 1, 3);
        service.addItem("Havat HaBokrim", 23, "Beef Fillet", expirationDate, 100, 1, 3);
        service.addItem("Havat HaBokrim", 24, "Beef Fillet", expirationDate, 100, 1, 3);
        service.addItem("Havat HaBokrim", 25, "Beef Fillet", expirationDate, 100, 1, 3);
        service.addItem("Havat HaBokrim", 26, "Beef Fillet", expirationDate, 100, 1, 3);
        service.addItem("Soltam", 27, "Pot 1L", null, 55, 2, 4);
        service.addItem("Soltam", 28, "Pot 1L", null, 55, 2, 4);
        service.addItem("Soltam", 29, "Pot 0.5L", null, 55, 2, 4);
        service.addItem("Soltam", 30, "Pot 0.5L", null, 55, 2, 4);
        service.addItem("Soltam", 31, "Pot 0.5L", null, 55, 2, 4);
        service.addItem("Ruhama", 32, "Broom", null, 15, 2, 5);
        service.addItem("Ruhama", 33, "Broom", null, 15, 2, 5);
        service.addItem("Ruhama", 34, "Broom", null, 15, 2, 5);
        service.addItem("Ruhama", 35, "Broom", null, 15, 2, 5);
        service.addItem("Ruhama", 36, "Broom", null, 15, 2, 5);
        service.addItem("Ruhama", 37, "Broom", null, 15, 2, 5);
        service.addItem("Ruhama", 38, "Broom", null, 15, 2, 5);
        service.addItem("Ruhama", 39, "Broom", null, 15, 2, 5);
    }


    @org.junit.jupiter.api.Test
    void addCategory() {
        ServiceController service = ServiceController.getInstance();
        assertFalse(service.addCategory("Dairy products", 0));
        assertTrue(service.addCategory("Dairy products", 4));
        assertFalse(service.addCategory("HelloWorld", 0));
    }

    @org.junit.jupiter.api.Test
    void addProduct() {
        ServiceController service = ServiceController.getInstance();
        assertFalse(service.addProduct("Milk", 5, 0, 0, 0));
        assertTrue(service.addProduct("Milk", 5, 0, 6, 0));
        assertFalse(service.addProduct("Milky", 5, 0, 0, 0));
    }

    @org.junit.jupiter.api.Test
    void addItem() {
        ServiceController service = ServiceController.getInstance();
        assertFalse(service.addItem("Soltam", 27, "Pot 1L", null, 55, 2, 4));
        assertTrue(service.addItem("Soltam", 45, "Pot 1L", null, 55, 2, 4));
        assertFalse(service.addItem("Soltam", 44, "Pot 1L", null, 55, 5, 4));
        assertFalse(service.addItem("Soltam", 43, "Pot 1L", null, 55, 5, 8));
    }

    @org.junit.jupiter.api.Test
    void itemSold() {
        //ToDo: there are some problems here that we need to discuss about
        ServiceController service = ServiceController.getInstance();
        assertTrue(service.itemSold(0,0));
        assertFalse(service.itemSold(5,0));
        assertFalse(service.itemSold(2,0));
        assertTrue(service.itemSold(1,14));
    }

    @org.junit.jupiter.api.Test
    void setMinimum() {
        ServiceController service = ServiceController.getInstance();
        ArrayList<Product> products = service.getProductsByCategory(0);
        Product p = products.get(0);
        assertEquals(5, p.getMinAmount());
        service.setMinimum(3, 10,p.getMakat());
        assertNotEquals(30, p.getMinAmount());
        assertEquals(5, p.getMinAmount());
        service.setMinimum(1, 3,p.getMakat());
        assertEquals(3, p.getMinAmount());

    }

    @org.junit.jupiter.api.Test
    void moveItemToStore() {
        ServiceController service = ServiceController.getInstance();
        ArrayList<Item> items = service.getItemsInStock(0);
        Item i = items.get(0);
        assertEquals(0, service.getItemsInStore().size());
        service.moveItemToStore(0, i.getBarcode());
        assertEquals(1, service.getItemsInStore().size());
    }

    @org.junit.jupiter.api.Test
    void setDiscountByProduct() {
        ServiceController service = ServiceController.getInstance();
        Item i = service.getItemsInStock(0).get(0);
        assertEquals(i.getPrice(), service.getPrice(i.getBarcode()));
        service.setDiscountByProduct(0, 10, "2023-01-01", "2023-08-01");
        assertEquals(0.9*(i.getPrice()), service.getPrice(i.getBarcode()));
    }

    @org.junit.jupiter.api.Test
    void setDiscountByCategory() {
        ServiceController service = ServiceController.getInstance();
        Item i = service.getItemsInStock(2).get(0);
        assertEquals(i.getPrice(), service.getPrice(i.getBarcode()));
        service.setDiscountByCategory(2, 10, "2023-01-01", "2023-08-01");
        assertEquals(0.9*(i.getPrice()), service.getPrice(i.getBarcode()));
    }

    @org.junit.jupiter.api.Test
    void getMinDiscount() {
        ServiceController service = ServiceController.getInstance();
        Item i = service.getItemsInStock(1).get(0);
        assertEquals(i.getPrice(), service.getPrice(i.getBarcode()));
        service.setDiscountByCategory(1, 15, "2023-01-01", "2023-08-01");
        service.setDiscountByProduct(2, 10, "2023-01-01", "2023-08-01");
        assertEquals(0.85*(i.getPrice()), service.getPrice(i.getBarcode()));
    }

//    @org.junit.jupiter.api.Test
//    void getInventoryReport() {
//        ServiceController service = ServiceController.getInstance();
////        StringBuilder sb = new StringBuilder();
////        ArrayList<Item> items = service.getItemsInStock(0);
////        for (Item i : items) {
////            sb.append(i.toString());
////    }
////        assertEquals(sb.toString(), service.getInventoryReport());
////    }
//        service.getInventoryReport();
//    }
}
