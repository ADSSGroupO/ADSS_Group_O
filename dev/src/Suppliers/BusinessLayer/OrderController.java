package Suppliers.BusinessLayer;

import Inventory.BusinessLayer.Product;
import Inventory.BusinessLayer.ProductController;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderController { //Controller for order as singleton
    HashMap<Integer, Order> orders; // <id, Order>: map that matches id to order
    private static OrderController instance = null; // the instance of the Order Controller

    // constructor
    private OrderController() {
        orders = new HashMap<Integer, Order>();
    }

    // get instance of order controller. if it doesn't exist, create it
    public static OrderController getInstance() {
        if (instance == null)
            instance = new OrderController();
        return instance;
    }

    // print all orders
    public void printAllOrders() {
        for (Order order : orders.values())
            System.out.println(order);
    }

    // this function takes in a list of optional supppliers who deliver all the products in the productsToOrder list, and a map with product code as key and
    // the amount to order as value. it finds the cheapest supplier, makes an order and returns it
    private Order cheapestSupplier(int branch, ArrayList<Supplier> optionalSuppliers, ArrayList<Integer> productsToOrder, HashMap<Integer, Integer> productsAndAmounts) {
        Supplier minSupplier = null;
        double minTotalPrice = -1;
        // iterating all suppliers and finding cheapest supplier for product
        for (int i = 0; i < optionalSuppliers.size(); i++) {
            // variable for total price if order from current supplier
            double current_total_price = 0;
            // iterating all products to order
            for (int j = 0; j < productsToOrder.size(); j++){
                // get best possible price for supplier
                double current_price = optionalSuppliers.get(i).getBestPossiblePrice(productsToOrder.get(j), productsAndAmounts.get(productsToOrder.get(j)));
                // add price of current product to total price
                current_total_price = current_total_price + current_price;
            }
            // if first supplier to check, or better option, set as minimal supplier
            if (current_total_price < minTotalPrice || minSupplier == null) {
                minTotalPrice = current_total_price;
                minSupplier = optionalSuppliers.get(i);
            }
        }
        // make order from chosen supplier
        return makeOrderFromSupplier(branch, minSupplier, productsToOrder, productsAndAmounts);
    }

    // function that takes in supplier, hashmap of product codes and total price for their portion, and hashmap of product codes and amounts. it creates an order and
    // adds it to list of supplier's orders
    private Order makeOrderFromSupplier(int branch, Supplier supplier, ArrayList<Integer> productsToOrder, HashMap<Integer, Integer> productsAndAmounts) {
        // starting the order
        Order newOrder = supplier.addNewOrder(branch);
        System.out.println("---INITIALIZING ORDER---");
        // iterating all products and adding to order
        for (int i = 0; i < productsToOrder.size(); i++) {
            int units = productsAndAmounts.get(productsToOrder.get(i));
            Product product = ProductController.getInstance().getProductById(productsToOrder.get(i));
            String product_name = product.getName();
            double list_price = supplier.getListPriceOfProducts(productsToOrder.get(i), 1);
            double price_before_discount = supplier.getListPriceOfProducts(productsToOrder.get(i), units);
            double final_price = supplier.getBestPossiblePrice(productsToOrder.get(i), units);
            double discount_given = price_before_discount - final_price;
            newOrder.addProducts(productsToOrder.get(i), product_name, units, list_price, discount_given, final_price);
        }
        // when finished, print and return order
        System.out.println("---ORDER WAS PLACED!---");
        // apply order discounts on order made
        supplier.applyOrderDiscountsOnOrder(newOrder);
        return newOrder;
    }


    // function that makes periodic order from supplier
    public void makePeriodicOrder(int branch, ArrayList<Integer> productsToOrder, HashMap<Integer, Integer> productsAndAmounts) {
        // creating a list of suppliers that are capable of delivering all products
        ArrayList<Supplier> relevantSuppliers = new ArrayList<>();
        // add suppliers who deliver this product to list of capable suppliers
        for (int i = 0; i < productsToOrder.size(); i++) {
            relevantSuppliers.addAll(SupplyAgreementController.getInstance().getSuppliersByProduct(productsToOrder.get(i)));
        }

        // if list of suppliers who can deliver all product is not empty, find cheapest supplier among them
        if (!relevantSuppliers.isEmpty()) {
            Order newOrder = cheapestSupplier(branch, relevantSuppliers, productsToOrder, productsAndAmounts);
            orders.put(newOrder.getOrderNumber(), newOrder);
        }

        // if list is empty, we need to divide the order and therefore to find cheapest supplier per product
        else {
            // map of suppliers and the products they deliver
            HashMap<Supplier, ArrayList<Integer>> suppliersAndProducts = new HashMap<>(); // <supplier, list<product_code>>
            // iterate all products to order
            for (int i = 0; i < productsToOrder.size(); i++) {
                // min price for current product
                double minPrice = -1;
                Supplier minSupplier = null;
                // get list of suppliers who ship products
                ArrayList<Supplier> supplierForProduct = SupplyAgreementController.getInstance().getSuppliersByProduct(productsToOrder.get(i));
                for (int j = 0; j < supplierForProduct.size(); j++) {
                    // finding best price for product for current supplier
                    double currentPrice = supplierForProduct.get(j).getBestPossiblePrice(productsToOrder.get(i), productsAndAmounts.get(productsToOrder.get(i)));
                    // if can make order
                    if (productsAndAmounts.get(productsToOrder.get(i)) <= supplierForProduct.get(j).getMaxAmountOfUnits(productsToOrder.get(i))) {
                        if (currentPrice < minPrice || minSupplier == null) {
                            minSupplier = supplierForProduct.get(j);
                            minPrice = currentPrice;
                        }
                    }
                }
                // if no supplier found, meaning can't make order
                if (minSupplier == null) {
                    throw new IllegalArgumentException("No supplier was found. can't make order!");
                }
                // getting current list of products to order from supplier
                ArrayList<Integer> minSupplierCurrentProducts = suppliersAndProducts.get(minSupplier);
                if (minSupplierCurrentProducts == null) {
                    minSupplierCurrentProducts = new ArrayList<>();
                }
                // add product to list
                minSupplierCurrentProducts.add(productsToOrder.get(i));
                // add to map
                suppliersAndProducts.put(minSupplier, minSupplierCurrentProducts);
            }
            // iterate the dictionary and make orders for matching suppliers
            for (Supplier currentSup : suppliersAndProducts.keySet()) {
                Order newOrder = makeOrderFromSupplier(branch, currentSup, suppliersAndProducts.get(currentSup), productsAndAmounts);
                orders.put(newOrder.getOrderNumber(), newOrder);
            }
        }
    }

    // function that cancels an order
    public void cancelOrder(int numOfOrder) {
        Order order = orders.get(numOfOrder);
        if (order == null)
            throw new IllegalArgumentException("Order number does not exist");
        order.cancelOrder();
    }

    // function that cancels a product of order
    public void cancelProductOfOrder(int numOfOrder, int product_code) {
        Order order = orders.get(numOfOrder);
        if (order == null)
            throw new IllegalArgumentException("Order number does not exist");
        Product product = ProductController.getInstance().getProductById(product_code);
        if (product == null)
            throw new IllegalArgumentException("Product code does not exist");
        order.cancelProduct(product_code);
    }

    // function that confirms an order
    public void confirmOrder(int numOfOrder) {
        Order order = orders.get(numOfOrder);
        if (order == null)
            throw new IllegalArgumentException("Order number does not exist");
        order.confirmDelivery();
    }

}
