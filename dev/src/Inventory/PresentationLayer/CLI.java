package Inventory.PresentationLayer;
import Inventory.ServiceLayer.ServiceController;
import Inventory.ServiceLayer.ProductService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    private final Scanner scanner = new Scanner(System.in);
    private final ServiceController serviceController = ServiceController.getInstance();
    public void start() {
        int choice;
        do{
//            String localDir = System.getProperty("user.dir");
//            System.out.println(localDir);
            serviceController.starConnection();

            printMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1 : {
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
                    System.out.println("Enter product sub category");
                    String sub_category= scanner.next();
                    if(serviceController.addProduct(name, minAmount, categoryID,sub_category, makat, supplierID)){
                        System.out.println("Product added successfully");
                        start();
                    }
                    else{
                        System.out.println("Product couldn't be added start from the beginning");
                        start();
                    }
                }
                case 2 : {
                    System.out.println("Enter category name");
                    String categoryName = scanner.next();
                    System.out.println("Enter category id");
                    int categoryID1 = scanner.nextInt();
                    try{
                        serviceController.addCategory(categoryName, categoryID1);
                        System.out.println("Category added successfully");
                        start();
                }catch(Exception e){
                        System.out.println("Category couldn't be added start from the beginning");
                        start();
                    }
                }
                case 3 : {
                    System.out.println("How many items do you want to add?");
                    int numOfItems = scanner.nextInt();
                    if(numOfItems==1){
                        System.out.println("Enter item manufacturer");
                        String manufacturer = scanner.next();
                        System.out.println("Enter first item barcode");
                        int barcode = scanner.nextInt();
                        System.out.println("Enter item name");
                        String name1 = scanner.next();
                        System.out.println("Enter item expiration date in the form of yyyy-mm-dd");
                        String expirationDate = scanner.next();
                        System.out.println("Enter item cost price");
                        float costPrice = scanner.nextFloat();
                        System.out.println("Enter item category id");
                        int categoryID2 = scanner.nextInt();
                        System.out.println("Enter item product id");
                        int productID = scanner.nextInt();
                        System.out.println("Enter item size if exist otherwise press enter or write null");
                        String size = scanner.next();
                        try{
                            for (int i = 0; i < numOfItems; i++) {
                                serviceController.addItem(manufacturer, barcode, name1, expirationDate, costPrice, categoryID2, productID,size);
                                barcode++;
                            }
                            System.out.println("Items added successfully");
                            start();
                        }catch(Exception e){
                            System.out.println("Items couldn't be added start from the beginning");
                            start();
                        }
                    }
                    else{
                        addItem();
                        start();
                    }
                }
                case 4 : {
                    System.out.println("Enter product id");
                    int productID1 = scanner.nextInt();
                    System.out.println("Enter product demand by how many items per day");
                    int demand = scanner.nextInt();
                    System.out.println("Enter how many days till supplier arrives");
                    int days = scanner.nextInt();
                    try{
                        serviceController.setMinimum(days, demand, productID1);
                        System.out.println("Minimum set successfully");
                        start();
                }catch(Exception e){
                        System.out.println("Minimum couldn't be set start from the beginning");
                        start();
                    }
                }
                case 5 : {
                    System.out.println("Enter product id");
                    int productID2 = scanner.nextInt();
                    System.out.println("Enter product discount");
                    float discount = scanner.nextFloat();
                    System.out.println("Enter product start date in the form of yyyy-mm-dd");
                    String start = scanner.next();
                    System.out.println("Enter product end date in the form of yyyy-mm-dd");
                    String end = scanner.next();
                    try{
                        serviceController.setDiscountByProduct(productID2, discount, start, end);
                        System.out.println("Discount set successfully");
                        start();
                    }catch(Exception e){
                        System.out.println("Discount couldn't be set start from the beginning");
                        start();
                    }
                }
                case 6 : {
                    System.out.println("Enter category id");
                    int categoryID2 = scanner.nextInt();
                    System.out.println("Enter category discount");
                    float discount1 = scanner.nextFloat();
                    System.out.println("Enter category start date");
                    String start = scanner.next();
                    System.out.println("Enter category end date");
                    String end = scanner.next();
                    try{
                        serviceController.setDiscountByCategory(categoryID2, discount1, start, end);
                        System.out.println("Discount set successfully");
                        start();
                    }catch(Exception e){
                        System.out.println("Discount couldn't be set, start from the beginning");
                        start();
                    }
                }
                case 7 : {
                    System.out.println("Enter category id");
                    int categoryID3 = scanner.nextInt();
                    System.out.println(serviceController.getProductsByCategory(categoryID3));
                    start();
                }
                case 8 : {
                    serviceController.getExpiredReport();
                    start();
                }
                case 9 : {
                    System.out.println("Enter how many days till expiration");
                    int days = scanner.nextInt();
                    serviceController.getToBeExpiredReport(days);
                    start();
                }
                case 10 : {
                    serviceController.getDefectiveReport();
                    start();
                }
                case 11 : {
                    System.out.println("Enter how often to get defective report");
                    int days = scanner.nextInt();
                    serviceController.setDefectiveReport(days);
                    start();
                }
                case 12 : {
                    serviceController.getInventoryReport();
                    start();
                }
                case 13 : {
                    System.out.println("Enter product id");
                    int productID = scanner.nextInt();
                    try{
                        System.out.println(serviceController.getAmountOfProduct(productID));
                        start();
                }catch(Exception e){
                        System.out.println("Product doesn't exist");
                        start();
                    }
                }
                case 14 :{
                    System.out.println("Enter supplier id");
                    int supplierID = scanner.nextInt();
                    System.out.println("Enter Product id");
                    int productID = scanner.nextInt();
                    System.out.println("Enter the amount of discount from the supplier");
                    Double discount = scanner.nextDouble();
                    try{
                        serviceController.setDiscountBySupplier(supplierID, productID, discount);
                        start();
                }catch(Exception e){
                        System.out.println("Product doesn't exist");
                        start();
                    }
                }
                case 15 : {
                    System.out.println("Enter Product id");
                    int productID = scanner.nextInt();
                    try{
                        System.out.println(serviceController.getDiscountsByProductId(productID));
                        start();
                }catch(Exception e){
                        System.out.println("Product doesn't exist");
                        start();
                    }
                }
                case 16 : {
                    serviceController.addData();
                    start();
                }
                case 17 : {
                    System.out.println("Enter how many categories you would like to be in the report");
                    int amountCategory = scanner.nextInt();
                    ArrayList<Integer> categoryList = new ArrayList<>();
                    for(int i=0 ; i<amountCategory;i++){
                        System.out.println("Enter the category you would like to be in the report");
                        categoryList.add(scanner.nextInt());
                    }
                    try{
                        System.out.println(serviceController.getInventoryReportByCategory(categoryList));
                        start();
                }catch(Exception e){
                        System.out.println("Category doesn't exist");
                        start();
                    }
                }
                case 18 : {
                    System.out.println("Enter the ID of the item you would like to move to the store from the storage");
                    int itemID = scanner.nextInt();
                    System.out.println("Enter the ID of the category of the item");
                    int categoryID = scanner.nextInt();
                    try{
                        serviceController.moveItemToStore(categoryID, itemID);
                        start();
                    }catch(Exception e){
                        System.out.println("Item couldn't be moved");
                        start();
                    }

                }
                case 19 : {//option to sell item
                    System.out.println("Enter the ID of the item you would like to sell");
                    int itemID = scanner.nextInt();
                    System.out.println("Enter the ID of the category of the item");
                    int categoryID = scanner.nextInt();
                    try{
                        serviceController.itemSold(categoryID, itemID);
                        start();
                    }catch(Exception e){
                        System.out.println("Item couldn't be sold");
                        start();
                    }
                }
                case 20 : {System.exit(0);}
                default : {
                    System.out.println("Unexpected value: " + choice);
                    System.out.println("Please try again");
                    start();
                }
            }
}
        while (true);
    }
    public void printMenu(){
        System.out.println("Hello, What would you like to do?");
        System.out.println("1. Add a product");
        System.out.println("2. Add a category");
        System.out.println("3. Add an item or items to a product");
        System.out.println("4. Set minimum amount of a product");
        System.out.println("5. Set discount by product");
        System.out.println("6. Set discount by category");
        System.out.println("7. Get products by category");
        System.out.println("8. Get expired report");
        System.out.println("9. Get set to be expired report");
        System.out.println("10. Get defective report");
        System.out.println("11. Set how often to get defective report");
        System.out.println("12. Get inventory report");
        System.out.println("13. Get amount of a product");
        System.out.println("14. Set discount by supplier");
        System.out.println("15. Get discounts by product id");
        System.out.println("16. Add sample data");
        System.out.println("17. Get inventory report by category/ies");
        System.out.println("18. Move item to the store");
        System.out.println("19. Sell item");
        System.out.println("20. Exit");
    }
    public void addItem(){
        System.out.println("Enter item manufacturer");
        String manufacturer = scanner.next();
        System.out.println("Enter item barcode");
        int barcode = scanner.nextInt();
        System.out.println("Enter item name");
        String name = scanner.next();
        System.out.println("Enter item expiration date in the form of yyyy-mm-dd");
        String expirationDate = scanner.next();
        System.out.println("Enter item cost price");
        float costPrice = scanner.nextFloat();
        System.out.println("Enter item category id");
        int categoryID2 = scanner.nextInt();
        System.out.println("Enter item product id");
        int productID = scanner.nextInt();
        System.out.println("Enter item size if exist otherwise press enter or write null");
        String size = scanner.next();
        try{
            serviceController.addItem(manufacturer, barcode, name, expirationDate, costPrice, categoryID2, productID,size);
        }
        catch (Exception e){
            System.out.println("Invalid input try again from the beginning");
            start();
        }
        System.out.println("Item added successfully");
    }
}