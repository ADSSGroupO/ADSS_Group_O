package Suppliers.Test;
import Suppliers.BusinessLayer.FixedDaysSupplier;
import Suppliers.Payment;
import Suppliers.BusinessLayer.ShipmentDays;
import org.junit.jupiter.api.Test;
import org.threeten.bp.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FixedDaysSupplierTest { // testing methods implemented in fixed days supplier class

    // create a new fixed days supplier that will be tested on methods
    FixedDaysSupplier fd_supplier = new FixedDaysSupplier("lior", 209259993, 1234567, Payment.Credit);

    @Test
    void addShipDay() {
        // adds shipping days
        fd_supplier.addShipDay(ShipmentDays.Friday);
        fd_supplier.addShipDay(ShipmentDays.Monday);
        // tests if method returns correct days
        assertEquals(true, fd_supplier.canShipOnDay(ShipmentDays.Friday));
        assertEquals(false, fd_supplier.canShipOnDay(ShipmentDays.Sunday));
        assertEquals(true, fd_supplier.canShipOnDay(ShipmentDays.Monday));
    }

    @Test
    void canShipOnDay() {
        // adds shipping day
        fd_supplier.addShipDay(ShipmentDays.Sunday);
        // tests if method returns correct days
        assertEquals(true, fd_supplier.canShipOnDay(ShipmentDays.Sunday));
    }

    @Test
    void removeShipDay() {
        // adds shipping days
        fd_supplier.addShipDay(ShipmentDays.Friday);
        fd_supplier.addShipDay(ShipmentDays.Monday);
        // tests if method returns correct days
        assertEquals(true, fd_supplier.canShipOnDay(ShipmentDays.Friday));
        assertEquals(true, fd_supplier.canShipOnDay(ShipmentDays.Monday));
        // remove friday from shipping days
        fd_supplier.removeShipDay("Friday");
        // tests if method returns false as it should
        assertEquals(false, fd_supplier.canShipOnDay(ShipmentDays.Friday));
    }

    @Test
    void getNextShippingDate() {
        // testing as of 07/04/2023 as current date
        LocalDate currentdate = LocalDate.of(2023, 4, 8);
        fd_supplier.addShipDay(ShipmentDays.Saturday);
        assertEquals(currentdate, fd_supplier.getNextShippingDate());
    }
}