public class Supplier_Contact {
    private int supplier_id;
    private String contact_name;
    private int cellphone;

    public Supplier_Contact(int id, String name, int phone) {
        supplier_id = id;
        contact_name = name;
        cellphone = phone;
    }

    public int getCompanyNumber() {return this.supplier_id;}
    public String getContactName() {return this.contact_name;}
    public int getPhone() {return this.cellphone;}
}
