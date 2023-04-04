package Suppliers;

public class Supplier_Contact {
    // description of class: this class represents a contact of a supplier. it has name of contact, and its phone number.
    private String contact_name; // contact name
    private int cellphone; // phone number

    public Supplier_Contact(String name, int phone) { // constructor
        contact_name = name;
        cellphone = phone;
    }

    // getters of attributes
    public String getContactName() {return this.contact_name;}
    public int getPhone() {return this.cellphone;}
}
