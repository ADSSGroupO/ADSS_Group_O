package Suppliers.Test;

import Suppliers.BusinessLayer.OnOrderSupplier;
import Suppliers.Payment;
import org.junit.jupiter.api.Test;
import org.threeten.bp.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

class OnOrderSupplierTest {

    // create instance of supplier that will be tested
    OnOrderSupplier oo_supplier = new OnOrderSupplier("itay", 3133, 100114, Payment.Checks);

    @Test
    void getNextShippingDate() // also tests setNumberOfDaysToNextOrder
     {
         // setting input
         oo_supplier.setNumberOfDaysToNextOrder(3);
         // check date is correct, assuming today is 8/4/23
        assertEquals(LocalDate.of(2023, 4, 11), oo_supplier.getNextShippingDate());

    }
}