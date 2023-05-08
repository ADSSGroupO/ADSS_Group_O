package Deliveries.BusinessLayer;

import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryException;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.DataAccessLayer.DeliveryFormDAO;
import HR.BusinessLayer.ShiftController;
import HR_Deliveries_Interface.DeliveryIntegrator;
import HR_Deliveries_Interface.HRIntegrator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeliveryFormsController implements DeliveryIntegrator {
    private final Set<DeliveryForm> pendingDeliveryForms; // Improve to separate by status
    private final Set<DeliveryForm> completedDeliveryForms;
    // singleton
    private static DeliveryFormsController instance;
    private int deliveryFormCount;
    private final TruckController truckController;
    private final DriverController driverController;
    private final DeliveryFormDAO deliveryFormDAO;
    private boolean TESTING_MODE;


    private DeliveryFormsController() {
        pendingDeliveryForms = new HashSet<>();
        completedDeliveryForms = new HashSet<>();
        deliveryFormCount = 0;
        truckController = TruckController.getInstance();
        driverController = DriverController.getInstance();
        deliveryFormDAO = new DeliveryFormDAO();
        pendingDeliveryForms.addAll(deliveryFormDAO.loadData());
        TESTING_MODE = false;
        // TODO: Implement properly

    }

    public static DeliveryFormsController getInstance() {
        if (instance == null) {
            instance = new DeliveryFormsController();
        }
        return instance; // Does it have to be a singleton?
    }


    private void addDeliveryForm(DeliveryForm deliveryForm) {
        pendingDeliveryForms.add(deliveryForm);
    }

    public void removeDeliveryForm(DeliveryForm deliveryForm) {
        this.pendingDeliveryForms.remove(deliveryForm);
    }

    public Set<DeliveryForm> getPendingDeliveryForms() {
        return pendingDeliveryForms;
    }

    public void startDeliveryForm(DeliveryForm deliveryForm) {
        deliveryForm.startJourney();
    }

    // print deliveries
    public void printPendingDeliveryForms() {
        for (DeliveryForm deliveryForm : pendingDeliveryForms) {
            System.out.println(deliveryForm);
        }
    }

    public void printCompletedDeliveryForms() {
        for (DeliveryForm deliveryForm : completedDeliveryForms) {
            System.out.println(deliveryForm);
        }
    }

    public void terminateDeliveryForm(DeliveryForm deliveryForm) {
        pendingDeliveryForms.remove(deliveryForm);
        completedDeliveryForms.add(deliveryForm);
    }

    public DeliveryForm getDeliveryForm(int id) {
        for (DeliveryForm deliveryForm : pendingDeliveryForms) {
            if (deliveryForm.getFormId() == id) {
                return deliveryForm;
            }
        }
        return null;
    }

    @Override
    public Set<DeliveryStop> getDeliverygetDeliveryByArrivalTime(Timestamp startTime, Timestamp finishTime, String store) {
        Set<DeliveryStop> deliveryStops = new HashSet<>();
        for (DeliveryForm deliveryForm : pendingDeliveryForms) {
            deliveryStops.addAll(deliveryForm.getStopsByTime(startTime, finishTime, store));
        }
        return deliveryStops;
    }

    /**
     * @return
     */
    public void createForm(List<DeliveryStop> stops, Site origin) throws DeliveryException {
        DeliveryForm deliveryForm = new DeliveryForm(deliveryFormCount++, stops, origin,
                new Timestamp(System.currentTimeMillis()));
        TruckType truckType = getTruckType(stops);
        Truck truck = truckController.pickTruck(truckType);
        deliveryForm.setTruck(truck);
        Driver driver = driverController.pickDriver(truck, deliveryForm.getDispatchTime(),
                deliveryForm.getEstimatedTerminationTime());

        deliveryForm.setDriver(driver);
        addDeliveryForm(deliveryForm);
    }



    private TruckType getTruckType(List<DeliveryStop> destinationSitesToVisit) {
        TruckType truckType = TruckType.Regular;
        // for each delivery stop, check if the truck type is the same
        for (DeliveryStop stop : destinationSitesToVisit) {
            if (stop.getTruckTypeRequired() == TruckType.Refrigerated) {
                truckType = TruckType.Refrigerated;
                return truckType;
            }
        }
        return truckType;
    }

    /**
     * should only be called if testing the class
     */
    public TruckType getTruckTypeTest(List<DeliveryStop> destinationSitesToVisit) throws Exception{
        if (!TESTING_MODE){
            throw new Exception("Not in testing mode");
        }
        return getTruckType(destinationSitesToVisit);
    }

    /**
     * should only be called if testing the class
     */
    public void setTestingMode(){
        TESTING_MODE = true;
    }



    /*
    To be used only for testing
     */
    public void createForm(List<DeliveryStop> stops, Site origin, Timestamp dispatchTime ,HRIntegrator hr) throws Exception {
        if (!TESTING_MODE) throw new Exception();
        DeliveryForm deliveryForm = new DeliveryForm(deliveryFormCount++, stops, origin,
                dispatchTime, hr);
        TruckType truckType = getTruckType(stops);
        Truck truck = truckController.pickTruck(truckType);
        deliveryForm.setTruck(truck);
        Driver driver = driverController.pickDriver(truck, deliveryForm.getDispatchTime(),
                deliveryForm.getEstimatedTerminationTime());

        deliveryForm.setDriver(driver);
        addDeliveryForm(deliveryForm);
    }
}