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

    // function takes in information of new agreement, initializes it and adds the new agreement to list of agreements, and returns the agreement created
    public Supply_Agreement addAgreement (int code, double price, int catalog, int amount) {
        Supply_Agreement newAgreement = new Supply_Agreement(code, price, catalog, amount);
        agreements.add(newAgreement);
        return newAgreement;
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

    // function takes in information of new order, initializes it, adds the new order to history of orders, and returns the order created
    public Order addNewOrder(int destination, Supplier_Contact supcontact) {
        Order order = new Order(destination, supcontact);
        ordersHistory.add(order);
        return order;
    }

    // function takes in information of new contact, initializes it, adds the new contact to list of contacts, and returns the contact created
    public Supplier_Contact addContact(String name, int phone) {
        Supplier_Contact contact = new Supplier_Contact(name, phone);
        contacts.add(contact);
        return contact;
    }

    // function that takes in the product code and number of units, and returns the list price the supplier offers. if supplier doesn't
    // sell product, return -1
    public double getListPriceOfProducts(int product_code, int units) {
        for (int i = 0; i < agreements.size(); i++) {
            if (product_code == agreements.get(i).getProductCode())
                return agreements.get(i).getListPriceBeforeDiscounts(units);
        }
        return -1;
    }

    // function that takes in the product code, and returns the catalog number of the product in the supplier's systems. if supplier doesn't
    // sell product, return -1
    public int getCatalogNumber(int product_code) {
        for (int i = 0; i < agreements.size(); i++) {
            if (product_code == agreements.get(i).getProductCode())
                return agreements.get(i).getCatalogCode();
        }
        return -1;
    }

    // function that takes in the product code, and returns the max amount of units the supplier can deliver. if supplier doesn't
    // sell product, return -1
    public int getMaxAmountOfUnits(int product_code) {
        for (int i = 0; i < agreements.size(); i++) {
            if (product_code == agreements.get(i).getProductCode())
                return agreements.get(i).getMaxAmount();
        }
        return -1;
    }

    // function that takes in the product code and number of units, and returns the minimal price the supplier offers after discount. if supplier doesn't
    // sell product, return -1
    public double getBestPossiblePrice(int product_code, int units) {
        for (int i = 0; i < agreements.size(); i++) {
            if (product_code == agreements.get(i).getProductCode()) {
               double minimal_price = agreements.get(i).getMinimalPrice(units);
               return minimal_price;
            }
        }
        return -1;
    }

    // function takes in name of contact of supplier, and returns it. if can't find, returns null
    public Supplier_Contact getContact(String name) {
        for (int i = 0; i < contacts.size(); i++) {
            if (name == contacts.get(i).getContactName()) {
                return contacts.get(i);
            }
        }
        return null;
    }
}
