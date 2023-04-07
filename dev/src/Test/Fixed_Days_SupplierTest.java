package Test;

import Suppliers.Fixed_Days_Supplier;
import Suppliers.Payment;
import Suppliers.Shipment_Days;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class Fixed_Days_SupplierTest { // testing methods implemented in fixed days supplier class

    // create a new fixed days supplier that will be tested on methods
    Fixed_Days_Supplier fd_supplier = new Fixed_Days_Supplier("lior", 209259993, 1234567, Payment.Credit);

    @Test
    void addShipDay() {
        // adds shipping days
        fd_supplier.addShipDay(Shipment_Days.Friday);
        fd_supplier.addShipDay(Shipment_Days.Monday);
        // tests if method returns correct days
        assertTrue(fd_supplier.canShipOnDay(Shipment_Days.Friday));
        assertFalse(fd_supplier.canShipOnDay(Shipment_Days.Sunday));
        assertTrue(fd_supplier.canShipOnDay(Shipment_Days.Monday));
    }

    @Test
    void canShipOnDay() {
        // adds shipping day
        fd_supplier.addShipDay(Shipment_Days.Sunday);
        // tests if method returns correct days
        assertTrue(fd_supplier.canShipOnDay(Shipment_Days.Sunday));
    }

    @Test
    void removeShipDay() {
        // adds shipping days
        fd_supplier.addShipDay(Shipment_Days.Friday);
        fd_supplier.addShipDay(Shipment_Days.Monday);
        // tests if method returns correct days
        assertTrue(fd_supplier.canShipOnDay(Shipment_Days.Friday));
        assertTrue(fd_supplier.canShipOnDay(Shipment_Days.Monday));
        // remove friday from shipping days
        fd_supplier.removeShipDay("Friday");
        // tests if method returns false as it should
        assertFalse(fd_supplier.canShipOnDay(Shipment_Days.Friday));
    }

    @Test
    void getNextShippingDate() {
        // testing as of 07/04/2023 as current date
        LocalDate currentdate = LocalDate.of(2023, 4, 8);
        fd_supplier.addShipDay(Shipment_Days.Saturday);
        assertEquals(currentdate, fd_supplier.getNextShippingDate());
    }
}