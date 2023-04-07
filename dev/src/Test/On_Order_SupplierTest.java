package Test;

import Suppliers.On_Order_Supplier;
import Suppliers.Payment;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class On_Order_SupplierTest {

    // create instance of supplier that will be tested
    On_Order_Supplier oo_supplier = new On_Order_Supplier("itay", 3133, 100114, Payment.Checks);

    @Test
    void updateNextOrderDate() // & also test getNextShippingDate
     {
        oo_supplier.updateNextOrderDate(3);
        assertEquals(LocalDate.of(2023, 4, 11), oo_supplier.getNextShippingDate()); // assuming today is 8/4/2023

    }
}