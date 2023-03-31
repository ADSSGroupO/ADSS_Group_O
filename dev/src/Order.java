import java.util.Date;
import java.util.List;

public class Order {
    private List<Order_Details_By_Product> Ordered_Product;
    private int supplier_id;
    private String branch; //branch we sent the product to
    private Date date_Of_Order;
    private int cellphone;
    private Status order_Status;
}
