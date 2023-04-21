package Deliveries.BusinessLayer;

import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity @IdClass(Driver.class)
        @Table(name = "DriverLicenses")
public class License {

    @Id
    @Column(name = "driver_id")
    private String driverId;
    @Column(name = "weight_allowed_tons")
    private final int weightAllowedTons;
    @ElementCollection
    private final Set<TruckType> truckTypesAllowed;

    public License() {
        this.weightAllowedTons = 15;
        this.truckTypesAllowed = new HashSet<>();
        this.truckTypesAllowed.add(TruckType.Regular);
        this.truckTypesAllowed.add(TruckType.Refrigerated);
    }

    public License(int weightAllowed, Set<TruckType> truckTypesAllowed) {
        this.weightAllowedTons = weightAllowed;
        this.truckTypesAllowed = truckTypesAllowed;
    }

    public int getWeightAllowedTons() {
        return weightAllowedTons;
    }


    public Set<TruckType> getTruckTypesAllowed() {
        return truckTypesAllowed;
    }

    public boolean isLicensed(Truck truck) {
        int weight = truck.getMaxWeightTons();
        TruckType type = truck.getType();
        return weight <= weightAllowedTons && truckTypesAllowed.contains(type);
    }
}
