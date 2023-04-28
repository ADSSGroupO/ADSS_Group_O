package Suppliers.BusinessLayer;

public class FixedPeriodOrder { // a class that represents a fixed period order, executed every fixed period of time of store's choice

    private int branch;
    private int supplier_id;
    private int product_code;
    private int amount;

    public FixedPeriodOrder(int id, int branch, int product_code, int amount){
        this.branch = branch;
        this.supplier_id = id;
        this.product_code = product_code;
        this.amount = amount;
    }

    // getter of branch
    public int getBranch() {
        return branch;
    }

    // getter of id
    public int getSupplierID() {
        return supplier_id;
    }

    // getter of amount
    public int getAmount() {
        return amount;
    }

    // getter of product code
    public int getProductCode() {
        return product_code;
    }

    // setter of amount
    public void setAmount(int amount) {
        this.amount = amount;
    }

    // setter of product code
    public void setProductCode(int product_code) {
        this.product_code = product_code;
    }

    // setter of supplier id
    public void setSupplierId(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    // setter of branch
    public void setBranch(int branch) {
        this.branch = branch;
    }
}
