import java.util.List;

public class Fixed_Days_Supplier extends Supplier {
    private List<Shipment_Days> days;
    private int numOfDays;

    public Fixed_Days_Supplier(String name, int id, int bank, String pay, int daysnum, List<Shipment_Days> ship_days) {
        super(name, id, bank, pay);
        numOfDays = daysnum;
    }

}
