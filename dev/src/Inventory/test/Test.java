//package dev.src.Inventory.test;
//
//import dev.src.Inventory.BuisnessLayer.Product;
//import dev.src.Inventory.ServiceLayer.ServiceController;
//import org.junit.jupiter.api.BeforeEach;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class Test {
//
////    @BeforeAll
////    public static void setupAll(){
////        System.out.println("Should print before all the tests");
////    }
//
//    @BeforeEach
//    void setUp() {
//        ServiceController service = ServiceController.getInstance();
//        service.createEmployee("318856994", "itay gershon", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("234567891", "gal halifa", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("123456789", "dan terem", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("345678912", "noa aviv", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("456789123", "nave avraham", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("789123456", "gili gershon", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("891234567", "amit halifa", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("012345678", "shachar bardugo", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("123456780", "nadia zlenko", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("234567801", "yossi gershon", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("345678012", "eti gershon", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("456780123", "amit sasson", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("567801234", "itamar shemesh", "123456", 1000, "yahav", "good conditions");
//        service.createEmployee("147258369", "dina agapov", "123456", 1, "yahav", "bad");
//        service.createEmployee("258369147", "mor shuker", "123456", 1, "yahav", "bad");
//    }
//
//
//    @org.junit.jupiter.api.Test
//    void createEmployee() {
//        ServiceController service = ServiceController.getInstance();
//
//        assertFalse(service.createEmployee("318856994", "itay gershon", "123456", 1000, "yahav", "good conditions"));
//        assertTrue(ServiceController.getInstance().createEmployee("318856995", "itay gershon", "123456", 1000, "yahav", "good conditions"));
//        assertFalse(ServiceController.getInstance().createEmployee("318856994", "yossi abuksis", "123456", 1000, "yahav", "good conditions"));
//    }
//
//    @org.junit.jupiter.api.Test
//    void removeEmployee() {
//        ServiceController service = ServiceController.getInstance();
//        assertTrue(ServiceController.removeEmployee("318856994"));
//        assertFalse(ServiceController.removeEmployee("318856994"));
//        assertFalse(ServiceController.removeEmployee("318856993"));
//        assertTrue(ServiceController.removeEmployee("123456789"));
//    }
//
//    @org.junit.jupiter.api.Test
//    void certifyEmployee() {
//        ServiceController service = ServiceController.getInstance();
//        Product p = service.getEmployee("318856994");
//        e.addCertification(JobType.HR_MANAGER);
//        e.addCertification(JobType.STOCK_KEEPER);
//        e.addCertification(JobType.SHIFT_MANAGER);
//        assertTrue(service.isCertified("318856994", JobType.HR_MANAGER));
//        assertTrue(service.isCertified("318856994", JobType.STOCK_KEEPER));
//        assertTrue(service.isCertified("318856994", JobType.SHIFT_MANAGER));
//        assertFalse(service.isCertified("318856994", JobType.MERCHANDISER));
//        Employee e2 = service.getEmployee("123456789");
//        e2.addCertification(JobType.CASHIER);
//        assertTrue(service.isCertified("123456789", JobType.CASHIER));
//        assertFalse(service.isCertified("123456789", JobType.HR_MANAGER));
//    }
//
//    @org.junit.jupiter.api.Test
//    void login() {
//        ServiceController service = ServiceController.getInstance();
//        assertTrue(service.login("123456789","123456"));
//        assertFalse(service.login("318856994", "123457"));
//    }
//
//    @org.junit.jupiter.api.Test
//    void exists() {
//        ServiceController service = ServiceController.getInstance();
//        assertTrue(service.exists("123456789"));
//        assertTrue(service.exists("318856994"));
//        assertFalse(service.exists("208595756"));
//    }
//
//    @org.junit.jupiter.api.Test
//    void isHRManager() {
//        ServiceController service = ServiceController.getInstance();
//        Employee e = service.getEmployee("318856994");
//        e.addCertification(JobType.HR_MANAGER);
//        assertFalse(service.isHRManager("123456789"));
//        assertTrue(service.isHRManager("318856994"));
//    }
//
//    @org.junit.jupiter.api.Test
//    void isCertified() {
//        ServiceController service = ServiceController.getInstance();
//        Employee e = service.getEmployee("234567891");
//        e.addCertification(JobType.DRIVER);
//        assertTrue(service.isCertified("234567891",JobType.DRIVER));
//        assertFalse(service.isCertified("234567891",JobType.HR_MANAGER));
//    }
//
//    @org.junit.jupiter.api.Test
//    void resetSchedule() {
//        ServiceController service = ServiceController.getInstance();
//        Employee e = service.getEmployee("318856994");
//        e.addAvailableTimeSlot(new ShiftPair("sunday", Time.MORNING));
//        assertEquals(1,e.getAvailabilitySchedule().getSchedule().size());
//        assertTrue(service.resetSchedule("318856994"));
//        assertEquals(0,e.getAvailabilitySchedule().getSchedule().size());
//
//    }
//
//    @org.junit.jupiter.api.Test
//    void createShift() {
//        ServiceController service = ServiceController.getInstance();
//        ShiftPair shiftPair = new ShiftPair("monday", Time.EVENING);
//        Employee shiftManager = service.getEmployee("318856994");
//        shiftManager.addCertification(JobType.SHIFT_MANAGER);
//        Employee cashier1 = service.getEmployee("123456789");
//        Employee cashier2 = service.getEmployee("456789123");
//        cashier1.addCertification(JobType.CASHIER);
//        cashier2.addCertification(JobType.CASHIER);
//        String cashierIDs = cashier1.getId() + "," + cashier2.getId();
//        Employee merchandiser1 = service.getEmployee("789123456");
//        Employee merchandiser2 = service.getEmployee("891234567");
//        merchandiser1.addCertification(JobType.MERCHANDISER);
//        merchandiser2.addCertification(JobType.MERCHANDISER);
//        String merchandisersIDs = cashier1.getId() + "," + cashier2.getId();
//        Employee driver = service.getEmployee("147258369");
//        driver.addCertification(JobType.DRIVER);
//        String driverID = driver.getId();
//        Employee stockKeeper = service.getEmployee("147258369");
//        stockKeeper.addCertification(JobType.STOCK_KEEPER);
//        String stockKeeperID = stockKeeper.getId();
//        Response r1 = service.createShift(shiftPair, shiftManager.getId(), cashierIDs, driverID, merchandisersIDs, stockKeeperID);
//        assertFalse(r1.errorOccurred());
//        r1 = facade.createShift(shiftPair, shiftManager.getId(), cashierIDs, driverID, merchandisersIDs, stockKeeperID);
//        assertTrue(r1.errorOccurred()); // create the same shift twice
//        driver.addCertification(JobType.STOCK_KEEPER);
//        stockKeeperID += "147258369";
//        shiftPair = new ShiftPair("tuesday", Time.MORNING);
//        Response r2 = service.createShift(shiftPair, shiftManager.getId(), cashierIDs, driverID, merchandisersIDs, stockKeeperID);
//        assertTrue(r2.errorOccurred()); // put the same employee in two jobs
//    }
//
//    @org.junit.jupiter.api.Test
//    void getAllCertified() {
//        ServiceController service = ServiceController.getInstance();
//        service.certifyEmployee(JobType.CASHIER, "318856994");
//        service.certifyEmployee(JobType.CASHIER, "123456789");
//        service.certifyEmployee(JobType.CASHIER, "234567891");
//        List<String[]> cashiers = service.getAllCertified(JobType.CASHIER, new ShiftPair("sunday", Time.MORNING), true);
//        for (String[] details : cashiers){
//            String id = details[1].substring(6);
//            Employee e = service.getEmployee(id);
//            assertTrue(e.isCertified(JobType.CASHIER));
//        }
//        assertEquals(3, cashiers.size());
//
//    }
//
//}
