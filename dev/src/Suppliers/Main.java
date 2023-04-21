package Suppliers;

import Suppliers.PresentationLayer.CLI;

public class Main {
    public static void main(String[] args) {
        CLI controller = new CLI();
        // start menu
        controller.printMenu();
    }
}
