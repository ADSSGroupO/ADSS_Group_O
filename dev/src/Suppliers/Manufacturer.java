package Suppliers;

public class Manufacturer {

    private int manu_code;
    private String manu_name;

    public Manufacturer(int code, String name) {
        manu_code = code;
        manu_name = name;
    }

    public String getName(){return manu_name;}
    public int getID(){return manu_code;}
}
