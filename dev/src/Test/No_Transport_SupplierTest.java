package Test;

import Suppliers.No_Transport_Supplier;
import Suppliers.Payment;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class No_Transport_SupplierTest {

    // instance of no transport supplier for test
    No_Transport_Supplier nt_supplier = new No_Transport_Supplier("ido", 1234, 707111, Payment.TransferToAccount, "gaon hayarden 38");

    @Test
    void getAddress() {
        assertEquals("gaon hayarden 38", nt_supplier.getAddress());
    }

    @Test
    void setAddress() {
        nt_supplier.setAddress("mapu");
        assertEquals("mapu", nt_supplier.getAddress());
    }

    @Test
    void setNextDeliveryDate() // also tests getNextDeliveryDate
    {
        LocalDate date = LocalDate.of(1998, 12, 24);
        nt_supplier.setNextDeliveryDate(24, 12, 1998);
        assertEquals(date, nt_supplier.getNextShippingDate());
    }
}