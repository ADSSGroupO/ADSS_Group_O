package Deliveries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeliveryManagerImpl implements DeliveryManager{
    private TruckController truckController;
    private DriverController driverController;
    private List<DeliveryStop> pendingDeliveryStops;

    private int deliveryCount;
    private int deliveryFormCount;
    private static DeliveryManagerImpl instance = null;
    // Singleton Constructor
   private DeliveryManagerImpl() {
            deliveryCount = 0;
            deliveryFormCount = 0;
            pendingDeliveryStops = new ArrayList<>();
            truckController = TruckController.getInstance();
            driverController = DriverController.getInstance();
    }

    public static DeliveryManagerImpl getInstance() {
        if (instance == null) {
            instance = new DeliveryManagerImpl();
        }
        return instance;
    }

    @Override
    public int addDeliveryStop(Map<String, Integer> items, Site origin, Site destination) {
        DeliveryStop deliveryStop = new DeliveryStop(++deliveryCount, items, origin, destination, TruckType.Regular);
        pendingDeliveryStops.add(deliveryStop);
        // decide how to manage the origin.
        return deliveryStop.getDeliveryId();
    }

    @Override
    public void removeDeliveryStop(int deliveryId) {
        pendingDeliveryStops.remove(deliveryId);
    }



    public DeliveryForm createForm(List<DeliveryStop> stops, Site origin) throws DeliveryException {
        TruckType truckType = getTruckType(stops);
        Truck truck = truckController.pickTruck(truckType);
        Driver driver = driverController.pickDriver(truck.getType(), truck.getMaxWeightTons());
        return new DeliveryForm(deliveryFormCount++, stops, origin, truck.getMaxWeightTons(), driver.getId(),truck.getLicensePlate());//TODO: fix weight

    }

    //maybe private
    public void createDeliveryGroup(){
        HashMap<String,List<DeliveryStop>> originToZones = createDeliveryLists(pendingDeliveryStops);
        System.out.println(originToZones);
        for(Map.Entry<String,List<DeliveryStop>> entries: originToZones.entrySet()){
            try {
                DeliveryForm form = createForm(entries.getValue(), entries.getValue().get(0).getOrigin()); // might be a bit messy, couldn't think of a better way to get the origin
            } catch (DeliveryException e) {
                // Notify UI
                }
        }
        pendingDeliveryStops.clear();
    }

    public TruckType getTruckType(List<DeliveryStop> destinationSitesToVisit) {
        TruckType truckType = TruckType.Regular;
        // for each delivery stop, check if the truck type is the same
        for (DeliveryStop stop : destinationSitesToVisit) {
            if (stop.getTruckTypeRequired() == TruckType.Refrigerated) {
                truckType = TruckType.Refrigerated;
            }
        }
        return truckType;
    }

    public void replanDelivery(DeliveryForm form) {
        // Notify UI
        Truck newTruck = replaceTruck(form);

        // Handle the case where no truck is available
        List<DeliveryStop> stopsToAdd = null;
        List<DeliveryStop> stopsToRemove = null;
    }


    private Truck replaceTruck(DeliveryForm form){
        try {
            Truck newTruck = truckController.pickTruck(form.getTruckType(), form.getDispatchWeightTons());
            form.setMaxWeightAllowed(newTruck.getMaxWeightTons());
            return newTruck;
        } catch (DeliveryException e) {
            // Notify UI
        }
       return null; //????
    }

    private void removeItems(DeliveryForm form){
       List<DeliveryStop> stopsToVisit = form.getDestinationSitesToVisit();
       for(DeliveryStop deliveryStop:stopsToVisit){
           Map<String, Integer> items = deliveryStop.getItems();
           items.remove(0); //need to decide how to remove items
       }
    }

    private void removeStop(DeliveryForm form){
        form.getDestinationSitesToVisit().remove(0); //decide how to remove stops

    }

    private void replaceStops(DeliveryForm form){
       //need zones for that
    }
    public int returnReplanningResponse(List<DeliveryStop> stopsToAdd, List<DeliveryStop> stopsToRemove) {
        // Notify UI
        return 0;
    }


    private HashMap<Site,List<DeliveryStop>> sortStopsByOrigin(List<DeliveryStop> pendingDeliveryStops){
        HashMap<Site,List<DeliveryStop>> sortedByOrigin = new HashMap<>();
        for (DeliveryStop stop : pendingDeliveryStops) {
            Site origin = stop.getOrigin();
            if(!sortedByOrigin.containsKey(origin)){
                ArrayList<DeliveryStop> originStops = new ArrayList<>();
                originStops.add(stop);
                sortedByOrigin.put(origin,originStops);
            }
            else{
                sortedByOrigin.get(origin).add(stop);
            }
        }
        return sortedByOrigin;
    }

    //takes a list of the stops for each origin and separates to lists sorted by zones
    private HashMap<String,List<DeliveryStop>> sortByDeliveryZones(List<DeliveryStop> originsStops){
       HashMap<String,List<DeliveryStop>> deliveryZonesSorted = new HashMap<>(); //key-delivery zone, value-stops in that delivery zones
        for (DeliveryStop stop: originsStops) {
            if (!deliveryZonesSorted.containsKey(stop.getDestination().getDeliveryZone())) {
                    ArrayList<DeliveryStop> deliveryZoneStops = new ArrayList<>();
                    deliveryZoneStops.add(stop);
                    deliveryZonesSorted.put(stop.getDestination().getDeliveryZone(), deliveryZoneStops);
                } else {
                    deliveryZonesSorted.get(stop.getDestination().getDeliveryZone()).add(stop);
                }

        }
        return deliveryZonesSorted;
    }

    public HashMap<String,List<DeliveryStop>> createDeliveryLists(List<DeliveryStop> pendingDeliveryStops){
        HashMap<String,List<DeliveryStop>> originToSortedByZones = new HashMap<>();
        HashMap<Site,List<DeliveryStop>> originSorted = sortStopsByOrigin(pendingDeliveryStops);
        for(Map.Entry<Site,List<DeliveryStop>> originsStops: originSorted.entrySet()){
            List<DeliveryStop> stops = originsStops.getValue();
            HashMap<String,List<DeliveryStop>> zoneSorted = sortByDeliveryZones(stops);
            for (Map.Entry<String,List<DeliveryStop>> entries: zoneSorted.entrySet()) {
                originToSortedByZones.put(entries.getKey(),entries.getValue());
            }
        }
        return originToSortedByZones;
    }

    public TruckController getTruckController() {
        return truckController;
    }

    public DriverController getDriverController() {
        return driverController;
    }
}


