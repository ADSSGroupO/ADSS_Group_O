package Suppliers;

import java.time.LocalDate;

public class On_Order_Supplier extends Supplier {
    // description of class: this is a class that represents the suppliers of superli, who delivers whenever an order is made.
    // it inherits the class supplier. in addition to parent class attributes, it has a date, which is the date in which the most recent order is supposed to arrive.
    private LocalDate nextDeliveryDate; // date of arrival of next order

    public On_Order_Supplier(String name, int id, int bank, Payment pay) {
        super(name, id, bank, pay);
        nextDeliveryDate = null;
    }

    // update date of new order - receives number of days until arrival, and updates date
    public void updateNextOrderDate(int numberOfDaysUntilDelivery) {
        nextDeliveryDate = LocalDate.now().plusDays(numberOfDaysUntilDelivery);
    }


    // function that returns the next shipping date saved in supplier's card
    @Override
    public LocalDate getNextShippingDate() {
        return nextDeliveryDate;
    }
}
