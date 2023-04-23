package Suppliers.BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class SupplierController { //Controller for supplier as singleton
    HashMap<Integer, Supplier> suppliers; // <id, Supplier>: map that matches id to supplier
    private static SupplierController instance = null; // the instance of the supplier controller

    // constructor
    private SupplierController() {
        suppliers = new HashMap<Integer, Supplier>();
    }

    // get instance of supplier controller. if it doesn't exist, create it
    public static SupplierController getInstance() {
        if (instance == null)
            instance = new SupplierController();
        return instance;
    }

    // add new fixed days supplier
    public void addFixedDaysSupplier(String name, int id, int bank, Payment pay, ArrayList<Integer> days) {
        if (suppliers.containsKey(id))
            throw new IllegalArgumentException("Supplier ID already exists");
        FixedDaysSupplier supplier = new FixedDaysSupplier(name, id, bank, pay);
        for (Integer day : days)
            supplier.addShipDay(day);
        suppliers.put(id, supplier);
    }

    // function that adds ship day to fixed days supplier
    public void addShipDayToFixedSupplier(int id, int dayNumber) {
        if (!suppliers.containsKey(id))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        if (!(suppliers.get(id) instanceof FixedDaysSupplier))
            throw new IllegalArgumentException("Supplier is not a fixed days supplier");
        FixedDaysSupplier supplier = (FixedDaysSupplier) suppliers.get(id);
        supplier.addShipDay(dayNumber);
    }

    // function that removes ship day from fixed days supplier
    public void removeShipDayFromFixedSupplier(int id, int dayNumber) {
        if (!suppliers.containsKey(id))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        if (!(suppliers.get(id) instanceof FixedDaysSupplier))
            throw new IllegalArgumentException("Supplier is not a fixed days supplier");
        FixedDaysSupplier supplier = (FixedDaysSupplier) suppliers.get(id);
        supplier.removeShipDay(dayNumber);
    }

    // add new on order supplier
    public void addOnOrderSupplier(String name, int id, int bank, Payment pay) {
        if (suppliers.containsKey(id))
            throw new IllegalArgumentException("Supplier ID already exists");
        Supplier supplier = new OnOrderSupplier(name, id, bank, pay);
        suppliers.put(id, supplier);
    }

    // add new no transport supplier
    public void addNoTransportSupplier(String name, int id, int bank, Payment pay, String address) {
        if (suppliers.containsKey(id))
            throw new IllegalArgumentException("Supplier ID already exists");
        Supplier supplier = new NoTransportSupplier(name, id, bank, pay, address);
        suppliers.put(id, supplier);
    }

    // function that takes in the ID of a supplier, and returns the instance of it
    public Supplier getSupplierByID(int id) {
        if (!suppliers.containsKey(id))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        return suppliers.get(id);
    }

    // function that takes in the ID of a supplier, and removes it from map of suppliers
    public void removeSupplierByID(int id) {
        if (!suppliers.containsKey(id))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        // remove supplier from all product lists
        ArrayList<SupplyAgreement> agreements = suppliers.get(id).getAllAgreements();
        for (int i = 0; i < agreements.size(); i++) {
            int product = agreements.get(i).getProductCode();
            SupplyAgreementController.getInstance().removeSupplyAgreement(id, product);
        }
        // remove supplier from suppliers list
        suppliers.remove(id);
    }

    // function that takes in the ID of a supplier, and details of new contact, and adds to it the supplier's contact list
    public void addContactToSupplier(int supplier_id, String contact_name, String phone) {
        if (!suppliers.containsKey(supplier_id))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        suppliers.get(supplier_id).addContact(contact_name, phone);
    }

    // function that takes in the ID of a supplier, and details of an existing contact, and removes it from supplier's contact list
    public void removeContactOfSupplier(int supplier_id, String contact_name, String phone) {
        if (!suppliers.containsKey(supplier_id))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        suppliers.get(supplier_id).removeContact(contact_name, phone);
    }

    // function that sets the next delivery date of on order supplier
    public void setNextDeliveryDateOfOnOrderSupplier(int supplierID, int days) {
        // if supplier doesn't exist
        if (!suppliers.containsKey(supplierID))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        // get supplier and update next delivery date
        Supplier supplier = suppliers.get(supplierID);
        ((OnOrderSupplier) supplier).setNumberOfDaysToNextOrder(days);

    }

    // function that sets the next delivery date of no transport supplier
    public void setNextDeliveryDateOfNoTransportSupplier(int supplierID, int day, int month, int year) {
        // if supplier doesn't exist
        if (!suppliers.containsKey(supplierID))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        // get supplier and update next delivery date
        Supplier supplier = suppliers.get(supplierID);
        ((NoTransportSupplier) supplier).setNextDeliveryDate(day, month, year);
    }

    // function that changes a supplier's name
    public void changeSupplierName(int supplierID, String newName) {
        // if supplier doesn't exist
        if (!suppliers.containsKey(supplierID))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        // get supplier and update name
        Supplier supplier = suppliers.get(supplierID);
        supplier.setName(newName);
    }

    // function that changes a supplier's payment method
    public void changePayment(int supplierID, Payment newPayment) {
        // if supplier doesn't exist
        if (!suppliers.containsKey(supplierID))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        // get supplier and update payment method
        Supplier supplier = suppliers.get(supplierID);
        supplier.setPaymentMethod(newPayment);
    }

    // function that changes a supplier's bank account
    public void changeBankAccount(int supplierID, int bankAccount) {
        // if supplier doesn't exist
        if (!suppliers.containsKey(supplierID))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        // get supplier and update bank account
        Supplier supplier = suppliers.get(supplierID);
        supplier.setBankAccount(bankAccount);
    }

    // function that takes in supplier's id and prints his orders history
    public void printOrdersOfSupplier(int supplierID) {
        // if supplier doesn't exist
        if (!suppliers.containsKey(supplierID))
            throw new IllegalArgumentException("Supplier ID doesn't exist");
        // get supplier and print orders
        Supplier supplier = suppliers.get(supplierID);
        supplier.printOrderHistory();
    }

}
