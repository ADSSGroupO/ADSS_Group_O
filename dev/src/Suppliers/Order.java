package Suppliers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    // description of class: this class represents an order made. it includes the id of the supplier, the branch the order needs to be sent to, the date in which the order
    // was made, the supplier contact and the status of the order.
    private List<Order_Details_By_Product> ordered_products; // list of all the information of ordered products
    private int supplier_id; // the supplier's id
    private String branch; // the destination branch of the order
    private Date date; // the estimated date of delivery
    private Supplier_Contact contact; // contact of the supplier
    private Status order_status; // the status of the order

    public Order(int id, String destination, Supplier_Contact supcontact) { // constructor
        supplier_id = id;
        branch = destination;
        date = new java.util.Date(); // current date
        contact = supcontact;
        order_status = Status.InProcess;
        ordered_products = new ArrayList<>(); // setting empty list
    }

    // function that takes in the details of new ordered product, creates listing and adds it to list of ordered products.
    public void addProduct(int code, String name, int amount, double price, double discount, double finalprice) {
        Order_Details_By_Product new_product = new Order_Details_By_Product(code, name, amount, price, discount, finalprice);
        ordered_products.add(new_product);
    }

    // a function that takes in the code of product and the amount to cancel, and cancels it. if can cancel returns true. else,
    // returns false
    public boolean cancelProduct(int code) {
        for (int i = 0; i < ordered_products.size(); i++) {
            // if finds product, delete it
            if (ordered_products.get(i).getProductCode() == code) {
                    ordered_products.remove(i);
                    return true;
            }
        }
        return false;
    }

    // a function that cancels order
    public void cancelOrder() {
        order_status = Status.Canceled;
    }

    // a function that marks the ordered as completed
    public void confirmDelivery() {
        order_status = Status.Completed;
    }

    // getters for attributes
    public int getSupplierID() {return supplier_id;}
    public String getBranch() {return branch;}
    public Date getDateOfOrder() {return date;}
    public Supplier_Contact getContact() {return contact;}
    public Status getOrderStatus() {return order_status;}
}
