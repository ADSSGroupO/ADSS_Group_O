package Deliveries.BusinessLayer;

import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryStatus;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class DeliveryStop {
    public static final int AVERAGE_TRUCK_SPEED_KM_AN_H = 80;
    public static final int BASE_DISTANCE = 10;
    private final int shipmentInstanceID;
    private Map<String, Integer> items; // Not final because we might want to support removal of items in the future
    private final Site origin;
    private final Site destination;
    private TruckType truckTypeRequired;
    private DeliveryStatus status;

    private Timestamp estimatedArrivalTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryStop that)) return false;

        return getShipmentInstanceID() == that.getShipmentInstanceID();
    }

    @Override
    public int hashCode() {
        return getShipmentInstanceID();
    }

    @Override
    public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("DeliveryStop{")
                    .append("shipmentInstanceID=")
                    .append(shipmentInstanceID)
                    .append(", deliveryItems={");
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                sb.append(entry.getKey())
                        .append(": ")
                        .append(entry.getValue())
                        .append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("}, from origin- ")
                    .append(origin)
                    .append(", to destination- ")
                    .append(destination)
                    .append(", truckTypeRequired- ")
                    .append(truckTypeRequired)
                    .append('}');
            return sb.toString();
    }

    public DeliveryStop(int shipmentInstanceID, Map<String, Integer> items, Site origin, Site destination,
                        TruckType truckTypeRequired) {
        this.shipmentInstanceID = shipmentInstanceID;
        this.items = items;
        this.destination = destination;
        this.truckTypeRequired = truckTypeRequired;
        this.origin=origin;
        this.status = DeliveryStatus.NOT_STARTED;
    }

    public DeliveryStop(boolean TestEnvironment, Site origin, Site destination) throws Exception {
        if (!TestEnvironment) {
            throw new Exception("This constructor is only for testing purposes");
        }
        this.origin = origin;
        this.destination = destination;
        this.shipmentInstanceID = -1;
    }

    public DeliveryStop(boolean TestEnvironment, int shipmentInstanceID) throws Exception {
        if (!TestEnvironment) {
            throw new Exception("This constructor is only for testing purposes");
        }
        this.shipmentInstanceID = shipmentInstanceID;
        this.origin = null;
        this.destination = null;
    }

    // Getters

    public TruckType getTruckTypeRequired() {
        return truckTypeRequired;
    }

    public Map<String, Integer> getItems(){
        return items;
    }

    public Site getOrigin(){
        return origin;
    }

    public Site getDestination(){
        return destination;
    }

    public int getShipmentInstanceID() {
        return shipmentInstanceID;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    // Setters
    public void setTruckTypeRequired(TruckType truckTypeRequired) {
        this.truckTypeRequired = truckTypeRequired;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public void updateArrivalTime(Timestamp dispatchTime) {
        int distanceKM = origin.computeDistance(destination) + BASE_DISTANCE;
        long travelDurationHours = distanceKM / AVERAGE_TRUCK_SPEED_KM_AN_H;
        estimatedArrivalTime = Timestamp.from(dispatchTime.toInstant().plus(travelDurationHours, ChronoUnit.HOURS));
    }

    public Timestamp getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }
}