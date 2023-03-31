public class No_Transport_Supplier extends Supplier {
    String address;

    public No_Transport_Supplier(String name, int id, int bank, String pay, String shipaddress) {
        super(name, id, bank, pay);
        address = shipaddress;
    }
}
