import java.util.Date;

public class On_Order_Supplier extends Supplier {
    public Date current_order;

    public On_Order_Supplier(String name, int id, int bank, String pay, Date orderdate) {
        super(name, id, bank, pay);
        current_order = orderdate;
    }

}
