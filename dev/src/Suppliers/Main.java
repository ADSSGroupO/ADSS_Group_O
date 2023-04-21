package Suppliers;

import Suppliers.PresentationLayer.CLI;

public class Main {
    public static void main(String[] args) {
        CLI controller = new CLI();
        // Add some products to the list
        controller.products.put(1, new Product("Apple", "1234", "Fruits"));
        controller.products.put(2, new Product("Milk", "5678", "Dairy"));
        controller.products.put(3, new Product("Bread", "9012", "Bakery"));
        controller.products.put(4, new Product("Orange", "1234", "Fruits"));
        controller.products.put(5, new Product("Yogurt", "5678", "Dairy"));
        controller.products.put(6, new Product("Croissant", "9012", "Bakery"));
        controller.products.put(7, new Product("Banana", "1234", "Fruits"));
        controller.products.put(8, new Product("Cheese", "5678", "Dairy"));
        controller.products.put(9, new Product("Baguette", "9012", "Bakery"));
        // start menu
        controller.printMenu();
    }
}
