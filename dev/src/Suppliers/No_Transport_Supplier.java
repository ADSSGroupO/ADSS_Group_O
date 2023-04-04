package Suppliers;

import java.util.Date;

public class No_Transport_Supplier extends Supplier {
    // description of class: this is a class that represents the suppliers of superli, who can't deliver their orders, and needs delivery assistance.
    // it inherits the class supplier. in addition to parent class attributes, it has an address, which is the address that the orders will be picked up from.
    String address; // pick up address

    public No_Transport_Supplier(String name, int id, int bank, Payment pay, String shipaddress) { // constructor
        super(name, id, bank, pay);
        address = shipaddress;
    }

    // getter and setter for address
    public String getAddress(){return address;}
    public void setAddress(String newAddress) {address=newAddress;}


    // function that returns the next shipping date - because we arrange delivery, the date is today by default (needs to be further integrated with other modules)
    @Override
    public Date getNextShippingDate() {
        Date date = new java.util.Date();
        return date;
    }
}
