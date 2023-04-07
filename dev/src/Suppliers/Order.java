package Suppliers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    // description of class: this class represents an order made. it includes the id of the supplier, the branch the order needs to be sent to, the date in which the order
    // was made, the supplier contact and the status of the order.
    private List<Order_Details_By_Product> ordered_products; // list of all the information of ordered products
    private int branch_code; // the destination branch of the order
    private LocalDate date; // the estimated date of delivery
    private Supplier_Contact contact; // contact of the supplier
    private Status order_status; // the status of the order
    private double total_price; // the total cost of the order
    private double orderDiscount; // discount given on total order

    public Order(Supplier supplier, int destination, Supplier_Contact supcontact) { // constructor
        branch_code = destination;
        date = supplier.getNextShippingDate(); // activating supplier's next shipping date method
        contact = supcontact;
        order_status = Status.InProcess;
        ordered_products = new ArrayList<Order_Details_By_Product>(); // setting empty list
        orderDiscount = 0; // discount 0 until finishing order and calculating final price
    }

    // function that takes in the details of new ordered product, creates listing and adds it to list of ordered products.
    public void addProducts(int code, String name, int amount, double price, double discount, double finalprice) {
        Order_Details_By_Product new_product = new Order_Details_By_Product(code, name, amount, price, discount, finalprice);
        ordered_products.add(new_product);
        total_price = total_price + finalprice;
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
    public int getBranch() {return branch_code;}
    public LocalDate getDateOfOrder() {return date;}
    public Supplier_Contact getContact() {return contact;}
    public Status getOrderStatus() {return order_status;}

    public double getTotalPrice() {return total_price;}

    // function that takes in the order discounts of supplier's and applies best discount to order
    public void applyOrderDiscount(ArrayList<DiscountByOrder> discountsByOrder) {
        double price_before_discount = total_price;
        for (int i = 0; i < discountsByOrder.size(); i++) {
            double newPrice = discountsByOrder.get(i).applyDiscount(price_before_discount);
            if (total_price > newPrice) {
                total_price = newPrice;
                orderDiscount = price_before_discount - newPrice;
            }
        }
    }
}
