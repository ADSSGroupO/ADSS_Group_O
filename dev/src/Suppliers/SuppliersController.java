package Suppliers;

import java.util.*;

public class SuppliersController {
    ArrayList<Supplier> suppliers;
    ArrayList<Product> products;
    HashMap<Integer, ArrayList<Supplier>> suppliersByProduct; // <product_code, list<supplier>>: the index is product codes, and the values are list of suppliers, making
    // this map helpful when wanting to make an entire order from specific supplier and wanting to check if the supplier delivers all requested products
    HashMap<Integer, String> productsAndNames; // <product_code, product_name>: allowing quick access to product name when trying to make new order

    public SuppliersController() {
        suppliers = new ArrayList<>();
        products = new ArrayList<>();
        suppliersByProduct = new HashMap<Integer, ArrayList<Supplier>>();
    }

    // a function that takes input for initializing supplier from user, initializes a new supplier and adds it to list of suppliers
    public void CreateSupplier() {
        // printing menu for user, asking for input
        Scanner supplier_input = new Scanner(System.in);
        System.out.println("Enter private company number: ");
        int supplier_id = supplier_input.nextInt();
        System.out.println("Enter supplier's name: ");
        String supplier_name = supplier_input.nextLine();
        System.out.println("Enter supplier's bank account number: ");
        int bank = supplier_input.nextInt();
        System.out.println("Choose preferred payment method:\n1. Credit\n2. Checks\n3. Cash\n4. Transfer To Account\n");
        int pay = supplier_input.nextInt();
        Payment payment = Payment.values()[pay - 1];
        System.out.println("Choose a preferred way of delivery:\n1. Fixed Days\n2. On Order\n3. Needs Pick Up Service\n");
        int option = supplier_input.nextInt();
        Supplier newSupplier;
        // if supplier delivers on fixed days
        if (option == 1) {
            // create supplier
            newSupplier = new Fixed_Days_Supplier(supplier_name, supplier_id, bank, payment);
            // get all the relevant days
            System.out.println("Enter number of shipping days: ");
            int numberOfDays = supplier_input.nextInt();
            ArrayList<Shipment_Days> shipDays = new ArrayList<>();
            for (int i = 0; i < numberOfDays; i++) {
                System.out.println("Choose day:\n1. Sunday\n2. Monday\n3. Tuesday\n4. Wednesday\n5. Thursday\n6. Friday\n7. Saturday\n");
                int currentDay = supplier_input.nextInt();
                Shipment_Days newDay = Shipment_Days.values()[currentDay];
                ((Fixed_Days_Supplier) newSupplier).addShipDay(newDay);
            }
            suppliers.add(newSupplier);
        }
        // if supplier delivers on order
        else if (option == 2) {
            newSupplier = new On_Order_Supplier(supplier_name, supplier_id, bank, payment);
            suppliers.add(newSupplier);
        }
        // if supplier has no transportation and needs pick up service
        else if (option == 3) {
            System.out.println("Enter supplier's pick up address: ");
            String address = supplier_input.nextLine();
            newSupplier = new No_Transport_Supplier(supplier_name, supplier_id, bank, payment, address);
            suppliers.add(newSupplier);
        } else {
            // if option is invalid, print error message
            System.out.println("Invalid option");
        }
    }

    // a function that takes input for initializing product from user, initializes a new product and adds it to list of product
    public void addProduct() {
        // printing menu for user, asking for input
        Scanner product_input = new Scanner(System.in);
        System.out.println("Enter product name: ");
        String product_name = product_input.nextLine();
        System.out.println("Enter section: ");
        String section = product_input.nextLine();
        System.out.println("Enter manufacturer code: ");
        int manu = product_input.nextInt();
        // initialize new product and add it to list of products
        Product newProduct = new Product(product_name, section, manu);
        products.add(newProduct);
        // add product to map of product code and names
        productsAndNames.put(newProduct.getProductCode(), product_name);
    }

    // function that adds new supply agreement to list of agreements
    public void addSupplyAgreement() {
        // printing menu for user, asking for input
        Scanner agreement_input = new Scanner(System.in);
        System.out.println("Enter private company number: ");
        int supplier_id = agreement_input.nextInt();
        // find supplier in suppliers list
        Supplier current_supplier = null;
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getPrivateCompanyNumber() == supplier_id)
                current_supplier = suppliers.get(i);
        }
        // if supplier doesn't exist, print error message and return
        if (current_supplier == null) {
            System.out.println("Invalid ID");
            return;
        }
        // if supplier exists, continue with input taking
        System.out.println("Enter product code: ");
        int product_code = agreement_input.nextInt();
        System.out.println("Enter list price per unit: ");
        double price = agreement_input.nextDouble();
        System.out.println("Enter serial number of product in supplier's catalog: ");
        int catalog = agreement_input.nextInt();
        System.out.println("Enter max amount of units: ");
        int amount = agreement_input.nextInt();
        // initialize new supply agreement & add agreement to supplier's list of agreement
        Supply_Agreement newSA = current_supplier.addAgreement(product_code, price, catalog, amount);
        // check if the supplier offers discount
        System.out.println("Do the agreement includes discounts?\n1. Yes\n 2. No\n");
        int option = agreement_input.nextInt();
        // if there are discounts, loop and take input for them until user enters to stop
        while (option == 1) {
            System.out.println("Please enter required number of products: ");
            int numOfProducts = agreement_input.nextInt();
            System.out.println("Is the discount by percentage or by price amount?\n1. By Percentage\n 2. By Products Amount\n");
            int discountOption = agreement_input.nextInt();
            DiscountByProduct newDiscount;
            System.out.println("Please enter discount value: ");
            double discountValue = agreement_input.nextInt();
            // if discount by percentage, meaning the percentage of the total price for the units of this product will be reduced
            if (discountOption == 1) {
                newDiscount = new DiscountOfPercentageByProduct(discountValue, numOfProducts);
                newSA.getDiscounts().add(newDiscount);
            }
            // if discount by products amount, meaning that a fixed amount of money will be reduced
            else if (discountOption == 2) {
                newDiscount = new DiscountOfPriceByProduct(discountValue, numOfProducts);
                newSA.getDiscounts().add(newDiscount);
            } else {
                // if invalid option,print error message and break to menu
                System.out.println("Invalid option");
                break;
            }
            // if there are more discount, repeat loop
            System.out.println("Do you have more discounts?\n1. Yes\n 2. No\n");
            option = agreement_input.nextInt();
        }
        // add supplier to map of products and suppliers
        suppliersByProduct.get(product_code).add(current_supplier);
    }

    // function for creating a new order
    public void addOrder() {
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
        // adding suppliers who deliver this product to list of capable suppliers
        relevantSuppliers.addAll(suppliersByProduct.get(product_code));
        System.out.println("Enter number of units: ");
        int numOfUnits = order_input.nextInt();
        // adding key value pair of current product and number of units
        productsAndAmounts.put(product_code, numOfUnits);
        // adding product code to list of products
        productsToOrder.add(product_code);
        // checking if there are more products to order
        System.out.println("Do you have more products?\n1. Yes\n 2. No\n");
        option = order_input.nextInt();
        // while there are more products to add
        while (option == 1) {
            // take input of new product
            System.out.println("Enter product code: ");
            product_code = order_input.nextInt();
            // keeping the intersection of suppliers who ship next product. at the end the result should be only suppliers
            // who ship all products
            relevantSuppliers.retainAll(suppliersByProduct.get(product_code));
            System.out.println("Enter number of units: ");
            numOfUnits = order_input.nextInt();
            // add product to key value pairs map
            productsAndAmounts.put(product_code, numOfUnits);
            // adding product code to list of products
            productsToOrder.add(product_code);
            // checking if there are more products
            System.out.println("Do you have more products?\n1. Yes\n 2. No\n");
            option = order_input.nextInt();
        }
        // if list of suppliers who can deliver all product is not empty, find cheapest supplier among them
        if (!relevantSuppliers.isEmpty()) {
            cheapestSupplier(branch, relevantSuppliers, productsToOrder, productsAndAmounts);
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
                ArrayList<Supplier> supplierForProduct = suppliersByProduct.get(productsToOrder.get(i));
                for (int j = 0; j < supplierForProduct.size(); j++) {
                    // finding best price for product for current supplier
                     double currentPrice = supplierForProduct.get(j).getBestPossiblePrice(productsToOrder.get(i), productsAndAmounts.get(productsToOrder.get(i)));
                     if (currentPrice < minPrice || minSupplier == null) {
                         minSupplier = supplierForProduct.get(j);
                         minPrice = currentPrice;
                     }
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
            // iterate the disctionary and make orders for matching suppliers
            for (Supplier currentSup : suppliersAndProducts.keySet()) {
                makeOrderFromSupplier(branch, currentSup, suppliersAndProducts.get(currentSup), productsAndAmounts);
            }
        }
    }

    // function that takes in supplier, hashmap of product codes and total price for their portion, and hashmap of product codes and amounts. it creates an order and
    // adds it to list of supplier's orders
    public Order makeOrderFromSupplier(int branch, Supplier supplier, ArrayList<Integer> productsToOrder, HashMap<Integer, Integer> productsAndAmounts) {
        // informing of the chosen supplier
        System.out.println("ID of chosen supplier: " + supplier.getPrivateCompanyNumber() + " Name: " + supplier.getName());
        // taking in the name of contact
        System.out.println("Enter name of contact: ");
        Scanner order_input = new Scanner(System.in);
        String contact_name = order_input.nextLine();
        Supplier_Contact contact = supplier.getContact(contact_name);
        // starting the order
        Order newOrder = supplier.addNewOrder(branch, contact);
        System.out.println("---INITIALIZING ORDER---");
        // iterating all products and adding to order
        for (int i = 0; i < productsToOrder.size(); i++) {
            int units = productsAndAmounts.get(productsToOrder.get(i));
            String product_name = productsAndNames.get(productsToOrder.get(i));
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

    // this function takes in a list of optional supppliers who deliver all the products in the productsToOrder list, and a map with product code as key and
    // the amount to order as value. it finds the cheapest supplier, makes an order and returns it
    public Order cheapestSupplier(int branch, ArrayList<Supplier> optionalSuppliers, ArrayList<Integer> productsToOrder, HashMap<Integer, Integer> productsAndAmounts) {
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
}
