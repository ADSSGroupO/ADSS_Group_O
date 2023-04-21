package Suppliers.BusinessLayer;

import Suppliers.Payment;
import Suppliers.ShipmentDays;

import java.util.ArrayList;
import java.util.HashMap;

public class SupplierController { //Controller for supplier as singleton
    HashMap<Integer, Supplier> suppliers = new HashMap<Integer, Supplier>(); // <id, Supplier>: map that matches id to supplier
    private static SupplierController instance = null; // the instance of the supplier controller

    // constructor
    public SupplierController() {
    }

    // get instance of supplier controller. if it doesn't exist, create it
    public static SupplierController getInstance() {
        if (instance == null)
            instance = new SupplierController();
        return instance;
    }

    // add new fixed days supplier
    public void addFixedDaysSupplier(String name, int id, int bank, String pay, ArrayList<String> days) {
        if (suppliers.containsKey(id))
            throw new IllegalArgumentException("Supplier ID already exists");
        Payment payment = Payment.valueOf(pay);
        FixedDaysSupplier supplier = new FixedDaysSupplier(name, id, bank, payment);
        for (String day : days)
            supplier.addShipDay(ShipmentDays.valueOf(day));
        suppliers.put(id, supplier);
    }

    // add new on order supplier
    public void addOnOrderSupplier(String name, int id, int bank, String pay) {
        if (suppliers.containsKey(id))
            throw new IllegalArgumentException("Supplier ID already exists");
        Payment payment = Payment.valueOf(pay);
        Supplier supplier = new OnOrderSupplier(name, id, bank, payment);
        suppliers.put(id, supplier);
    }

    // add new no transport supplier
    public void addNoTransportSupplier(String name, int id, int bank, String pay, String address) {
        if (suppliers.containsKey(id))
            throw new IllegalArgumentException("Supplier ID already exists");
        Payment payment = Payment.valueOf(pay);
        Supplier supplier = new NoTransportSupplier(name, id, bank, payment, address);
        suppliers.put(id, supplier);
    }

    // function that takes in the ID of a supplier, and returns the instance of it
    public Supplier getSupplierByID(int id) {
        if (!suppliers.containsKey(id))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        return suppliers.get(id);
    }


}
