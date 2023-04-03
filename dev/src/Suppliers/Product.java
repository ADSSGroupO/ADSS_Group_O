package Suppliers;

public class Product {
    static int numberOfProducts = 0;

    // description of class: this class represents an item that can be ordered from suppliers. it includes serial number (code),
    // name, and the section it belongs to.
    private int product_code; // serial number of product
    private String product_name; // name of product
    private String section; // section in superli
    private int manu_code; // the code of the manufacturer of the product

    public Product(String name, String sec, int manufacturer) {
        numberOfProducts++;
        product_code = numberOfProducts;
        product_name = name;
        manu_code = manufacturer;
        section = sec;
    }

    // getters for attributes
    public int getProductCode() {return product_code;}
    public String getProductName() {return product_name;}
    public String getSection() {return section;}
    public int getNumberOfProducts() {return numberOfProducts;}
    public int getManufacturer() {return manu_code;}
}
