package Deliveries;
// extend compile time exception
public class DeliveryException extends Exception {
    public DeliveryException(String message) {
        super(message);
    }
}
