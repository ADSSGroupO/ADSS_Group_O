package Suppliers;

import java.util.ArrayList;

public abstract class Supplier {

    // description of class: this is an abstract class that represents the suppliers of superli. each supplier has name, id (private company number),
    // number of bank account, preferred method of payment, and a list of supply agreements made with the supplier.
    protected String supplier_name; // supplier's name
    protected int supplier_id; // supplier's id
    protected int bank_account; // number of bank accounts
    protected Payment pay_system; // preferred method of payment
    protected ArrayList<Supply_Agreement> agreements; // list of supply agreements made with the supplier

    public Supplier(String name, int id, int bank, String pay) { // constructor
        supplier_name = name;
        supplier_id = id;
        bank_account = bank;
        pay_system = Payment.valueOf(pay);
    }

    // getters for the attributes of the class
    public int getPrivateCompanyNumber() {return this.supplier_id;}
    public String getName() {return this.supplier_name;}
    public int getBankAccount() {return this.bank_account;}
}
