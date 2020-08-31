package htw.smartcity.aggregator.parking;

/**
 * The type Parking group not found exception.
 */
public class ParkingGroupNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Parking group not found exception.
     *
     * @param id the id
     */
    public ParkingGroupNotFoundException(Long id) {
        super("Could not find temperature " + id);
    }
}
