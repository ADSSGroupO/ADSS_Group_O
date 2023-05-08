package Deliveries.BusinessLayer;

public class Site {
    public static final int KM_BETWEEN_ZONES = 10;
    private final String name;
    private final String address;
    private final String contactName;
    private final String contactPhone;
    private final int deliveryZone;

    public Site(String name, String address, String contactName, String contactPhone, int deliveryZone) {
        this.name = name;
        this.address = address;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.deliveryZone = deliveryZone;
    }

    @Override
    public String toString() {
        return "Site{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public int getDeliveryZone() {
        return deliveryZone;
    }

    public int computeDistance(Site otherSite) {
        return KM_BETWEEN_ZONES*Math.abs(otherSite.getDeliveryZone() - this.getDeliveryZone());
    }
}