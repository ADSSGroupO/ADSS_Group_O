package Suppliers;

public class Supplier_Contact {
    // description of class: this class represents a contact of a supplier. it has the id of employer (supplier), name of contact, and its phone number.
    private int supplier_id; // supplier's id
    private String contact_name; // contact name
    private int cellphone; // phone number

    public Supplier_Contact(int id, String name, int phone) { // constructor
        supplier_id = id;
        contact_name = name;
        cellphone = phone;
    }

    // getters of attributes
    public int getCompanyNumber() {return this.supplier_id;}
    public String getContactName() {return this.contact_name;}
    public int getPhone() {return this.cellphone;}
}
