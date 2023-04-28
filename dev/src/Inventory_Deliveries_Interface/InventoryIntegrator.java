package Inventory_Deliveries_Interface;

import Suppliers.BusinessLayer.Order;

import java.util.ArrayList;
import java.util.HashMap;

public interface InventoryIntegrator {

    // lior and noa: this are arguments we need from you:
    // branch - the branch that ordered the products
    // productsToOrder - arraylist of product codes
    // productsAndAmounts - hashmap of product codes and amounts to order of each product
    public void orderProducts(int branch, ArrayList<Integer> productsToOrder, HashMap<Integer, Integer> productsAndAmounts);
    // send the arguments to function OrderController.getInstance().makeOrder(branch, productsToOrder, productsAndAmounts); and it will make the order
    /*
        // this is the function we made for creating a new order due to shortage
    public void makeShortageOrder() {
        Scanner order_input = new Scanner(System.in);
        // a map with product code as key, and units as values
        HashMap<Integer, Integer> productsAndAmounts = new HashMap<>(); // <product_code, num_of_units>
        // a list of all ordered products
        ArrayList<Integer> productsToOrder = new ArrayList<>();
        int option = 1;
        // creating a list of suppliers that are capable of delivering all products
        ArrayList<Supplier> relevantSuppliers = new ArrayList<>();
        System.out.println("Please enter branch code of order destination: ");
        int branch = order_input.nextInt();
        // taking input of first product
        System.out.println("---CHOOSING PRODUCTS---\n Please enter product code: ");
        int product_code = order_input.nextInt();
        System.out.println("Enter number of units: ");
        int numOfUnits = order_input.nextInt();
        // adding key value pair of current product and number of units
        productsAndAmounts.put(product_code, numOfUnits);
        // adding product code to list of products
        productsToOrder.add(product_code);
        // checking if there are more products to order
        System.out.println("Do you have more products?\n1. Yes\n2. No");
        option = order_input.nextInt();
        // while there are more products to add
        while (option == 1) {
            // take input of new product
            System.out.println("Enter product code: ");
            product_code = order_input.nextInt();
            System.out.println("Enter number of units: ");
            numOfUnits = order_input.nextInt();
            // add product to key value pairs map
            productsAndAmounts.put(product_code, numOfUnits);
            // adding product code to list of products
            productsToOrder.add(product_code);
            // checking if there are more products
            System.out.println("Do you have more products?\n1. Yes\n2. No");
            option = order_input.nextInt();
        }
        OrderController.getInstance().makeOrder(branch, productsToOrder, productsAndAmounts);
    }

     */
}
