package Suppliers;

import java.util.ArrayList;

public class Fixed_Days_Supplier extends Supplier {

    // description of class: this is a class that represents the suppliers of superli, who delivers orders on fixed days. it inherits the class supplier.
    // in addition to parent class, it has a list of days. these days are the delivery days of the supplier.
    private ArrayList<Shipment_Days> days; // shipping days

    public Fixed_Days_Supplier(String name, int id, int bank, Payment pay) { // constructor
        super(name, id, bank, pay);
        days = new ArrayList<Shipment_Days>();
    }

    // function that takes in the string of the new day, and adds it to the list of days
    public void addShipDay(Shipment_Days day) {
        days.add(day);
    }

    // function that takes in the string of the new day, and removes it from the list of days
    public void removeShipDay(String day) {
        // iterate list of days. when finds, delete
        for (int i = 0; i < days.size(); i++) {
            if (days.get(i).toString().equals(day))
                days.remove(i);
        }
    }

}
