package Deliveries;

public class Truck {
    private String model;
    private String licensePlate;
    private TruckType type;
    private int maxWeightTons;
    private int netoWeightTons;

    public Truck(String model, String licensePlate, TruckType type, int maxWeightTons, int netoWeightTons) {
        this.model = model;
        this.licensePlate = licensePlate;
        this.type = type;
        this.maxWeightTons = maxWeightTons;
        this.netoWeightTons = netoWeightTons;
    }

    public String getModel() {
        return model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public TruckType getType() {
        return type;
    }

    public int getMaxWeightTons() {
        return maxWeightTons;
    }

    public int getNetoWeightTons() {
        return netoWeightTons;
    }


}