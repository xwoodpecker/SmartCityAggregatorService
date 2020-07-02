package htw.smartcity.aggregator.parking;

public class ParkingGroupNotFoundException extends RuntimeException {
    public ParkingGroupNotFoundException(Long id) {
        super("Could not find temperature " + id);
    }
}
