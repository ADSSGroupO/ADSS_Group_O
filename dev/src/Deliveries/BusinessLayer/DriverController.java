package Deliveries.BusinessLayer;

import Deliveries.BusinessLayer.Enums_and_Interfaces.Availability;
import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryException;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.DataAccessLayer.DriverDAO;
import HR.BusinessLayer.ShiftController;
import HR_Deliveries_Interface.HRIntegrator;

import java.sql.Timestamp;
import java.util.*;

public class DriverController {
    private final HashSet<Driver> drivers;
    private HRIntegrator hrManager; //
    private static DriverController instance = null;
    private final DriverDAO driverDAO;


    // Singleton Constructor
    private DriverController() {
        driverDAO = new DriverDAO();
        drivers = new HashSet<>();
        drivers.addAll(driverDAO.loadData());
        generateFleet(40);
        hrManager = ShiftController.getInstance();
    }

    public void generateFleet(int numberOfDrivers) {
        Set<String> usedIds = new HashSet<>();
        Random random = new Random();

        for (int i = 0; i < numberOfDrivers; i++) {
            // Generate random driver data
            String name = "Driver " + i;
            String phone = "555-555-" + String.format("%04d", i);

            // Generate unique driver ID
            String id;
            do {
                id = String.format("%09d", random.nextInt(1000000000));
            } while (usedIds.contains(id));
            usedIds.add(id);

            // Create a new driver object and set availability
            Driver driver = new Driver(name, id, phone,
                    new License(random.nextInt(30),
                    random.nextInt(1), random.nextInt(1)));
            driver.setAvailability(Availability.Available);

            // Add the driver to the DriverController instance
            addDriver(driver);
        }
    }

    private void addDriver(Driver driver) {
        drivers.add(driver);
    }

    public static DriverController getInstance() {
        if (instance == null) {
            instance = new DriverController();
        }
        return instance;
    }

    public Driver pickDriver(Truck truck, Timestamp startTime, Timestamp finishTime) throws DeliveryException {
        List<String> availableDrivers = hrManager.getAvailableDrivers(startTime, finishTime);
        for (Driver driver : drivers) {
            if (driver.isAvailable() &&
                    availableDrivers.contains(driver.getId())) {
                if (driver.isLicensed(truck)) {
                    driver.setAvailability(Availability.Busy);
                    notifyHR(startTime, finishTime, driver);
                    return driver;
                }
            }
        }
        throw new DeliveryException("No available drivers with license for truck " + truck);
    }

    private void notifyHR(Timestamp startTime, Timestamp finishTime, Driver driver) {
        // TODO: Use boolean which returns to check if the driver was assigned successfully.
        //  If not, throw exception?
        hrManager.assignDrivers(driver.getId(), startTime, finishTime);
    }

}

