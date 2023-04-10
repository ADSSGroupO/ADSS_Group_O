package Suppliers;

import org.junit.platform.engine.support.descriptor.FileSystemSource;

import java.lang.reflect.Array;
import java.util.*;

public class SupplierController {
    HashMap<Integer, Supplier> suppliers; // <id, Supplier>: map that matches id to supplier
    HashMap<Integer, Product> products; // <product_code, Product>: map that matches product code to product
    HashMap<Integer, ArrayList<Supplier>> suppliersByProduct; // <product_code, list<supplier>>: the index is product codes, and the values are list of suppliers, making
    // this map helpful when wanting to make an entire order from specific supplier and wanting to check if the supplier delivers all requested products

    // initialize controller
    public SupplierController() {
        // initialize data structures
        suppliers =  new HashMap<>();
        products = new HashMap<>();
        suppliersByProduct = new HashMap<>();
    }

    /*** main menu: this is the first menu that appears on screen ***/
    // the main menu that should be printed on screen, each option activates menu related to relevant objects
    public void printMenu() {
        Scanner supplier_input = new Scanner(System.in);
        int option;
        do {
            System.out.println("1. Manage suppliers\n2. Manage supply agreements\n3. Manage orders\n4. Exit");
            option = supplier_input.nextInt();
            switch (option) {
                case 1: {
                    suppliersMenu();
                    break;
                }
                case 2: {
                    supplyAgreementsMenu();
                    break;
                }
                case 3: {
                    ordersMenu();
                    break;
                }
                case 4: {
                    break;
                }
                default: {
                    System.out.println("Invalid option!");
                }
            }
        } while (option != 4);
    }

    /*** suppliers menu: when choosing option 1 (manage suppliers), this menu appears ***/
    // function that prints and manages menu related to suppliers
    public void suppliersMenu() {
        // print menu
        System.out.println("1. Add new supplier");
        System.out.println("2. Remove supplier");
        System.out.println("3. Edit supplier");
        // get input of option
        Scanner supplier_input = new Scanner(System.in);
        int option = supplier_input.nextInt();
        // adding supplier
        if (option == 1) {
            addSupplier();
        }
        else if (option == 2) {
            removeSupplier();
        }
        else if (option == 3) {
            editSupplierInfo();
        }
        else {
            System.out.println("Invalid option!");
        }
    }

    /*** edit supplier: when choosing option 3 in manage suppliers (which is edit supplier), this menu appears ***/
    // function that prints and manages menu that edit suppliers' information
    public void editSupplierInfo() {
        // print menu for editing information of supplier
        System.out.println("What would you want to edit?\n1. Name\n2. Bank account\n3. Payment method\n4. Contacts");
        // get input for choice
        Scanner info_input = new Scanner(System.in);
        int option = info_input.nextInt();
        // if option invalid, print error message and return to main menu
        if (option != 1 && option != 2 && option != 3 && option != 4) {
            System.out.println("Invalid option!");
            return;
        }
        // find the supplier that needs to be edited
        System.out.println("Enter supplier's id: ");
        int id = info_input.nextInt();
        // get supplier
        Supplier supplier = suppliers.get(id);
        // if supplier doesn't exist, print error message and return
        if (supplier == null) {
            System.out.println("Invalid ID");
            return;
        }
        // change name
        if (option == 1) {
            info_input.nextLine();
            System.out.println("Enter new name: ");
            String name = info_input.nextLine();
            supplier.setName(name);
            System.out.println("Name was changed successfully");
        }
        // change bank account number
        else if (option == 2) {
            System.out.println("Enter new bank account: ");
            int bank = info_input.nextInt();
            supplier.setBankAccount(bank);
            System.out.println("Bank account was changed successfully");
        }
        // change preferred payment method
        else if (option == 3) {
            System.out.println("Choose new preferred payment method:\n1. Credit\n2. Checks\n3. Cash\n4. Transfer To Account");
            int pay = info_input.nextInt();
            Payment payment = Payment.values()[pay - 1];
            supplier.setPaymentMethod(payment);
            System.out.println("Payment method was changed successfully");
        }
        // change contacts
        else {
            contactsMenu(id);
        }
    }

    /*** contacts menu: when choosing option 4 (which is edit contacts) in edit supplier, this menu appears ***/
    // function that manages menu for contacts
    public void contactsMenu(int id) {
        System.out.println("What would you want to edit?\n1. Add contact\n2. Remove contact");
        Scanner contact_input = new Scanner(System.in);
        int option = contact_input.nextInt();
        if (option == 1)
            addContact(id);
        else if (option == 2)
            removeContact(id);
        else {
            System.out.println("Invalid option!");
        }
    }

    /*** supply agreements menu: when choosing option 2 (which is manage supply agreements) in the main menu, this menu appears ***/
    public void supplyAgreementsMenu() {
        // print menu of supply agreements methods
        System.out.println("1. Add new supply agreement");
        System.out.println("2. Remove supply agreement");
        System.out.println("3. Add discount by total price of order");
        System.out.println("4. Print supply agreements of supplier");
        // ask for input
        Scanner input = new Scanner(System.in);
        int option = input.nextInt();
        // add supply agreement
        if (option == 1) {
            addSupplyAgreement();
        }
        else if (option == 2) {
            removeSupplyAgreement();
        }
        else if (option == 3) {
            addOrderDiscountForSupplier();
        }
        else if (option == 4) {
            printSupplyAgreements();
        }
        else {
            System.out.println("Invalid option");
        }
    }


    /*** functions of controller ***/

    /*** suppliers ***/
    // a function that takes input for initializing supplier from user, initializes a new supplier and adds it to list of suppliers
    public void addSupplier() {
        // printing menu for user, asking for input
        Scanner supplier_input = new Scanner(System.in);
        System.out.println("Enter private company number: ");
        int supplier_id = supplier_input.nextInt();
        supplier_input.nextLine();
        if (suppliers.containsKey(supplier_id)) {
            System.out.println("Supplier already exists!");
            return;
        }
        System.out.println("Enter supplier's name: ");
        String supplier_name = supplier_input.nextLine();
        System.out.println("Enter supplier's bank account number: ");
        int bank = supplier_input.nextInt();
        System.out.println("Choose preferred payment method:\n1. Credit\n2. Checks\n3. Cash\n4. Transfer To Account");
        int pay = supplier_input.nextInt();
        Payment payment = Payment.values()[pay - 1];
        System.out.println("Choose a preferred way of delivery:\n1. Fixed Days\n2. On Order\n3. Needs Pick Up Service");
        int option = supplier_input.nextInt();
        Supplier newSupplier;
        // if supplier delivers on fixed days
        if (option == 1) {
            // create supplier
            newSupplier = new FixedDaysSupplier(supplier_name, supplier_id, bank, payment);
            // get all the relevant days
            System.out.println("Enter number of shipping days: ");
            int numberOfDays = supplier_input.nextInt();
            for (int i = 0; i < numberOfDays; i++) {
                System.out.println("Choose day:\n1. Sunday\n2. Monday\n3. Tuesday\n4. Wednesday\n5. Thursday\n6. Friday\n7. Saturday");
                int currentDay = supplier_input.nextInt();
                while (currentDay < 1 || currentDay > 7) {
                    System.out.println("Invalid option. enter new option");
                    currentDay = supplier_input.nextInt();
                }
                ShipmentDays newDay = ShipmentDays.values()[currentDay];
                ((FixedDaysSupplier) newSupplier).addShipDay(newDay);
            }
            suppliers.put(supplier_id, newSupplier);
        }
        // if supplier delivers on order
        else if (option == 2) {
            newSupplier = new OnOrderSupplier(supplier_name, supplier_id, bank, payment);
            suppliers.put(supplier_id, newSupplier);
        }
        // if supplier has no transportation and needs pick up service
        else if (option == 3) {
            supplier_input.nextLine();
            System.out.println("Enter supplier's pick up address: ");
            String address = supplier_input.nextLine();
            newSupplier = new NoTransportSupplier(supplier_name, supplier_id, bank, payment, address);
            suppliers.put(supplier_id, newSupplier);
        } else {
            // if option is invalid, print error message
            System.out.println("Invalid option");
        }
    }

    // function that takes in supplier's id and removes it from system
    public void removeSupplier() {
        Scanner input = new Scanner(System.in);
        // get input of supplier to remove
        System.out.println("Enter supplier's id: ");
        int id = input.nextInt();
        // get supplier
        Supplier supplier = suppliers.get(id);
        // if supplier doesn't exist, print error message and return
        if (supplier == null) {
            System.out.println("Invalid ID");
            return;
        }
        // remove supplier from all product lists
        ArrayList<SupplyAgreement> agreements = supplier.getAllAgreements();
        for (int i = 0; i < agreements.size(); i++) {
            int product = agreements.get(i).getProductCode();
            suppliersByProduct.get(product).remove(supplier);
        }
        // remove supplier from list of suppliers
        suppliers.remove(supplier.supplier_id);
        // inform supplier was deleted
        System.out.println("Supplier deleted successfully");
    }

    // function for adding contact of supplier
    public void addContact(int supplier_id) {
        // get supplier
        Supplier supplier = suppliers.get(supplier_id);
        // if supplier doesn't exist, print error message and return
        if (supplier == null) {
            System.out.println("Invalid ID");
            return;
        }
        // taking input of contact info
        Scanner contact_input = new Scanner(System.in);
        System.out.println("Enter contact's name: ");
        String name = contact_input.nextLine();
        System.out.println("Enter phone number: ");
        String phone = contact_input.nextLine();
        supplier.addContact(name, phone);
        System.out.println("Contact added successfully");
    }

    // function for removing contact of supplier
    public void removeContact(int supplier_id) {
        // get supplier
        Supplier supplier = suppliers.get(supplier_id);
        // if supplier doesn't exist, print error message and return
        if (supplier == null) {
            System.out.println("Invalid ID");
            return;
        }
        // taking input of contact info
        Scanner contact_input = new Scanner(System.in);
        // ask for contact name and remove from list of contacts
        System.out.println("Enter contact's name: ");
        String name = contact_input.nextLine();
        if (supplier.removeContact(name)) {
            System.out.println("Contact removed successfully");
        }
        else {
            System.out.println("Invalid name");
        }
    }


    /*** supply agreements ***/
    // function that adds new supply agreement to list of agreements
    public void addSupplyAgreement() {
        // find supplier in suppliers list
        Scanner agreement_input = new Scanner(System.in);
        System.out.println("Enter supplier's id: ");
        int id = agreement_input.nextInt();
        // get supplier
        Supplier current_supplier = suppliers.get(id);
        if (current_supplier == null) {
            System.out.println("Invalid ID");
            return;
        }
        // if supplier exists, continue with input taking
        // printing menu for user, asking for input
        System.out.println("Enter product code: ");
        int product_code = agreement_input.nextInt();
        if (!products.containsKey(product_code)) {
            System.out.println("Invalid product code");
            return;
        }
        System.out.println("Enter list price per unit: ");
        double price = agreement_input.nextDouble();
        System.out.println("Enter serial number of product in supplier's catalog: ");
        int catalog = agreement_input.nextInt();
        System.out.println("Enter max amount of units: ");
        int amount = agreement_input.nextInt();
        // initialize new supply agreement & add agreement to supplier's list of agreement
        SupplyAgreement newSA = current_supplier.addAgreement(product_code, price, catalog, amount);
        // check if the supplier offers discount
        System.out.println("Do the agreement includes discounts?\n1. Yes\n2. No");
        int option = agreement_input.nextInt();
        // if there are discounts, loop and take input for them until user enters to stop
        while (option == 1) {
            System.out.println("Please enter required number of products: ");
            int numOfProducts = agreement_input.nextInt();
            System.out.println("Is the discount by percentage or by price amount?\n1.By Percentage\n2.By Price");
            int discountOption = agreement_input.nextInt();
            DiscountByProduct newDiscount;
            System.out.println("Please enter discount value: ");
            double discountValue = agreement_input.nextDouble();
            // if discount by percentage, meaning the percentage of the total price for the units of this product will be reduced
            if (discountOption == 1) {
                newDiscount = new DiscountOfPercentageByProduct(discountValue, numOfProducts);
                newSA.addDiscount(newDiscount);
            }
            // if discount by products amount, meaning that a fixed amount of money will be reduced
            else if (discountOption == 2) {
                newDiscount = new DiscountOfPriceByProduct(discountValue, numOfProducts);
                newSA.addDiscount(newDiscount);
            } else {
                // if invalid option,print error message and break to menu
                System.out.println("Invalid option");
                break;
            }
            // if there are more discount, repeat loop
            System.out.println("Do you have more discounts?\n1. Yes\n2. No");
            option = agreement_input.nextInt();
        }
        // add supplier to map of products and suppliers
        ArrayList<Supplier> suppliersOfProduct = suppliersByProduct.get(product_code);
        if (suppliersOfProduct == null)
            suppliersOfProduct = new ArrayList<>();
        suppliersOfProduct.add(current_supplier);
        suppliersByProduct.put(product_code, suppliersOfProduct);
    }

    public void removeSupplyAgreement() {
        // find supplier in suppliers list
        Scanner code_input = new Scanner(System.in);
        System.out.println("Enter supplier's id: ");
        int id = code_input.nextInt();
        // get supplier
        Supplier current_supplier = suppliers.get(id);
        if (current_supplier == null) {
            System.out.println("Invalid ID");
            return;
        }
        // asking for input
        System.out.println("Enter product code of agreement: ");
        int product_code = code_input.nextInt();
        // try remove agreement
        if (current_supplier.removeAgreement(product_code)) {
            // if can remove print success message
            System.out.println("Agreement removed successfully");
            // remove from products by suppliers list
            suppliersByProduct.get(product_code).remove(current_supplier);
        }
        else {
            // if cant remove print error message
            System.out.println("Agreement not found");
        }
    }

    // function that takes in the id of supplier, and prints all his supply agreements
    public void printSupplyAgreements() {
        // find supplier in suppliers list
        Scanner code_input = new Scanner(System.in);
        System.out.println("Enter supplier's id: ");
        int id = code_input.nextInt();
        // get supplier
        Supplier current_supplier = suppliers.get(id);
        if (current_supplier == null) {
            System.out.println("Invalid ID");
            return;
        }
        current_supplier.printSupplyAgreements();
    }

    /*** orders ***/
    public void ordersMenu() {
        // print menu of orders methods
        System.out.println("1. Make new order");
        System.out.println("2. Update order status");
        System.out.println("3. Print all orders");
        System.out.println("4. Print order of supplier");
        // ask for input
        Scanner input = new Scanner(System.in);
        int option = input.nextInt();
        // add new order
        if (option == 1) {
            addOrder();
        }
        // cancel or confirm order
        else if (option == 2) {
            updateOrderStatus();
        }
        // print all orders history
        else if (option == 3) {
            for (Supplier supplier : suppliers.values()) {
                supplier.printOrderHistory();
            }
        }
        // print supplier's orders
        else if (option == 4) {
            // get input of supplier to remove
            System.out.println("Enter supplier's id: ");
            int id = input.nextInt();
            // get supplier
            Supplier supplier = suppliers.get(id);
            // if supplier doesn't exist, print error message and return
            if (supplier == null) {
                System.out.println("Invalid ID");
                return;
            }
            supplier.printOrderHistory();
        }
        else {
            System.out.println("Invalid option");
        }
    }

    // function that takes in supplier id and adds discount by price of order to supplier's list of discounts
    public void addOrderDiscountForSupplier() {
        // taking input
        Scanner order_discount = new Scanner(System.in);
        System.out.println("Enter supplier's id: ");
        int id = order_discount.nextInt();
        // get supplier
        Supplier supplier = suppliers.get(id);
        // if supplier doesn't exist, print error message and return
        if (supplier == null) {
            System.out.println("Invalid ID");
            return;
        }
        System.out.println("Is the discount of percentage or of fixed price?\n1. By Percentage\n2. By Fixed Price");
        int discountOption = order_discount.nextInt();
        DiscountByOrder newDiscount;
        System.out.println("Please enter discount value: ");
        double discountValue = order_discount.nextDouble();
        System.out.println("Please enter minimal price: ");
        double minPrice = order_discount.nextDouble();
        // if discount by percentage, meaning the percentage of the total price
        if (discountOption == 1) {
            newDiscount = new DiscountOfPercentageByOrder(discountValue, minPrice);
            supplier.addOrderDiscount(newDiscount);
        }
        // if discount by fixed price, meaning that a fixed amount of money will be reduced
        else if (discountOption == 2) {
            newDiscount = new DiscountOfPriceByOrder(discountValue, minPrice);
            supplier.addOrderDiscount(newDiscount);
        } else {
            // if invalid option,print error message and break to menu
            System.out.println("Invalid option");
        }
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
        if (!products.containsKey(product_code)) {
            System.out.println("Invalid product code");
            return;
        }
        // adding suppliers who deliver this product to list of capable suppliers
        relevantSuppliers.addAll(suppliersByProduct.get(product_code));
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
            System.out.println("Do you have more products?\n1. Yes\n2. No");
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
                    System.out.println("can't make order!");
                    return;
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
                makeOrderFromSupplier(branch, currentSup, suppliersAndProducts.get(currentSup), productsAndAmounts);
            }
        }
    }

    // function that takes in supplier, hashmap of product codes and total price for their portion, and hashmap of product codes and amounts. it creates an order and
    // adds it to list of supplier's orders
    public Order makeOrderFromSupplier(int branch, Supplier supplier, ArrayList<Integer> productsToOrder, HashMap<Integer, Integer> productsAndAmounts) {
        // informing of the chosen supplier
        System.out.println("ID of chosen supplier: " + supplier.getPrivateCompanyNumber() + ", Name: " + supplier.getName());
        // scanner for input
        Scanner order_input = new Scanner(System.in);
        // taking input for delivery dates based on class:
        // if supplier is on order
        if (supplier.getClass() == OnOrderSupplier.class) {
            System.out.println("Enter number of days to deliver: ");
            int days = order_input.nextInt();
            ((OnOrderSupplier) supplier).setNumberOfDaysToNextOrder(days);
        }
        // if supplier is no transport
        if (supplier.getClass() == NoTransportSupplier.class) {
            System.out.println("Enter day of estimated delivery date: ");
            int day = order_input.nextInt();
            System.out.println("Enter month of estimated delivery date: ");
            int month = order_input.nextInt();
            System.out.println("Enter year of estimated delivery date: ");
            int year = order_input.nextInt();
            ((NoTransportSupplier) supplier).setNextDeliveryDate(day, month, year);
        }
        // starting the order
        Order newOrder = supplier.addNewOrder(branch);
        System.out.println("---INITIALIZING ORDER---");
        // iterating all products and adding to order
        for (int i = 0; i < productsToOrder.size(); i++) {
            int units = productsAndAmounts.get(productsToOrder.get(i));
            String product_name = products.get(productsToOrder.get(i)).getProductName();
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

    // function that takes input of order and desired status, and updates it
    public void updateOrderStatus() {
        Scanner input = new Scanner(System.in);
        // get input of supplier to remove
        System.out.println("Enter supplier's id: ");
        int id = input.nextInt();
        // get supplier
        Supplier supplier = suppliers.get(id);
        // if supplier doesn't exist, print error message and return
        if (supplier == null) {
            System.out.println("Invalid ID");
            return;
        }
        // take input of order information
        System.out.println("Enter number of order: ");
        int order = input.nextInt();
        // find order in supplier's order history
        Order orderToUpdate = supplier.getOrder(order);
        if (orderToUpdate == null) {
            // if can't find, print error message and return to menu
            System.out.println("Invalid order number!");
            return;
        }
        // get option for order update
        System.out.println("What do you wish to do?\n1. Cancel product\n2. Cancel order\n3. Confirm delivery");
        int option = input.nextInt();
        // cancel product
        if (option == 1) {
            // get input of product to cancel
            System.out.println("Enter product code you would like to cancel: ");
            int product = input.nextInt();
            // if can cancel, print success message
            if (orderToUpdate.cancelProduct(product)) {
                System.out.println("Product was cancelled successfully");
            }
            else { // if can't cancel, print error message
                System.out.println("Can't find product!");
            }
        }
        // cancel order
        else if (option == 2) {
            orderToUpdate.cancelOrder();
            System.out.println("Order was cancelled successfully");
        } // confirm delivery
        else if (option == 3) {
            orderToUpdate.confirmDelivery();
            System.out.println("Delivery confirmed");
        }
        else {
            System.out.println("Invalid option");
        }
    }

}

