package Deliveries.BusinessLayer;


import Deliveries.BusinessLayer.Enums_and_Interfaces.Availability;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import jakarta.persistence.*;

@Entity
@Table(name = "Drivers")
public class Driver {

    @Column(name = "driver_name")
    private final String name;
    @Id
    @Column(name = "driver_id")
    private final String id;
    @Column(name = "phone")
    private final String phone;

    public Driver() {
        this.name = "";
        this.id = "";
        this.phone = "";
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    private License license;

    private Availability availability;

    public Driver(String name, String id, String phone) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.license = new License();
        availability = Availability.Available;
    }



    public String getName() {
        return name;
    }

    public License getLicense() {
        return license;
    }

    public Availability getAvailability() {
        return availability;
    }

    public String getId() {
        return id;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public void freeDriver() {
        availability = Availability.Available;
    }

    public boolean isLicensed(Truck truck) {
        return license.isLicensed(truck);
    }

}
