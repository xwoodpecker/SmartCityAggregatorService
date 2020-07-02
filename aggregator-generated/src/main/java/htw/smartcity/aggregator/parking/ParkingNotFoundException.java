package htw.smartcity.aggregator.parking;

public class ParkingNotFoundException extends RuntimeException {
    public ParkingNotFoundException(Long id) {
        super("Could not find parking spot " + id);
    }
}
