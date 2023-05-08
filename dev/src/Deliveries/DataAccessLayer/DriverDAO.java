package Deliveries.DataAccessLayer;

import Deliveries.BusinessLayer.License;
import HR.DataAccessLayer.Connect;
import Deliveries.BusinessLayer.Driver;

import java.sql.SQLException;
import java.util.*;

public class DriverDAO {
    private final Connect conn;
    public DriverDAO() {
        conn = Connect.getInstance();
    }

    //TODO: insert drivers to db
    public Set<Driver> loadData(){
        Set<Driver> drivers = new HashSet<>();
        try {
            List<HashMap<String, Object>> driverDetails = conn.executeQuery("SELECT * FROM Drivers JOIN " +
                    "DriverLicenses ON Drivers.driver_id = DriverLicenses.driver_id");
            for (HashMap<String, Object> driverRecord: driverDetails) {
                Driver driver = getDriver(driverRecord);
                drivers.add(driver);
            }
            return drivers;
        }
        catch (SQLException exception) {
            return null;
        }
    }


    private Driver getDriver(HashMap<String, Object> driverDetails) {
        String id = (String) driverDetails.get("driver_id");
        String name = (String) driverDetails.get("driver_name");
        String phone = (String) driverDetails.get("phone");

        int weightAllowed = Integer.parseInt(driverDetails.get("weight_allowed_tons").toString());
        Integer regularAllowed = (Integer) driverDetails.get("regular_allowed");
        Integer refrigeratedAllowed = (Integer) driverDetails.get("refrigerated_allowed");
        License license = new License(weightAllowed, regularAllowed, refrigeratedAllowed);
        return new Driver(id, name, phone, license);


    }

    public boolean addDriver(Driver driver) {
        String id = driver.getId();
        String name = driver.getName();
        String phone = driver.getPhone();
        License license = driver.getLicense();
        int weightAllowed = license.getWeightAllowedTons();
        int regularAllowed = license.getTruckTypesAllowed().contains("Regular") ? 1 : 0;
        int refrigeratedAllowed = license.getTruckTypesAllowed().contains("Refrigerated") ? 1 : 0;
        String query = "INSERT INTO Drivers (driver_id, driver_name, phone)" +
                " VALUES ('" + id + "', '" + name + "', '" + phone + "');";
        String query2 = "INSERT INTO DriverLicenses " +
                "(driver_id, weight_allowed_tons, regular_allowed, refrigerated_allowed)" +
                " VALUES ('" + id + "', " + weightAllowed + ", " + regularAllowed + ", " + refrigeratedAllowed + ");";
        try {
            conn.executeUpdate(query);
            conn.executeUpdate(query2);
            return true;
        }
        catch (SQLException exception) {
            return false;
        }
    }
}