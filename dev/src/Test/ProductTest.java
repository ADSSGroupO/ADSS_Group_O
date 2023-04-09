package Test;

import Suppliers.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    // object that will be tested
    Product product = new Product("milk", "43", "dairy");

    @Test
    void getProductCode() {
        assertEquals(1, product.getProductCode());
    }

    @Test
    void getProductName() {
        assertEquals("milk", product.getProductName());
    }
}