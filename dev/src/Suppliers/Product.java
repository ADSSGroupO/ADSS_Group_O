package Suppliers;

public class Product {

    // description of class: this class represents an item that can be ordered from suppliers. it includes serial number (code),
    // name, and the section it belongs to.
    private int product_code; // serial number of product
    private String product_name; // name of product
    private String section; // section in superli

    public Product(int code, String name, String sec) {
        product_code = code;
        product_name = name;
        section = sec;
    }

    // getters for attributes
    public int getProductCode() {return product_code;}
    public String getProductName() {return product_name;}
    public String getSection() {return section;}
}
