package Suppliers.PresentationLayer;
import Suppliers.*;
import Suppliers.BusinessLayer.*;

import java.lang.reflect.Array;
import java.util.*;

public class CLI {

    // initialize CLI
    public CLI() {
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
        // add supplier
        if (option == 1) {
            addSupplier();
        }
        // remove supplier
        else if (option == 2) {
            removeSupplier();
        }
        // edit supplier
        else if (option == 3) {
            editSupplierInfo();
        }
        else {
            throw new IllegalArgumentException("Invalid option!");
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
            throw new IllegalArgumentException("Invalid option!");
        }
        // find the supplier that needs to be edited
        System.out.println("Enter supplier's id: ");
        int id = info_input.nextInt();
        // change name
        if (option == 1) {
            info_input.nextLine();
            System.out.println("Enter new name: ");
            String name = info_input.nextLine();
            SupplierController.getInstance().changeSupplierName(id, name);
            System.out.println("Name was changed successfully");
        }
        // change bank account number
        else if (option == 2) {
            System.out.println("Enter new bank account: ");
            int bank = info_input.nextInt();
            SupplierController.getInstance().changeBankAccount(id, bank);
            System.out.println("Bank account was changed successfully");
        }
        // change preferred payment method
        else if (option == 3) {
            System.out.println("Choose new preferred payment method:\n1. Credit\n2. Checks\n3. Cash\n4. Transfer To Account");
            int pay = info_input.nextInt();
            Payment payment = Payment.values()[pay - 1];
            SupplierController.getInstance().changePayment(id, payment);
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
            // get all the relevant days
            System.out.println("Enter number of shipping days: ");
            int numberOfDays = supplier_input.nextInt();
            // create list of ship days
            ArrayList<Integer> days = new ArrayList<>();
            // get all delivery dates
            for (int i = 0; i < numberOfDays; i++) {
                System.out.println("Choose day:\n1. Sunday\n2. Monday\n3. Tuesday\n4. Wednesday\n5. Thursday\n6. Friday\n7. Saturday");
                int currentDay = supplier_input.nextInt();
                while (currentDay < 1 || currentDay > 7) {
                    System.out.println("Invalid option. enter new option");
                    currentDay = supplier_input.nextInt();
                }
                days.add(currentDay);
            }
            // create supplier
            SupplierController.getInstance().addFixedDaysSupplier(supplier_name, supplier_id, bank, payment, days);
        }
        // if supplier delivers on order
        else if (option == 2) {
            SupplierController.getInstance().addOnOrderSupplier(supplier_name, supplier_id, bank, payment);
        }
        // if supplier has no transportation and needs pick up service
        else if (option == 3) {
            supplier_input.nextLine();
            System.out.println("Enter supplier's pick up address: ");
            String address = supplier_input.nextLine();
            SupplierController.getInstance().addNoTransportSupplier(supplier_name, supplier_id, bank, payment, address);
        } else {
            // if option is invalid, throw exception
            throw new IllegalArgumentException("Invalid option");
        }
    }

    // function that takes in supplier's id and removes it from system
    public void removeSupplier() {
        Scanner input = new Scanner(System.in);
        // get input of supplier to remove
        System.out.println("Enter supplier's id: ");
        int id = input.nextInt();
        SupplierController.getInstance().removeSupplierByID(id);
        // inform supplier was deleted
        System.out.println("Supplier deleted successfully");
    }

    // function for adding contact of supplier
    public void addContact(int supplier_id) {
        // taking input of contact info
        Scanner contact_input = new Scanner(System.in);
        System.out.println("Enter contact's name: ");
        String name = contact_input.nextLine();
        System.out.println("Enter phone number: ");
        String phone = contact_input.nextLine();
        SupplierController.getInstance().addContactToSupplier(supplier_id, name, phone);
        System.out.println("Contact added successfully");
    }

    // function for removing contact of supplier
    public void removeContact(int supplier_id) {
        // taking input of contact info
        Scanner contact_input = new Scanner(System.in);
        // ask for contact name and remove from list of contacts
        System.out.println("Enter contact's name: ");
        String name = contact_input.nextLine();
        System.out.println("Enter contact's phone: ");
        String phone = contact_input.nextLine();
        SupplierController.getInstance().removeContactOfSupplier(supplier_id, name, phone);
        // inform user
        System.out.println("Contact removed successfully");
    }


    /*** supply agreements ***/
    // function that adds new supply agreement to list of agreements
    public void addSupplyAgreement() {
        // find supplier in suppliers list
        Scanner agreement_input = new Scanner(System.in);
        System.out.println("Enter supplier's id: ");
        int id = agreement_input.nextInt();
        // printing menu for user, asking for input
        System.out.println("Enter product code: ");
        int product_code = agreement_input.nextInt();
        System.out.println("Enter list price per unit: ");
        double price = agreement_input.nextDouble();
        System.out.println("Enter serial number of product in supplier's catalog: ");
        int catalog = agreement_input.nextInt();
        System.out.println("Enter max amount of units: ");
        int amount = agreement_input.nextInt();
        // add new supply agreement
        SupplyAgreement agreement = SupplyAgreementController.getInstance().addSupplyAgreement(id, product_code, price, catalog, amount);
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
            // create discount from input
            SupplyAgreementController.getInstance().addDiscountByProductToAgreement(agreement, discountValue, numOfProducts, discountOption);
            // if there are more discount, repeat loop
            System.out.println("Do you have more discounts?\n1. Yes\n2. No");
            option = agreement_input.nextInt();
        }
    }

    public void removeSupplyAgreement() {
        // find supplier in suppliers list
        Scanner code_input = new Scanner(System.in);
        System.out.println("Enter supplier's id: ");
        int id = code_input.nextInt();
        // asking for input
        System.out.println("Enter product code of agreement: ");
        int product_code = code_input.nextInt();
        // remove agreement
        SupplyAgreementController.getInstance().removeSupplyAgreement(id, product_code);
        // print message to user
        System.out.println("Agreement removed successfully");
    }

    // function that takes in the id of supplier, and prints all his supply agreements
    public void printSupplyAgreements() {
        // find supplier in suppliers list
        Scanner code_input = new Scanner(System.in);
        System.out.println("Enter supplier's id: ");
        int id = code_input.nextInt();
        // print all agreements
        SupplyAgreementController.getInstance().printAllSupplyAgreementsOfSupplier(id);
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
            OrderController.getInstance().printAllOrders();
        }
        // print supplier's orders
        else if (option == 4) {
            // get input of supplier to remove
            System.out.println("Enter supplier's id: ");
            int id = input.nextInt();
            SupplierController.getInstance().printOrdersOfSupplier(id);
        }
        else {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    // function that takes in supplier id and adds discount by price of order to supplier's list of discounts
    public void addOrderDiscountForSupplier() {
        // taking input
        Scanner order_discount = new Scanner(System.in);
        System.out.println("Enter supplier's id: ");
        int id = order_discount.nextInt();
        System.out.println("Is the discount of percentage or of fixed price?\n1. By Percentage\n2. By Fixed Price");
        int discountOption = order_discount.nextInt();
        DiscountByOrder newDiscount;
        System.out.println("Please enter discount value: ");
        double discountValue = order_discount.nextDouble();
        System.out.println("Please enter minimal price: ");
        double minPrice = order_discount.nextDouble();
        // add discount to supplier
        SupplyAgreementController.getInstance().addDiscountByOrder(id, discountValue, minPrice, discountOption);
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

    // function that takes input of order and desired status, and updates it
    public void updateOrderStatus() {
        Scanner input = new Scanner(System.in);
        // take input of order information
        System.out.println("Enter number of order: ");
        int order = input.nextInt();
        // get option for order update
        System.out.println("What do you wish to do?\n1. Cancel product\n2. Cancel order\n3. Confirm delivery");
        int option = input.nextInt();
        // cancel product
        if (option == 1) {
            // get input of product to cancel
            System.out.println("Enter product code you would like to cancel: ");
            int product = input.nextInt();
            OrderController.getInstance().cancelProductOfOrder(order, product);
            System.out.println("Product was cancelled successfully");

        }
        // cancel order
        else if (option == 2) {
            OrderController.getInstance().cancelOrder(order);
            System.out.println("Order was cancelled successfully");
        } // confirm delivery
        else if (option == 3) {
            OrderController.getInstance().confirmOrder(order);
            System.out.println("Delivery confirmed");
        }
        else {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    // TO DO: update dates of supplier's delivery by using this input configuration
    // should do it after creating new making orders method
    /*
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
     */

}

