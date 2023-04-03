package Suppliers;

public abstract class DiscountByOrder extends Discount {

    // description of class: this discount represents discount that needs to be applied to total order, and not just by one type
    // of product
    public DiscountByOrder(double val) { // constructor
        super(val);
    }
}
