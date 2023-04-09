package Suppliers;

public class Product {
    // static variable that counts number of products and assign serial number
    static int numberOfProducts = 0;

    // description of class: this class represents an item that can be ordered from suppliers. it includes serial number (code),
    // name, and the section it belongs to.
    private int product_code; // serial number of product
    private String product_name; // name of product
    private String section; // section in superli
    private String manu_code; // the code of the manufacturer of the product

    public Product(String name, String manufacturer, String sec) {
        numberOfProducts++;
        product_code = numberOfProducts;
        product_name = name;
        section = sec;
        manu_code = manufacturer;
    }

    // getters for attributes
    public int getProductCode() {return product_code;}
    public String getProductName() {return product_name;}
}
