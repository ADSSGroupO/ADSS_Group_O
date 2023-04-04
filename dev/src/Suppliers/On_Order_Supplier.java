package Suppliers;

import java.util.Date;

public class On_Order_Supplier extends Supplier {
    // description of class: this is a class that represents the suppliers of superli, who delivers whenever an order is made.
    // it inherits the class supplier. in addition to parent class attributes, it has a date, which is the date in which the most recent order is supposed to arrive.
    public Date current_order; // date of arrival

    public On_Order_Supplier(String name, int id, int bank, Payment pay) {
        super(name, id, bank, pay);
        current_order = null;
    }

    // update date of new order
    public void updateNewOrder(Date newOrder) {
        current_order = newOrder;
    }


    // function that returns the next shipping date saved in supplier's card
    @Override
    public Date getNextShippingDate() {
        return current_order;
    }
}
