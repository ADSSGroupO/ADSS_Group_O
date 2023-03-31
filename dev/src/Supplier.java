public abstract class Supplier {
    protected String supplier_name;
    protected int supplier_id;
    protected int bank_account;
    protected Payment pay_system;

    public Supplier(String name, int id, int bank, String pay) {
        supplier_name = name;
        supplier_id = id;
        bank_account = bank;
        pay_system = Payment.valueOf(pay);
    }
    public int getPrivateCompanyNumber() {return this.supplier_id;}
    public String getName() {return this.supplier_name;}
    public int getBankAccount() {return this.bank_account;}
}
