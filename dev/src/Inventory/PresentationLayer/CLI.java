package dev.src.Inventory.PresentationLayer;

import dev.src.Inventory.ServiceLayer.ServiceController;

import java.time.LocalDate;
import java.util.Scanner;

public class CLI {
    private final Scanner scanner = new Scanner(System.in);
    private final ServiceController serviceController = ServiceController.getInstance();
    public void start() {
        System.out.println("Hello, What would you like to do?");
        System.out.println("1. Add a product");
        System.out.println("2. Add a category");
        System.out.println("3. Add a item to a product");
        System.out.println("4. Set minimum amount of a product");
        System.out.println("5. Set discount by product");
        System.out.println("6. Set discount by category");
        System.out.println("7. Get products by category");
        System.out.println("8. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("Enter product name");
                String name = scanner.next();
                System.out.println("Enter product minimum amount");
                int minAmount = scanner.nextInt();
                System.out.println("Enter product category id");
                int categoryID = scanner.nextInt();
                System.out.println("Enter product makat");
                int makat = scanner.nextInt();
                System.out.println("Enter product supplier id");
                int supplierID = scanner.nextInt();
                serviceController.addProduct(name, minAmount, categoryID, makat, supplierID);
            }
            case 2 -> {
                System.out.println("Enter category name");
                String categoryName = scanner.next();
                System.out.println("Enter category id");
                int categoryID1 = scanner.nextInt();
                serviceController.addCategory(categoryName, categoryID1);
            }
            case 3 -> {
                System.out.println("Enter item manufacturer");
                String manufacturer = scanner.next();
                System.out.println("Enter item barcode");
                int barcode = scanner.nextInt();
                System.out.println("Enter item name");
                String name1 = scanner.next();
                System.out.println("Enter item expiration date in the form of yyyy-mm-dd");
                String expirationDate = scanner.next();
                System.out.println("Enter item cost price");
                float costPrice = scanner.nextFloat();
                System.out.println("Enter item selling price");
                float sellingPrice = scanner.nextFloat();
                System.out.println("Enter item category id");
                int categoryID2 = scanner.nextInt();
                System.out.println("Enter item product id");
                int productID = scanner.nextInt();
                serviceController.addItem(manufacturer, barcode, name1, LocalDate.parse(expirationDate), costPrice, sellingPrice, categoryID2, productID);
            }
            case 4 -> {
                System.out.println("Enter product id");
                int productID1 = scanner.nextInt();
                System.out.println("Enter product demand");
                int demand = scanner.nextInt();
                System.out.println("Enter how many days till supplier arrives");
                int days = scanner.nextInt();
                serviceController.setMinimum(days, demand, productID1);

            }
            case 5 -> {
                System.out.println("Enter product id");
                int productID2 = scanner.nextInt();
                System.out.println("Enter product discount");
                float discount = scanner.nextFloat();
                System.out.println("Enter product start date");
                String start = scanner.next();
                System.out.println("Enter product end date");
                String end = scanner.next();
                serviceController.setDiscountByProduct(productID2, discount, start, end);
            }
            case 6 -> {
                System.out.println("Enter category id");
                int categoryID2 = scanner.nextInt();
                System.out.println("Enter category discount");
                float discount1 = scanner.nextFloat();
                System.out.println("Enter category start date");
                String start1 = scanner.next();
                System.out.println("Enter category end date");
                String end1 = scanner.next();
                serviceController.setDiscountByCategory(categoryID2, discount1, start1, end1);
            }
            case 7 -> {
                System.out.println("Enter category id");
                int categoryID3 = scanner.nextInt();
                serviceController.getProductsByCategory(categoryID3);
            }
            case 8 -> System.exit(0);
        }
    }
}
