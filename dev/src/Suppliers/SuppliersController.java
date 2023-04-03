package Suppliers;

import java.util.*;

public class SuppliersController {
    ArrayList<Supplier> suppliers;
    ArrayList<Product> products;
    HashMap<Integer, ArrayList<Supplier>> suppliersByProduct; // <product_code, list<supplier>>: the index is product codes, and the values are list of suppliers, making
    // this map helpful when wanting to make an entire order from specific supplier and wanting to check if the supplier delivers all requested products

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
            // get all the relevant days
            System.out.println("Enter number of shipping days: ");
            int numberOfDays = supplier_input.nextInt();
            ArrayList<Shipment_Days> shipDays = new ArrayList<>();
            for (int i = 0; i < numberOfDays; i++) {
                System.out.println("Choose day:\n1. Sunday\n2. Monday\n3. Tuesday\n4. Wednesday\n5. Thursday\n6. Friday\n7. Saturday\n");
                int currentDay = supplier_input.nextInt();
                Shipment_Days newDay = Shipment_Days.values()[currentDay];
                shipDays.add(newDay);
            }
            // add days to the supplier's list of ship days
            newSupplier = new Fixed_Days_Supplier(supplier_name, supplier_id, bank, payment, shipDays);
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
        // initializing new supply agreement
        Supply_Agreement newSA = new Supply_Agreement(product_code, price, catalog, amount);
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
        // add agreement to supplier's list of agreement
        current_supplier.addAgreement(newSA);
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
        // taking input of first product
        System.out.println("Enter product code: ");
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

    }

    // עוד לא סיימתי את הפונקציה הזאת, צריכה להשלים
    // function that takes in supplier, hashmap of product codes and total price for their portion, and hashmap of product codes and amounts. it creates an order and
    // adds it to list of supplier's orders
    public Order makeOrderFromSupplier(Supplier supplier, HashMap<Integer, Double> productsAndPrices, ArrayList<Integer> productsToOrder, HashMap<Integer, Integer> productsAndAmounts) {
        for (int i = 0; i < productsToOrder.size(); i++) {

        }
    }

    // this function takes in a list of optional supppliers who deliver all the products in the productsToOrder list, and a map with product code as key and
    // the amount to order as value. it finds the cheapest supplier, makes an order and returns it
    public Order cheapestSupplier(ArrayList<Supplier> optionalSuppliers, ArrayList<Integer> productsToOrder, HashMap<Integer, Integer> productsAndAmounts) {
        Supplier minSupplier = null;
        double minTotalPrice = -1;
        // creating a sub map with supplier's id as key, and another map as values with pairs of products and prices
        HashMap<Supplier, HashMap<Integer, Double>> mapOfProductsAndPricesBySuppliers = new HashMap<>(); // <supplier, HashMap(<product_code, price_after_discount>)>
        // iterating all products to order
        for (int i = 0; i < optionalSuppliers.size(); i++) {
            // get all agreements of current supplier
            ArrayList<Supply_Agreement> listOfAgreements = optionalSuppliers.get(i).getAgreements();
            // if there are no agreements for this supplier, can't make order with all products so return null
            if (listOfAgreements == null)
                return null;
            // variable for total price if order from current supplier
            double current_totalprice = 0;
            // iterating all agreements of supplier
            for (int j = 0; j < listOfAgreements.size(); j++) {
                // if agreement includes product that needs to be ordered
                if (productsToOrder.contains(listOfAgreements.get(j).getProductCode())) {
                    // get amount of units to order
                    int amount = productsAndAmounts.get(listOfAgreements.get(j).getProductCode());
                    // get list price of supplier
                    double priceBeforeDiscounts = amount * listOfAgreements.get(j).getListPrice();
                    double minPrice = priceBeforeDiscounts;
                    // iterating all discounts
                    for (int k = 0; k < listOfAgreements.get(j).getDiscounts().size(); k++) {
                        // if amount is enough for discount
                        if (listOfAgreements.get(i).getDiscounts().get(j).isEligibleForDiscount(amount, priceBeforeDiscounts)) {
                            // calculate price after discount
                            double newMinPrice = listOfAgreements.get(j).getDiscounts().get(k).applyDiscount(priceBeforeDiscounts);
                            // if current discount reduced price to be smaller than previous discounts, set it as the best discount
                            if (newMinPrice < minPrice) {
                                minPrice = newMinPrice;
                            }
                        }
                    }
                    // adding final price of product units to map of pairs of products and prices: <supplier's_id, <<product code, best price possible for supplier>>
                    // get map of supplier
                    HashMap current_supplier_map = mapOfProductsAndPricesBySuppliers.get(optionalSuppliers.get(i));
                    // if no list, create it
                    if (current_supplier_map == null) {
                        current_supplier_map = new HashMap<>();
                    }
                    // add map to map of suppliers, and then match supplier id to map of products and prices
                    current_supplier_map.put(listOfAgreements.get(j).getProductCode(), minPrice);
                    mapOfProductsAndPricesBySuppliers.put(optionalSuppliers.get(i), current_supplier_map);
                    // add current price to the total price of order from supplier
                    current_totalprice += minPrice;
                }
            }
            // if not initialized or there is better option, change values
            if (minSupplier == null || current_totalprice < minTotalPrice) {
                minSupplier = optionalSuppliers.get(i);
                minTotalPrice = current_totalprice;
            }
        }
        // get the map of the supplier with the minimal price
        HashMap<Integer, Double> chosenSupplierMap = mapOfProductsAndPricesBySuppliers.get(minSupplier);
        // make order
        return makeOrderFromSupplier(minSupplier, chosenSupplierMap, productsToOrder, productsAndAmounts);
    }
}
