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
    protected ArrayList<DiscountByOrder> discountsByOrder; // list of discounts relevant to entire order from supplier
    protected ArrayList<Order> ordersHistory; // list of orders made from supplier
    protected ArrayList<Supplier_Contact> contacts; // list of supplier's contacts


    public Supplier(String name, int id, int bank, Payment pay) { // constructor
        supplier_name = name;
        supplier_id = id;
        bank_account = bank;
        pay_system = pay;
        agreements = new ArrayList<Supply_Agreement>();
        discountsByOrder = new ArrayList<DiscountByOrder>();
        ordersHistory = new ArrayList<Order>();
        contacts = new ArrayList<Supplier_Contact>();
    }

    // getters for the attributes of the class
    public int getPrivateCompanyNumber() {return this.supplier_id;}
    public String getName() {return this.supplier_name;}
    public int getBankAccount() {return this.bank_account;}

    public ArrayList<Supply_Agreement> getAgreements() {return this.agreements;}

    public ArrayList<DiscountByOrder> getOrderDiscounts() {return this.discountsByOrder;}

    // add new agreement to list of agreements
    public void addAgreement (Supply_Agreement agreement) {
        agreements.add(agreement);
    }

    // remove agreement from list of agreements. if finds and deletes, return true. else, return false
    public boolean removeAgreement (int product_code) {
        for (int i = 0; i < agreements.size(); i++) {
            if (agreements.get(i).getProductCode() == product_code) {
                agreements.remove(i);
                return true;
            }
        }
        return false;
    }

    // add new order discount to list of discounts
    public void addOrderDiscount(DiscountByOrder discount) {
        discountsByOrder.add(discount);
    }

    // add new order to history of orders

    public void addNewOrder(Order order) {
        ordersHistory.add(order);
    }

    public void addContact(Supplier_Contact contact) {
        contacts.add(contact);
    }
}
