package htw.smartcity.aggregator.parking;

/**
 * The type Parking not found exception.
 */
public class ParkingNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Parking not found exception.
     *
     * @param id the id
     */
    public ParkingNotFoundException(Long id) {
        super("Could not find parking spot " + id);
    }
}
