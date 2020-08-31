package htw.smartcity.aggregator.sensor;

/**
 * The type Sensor not found exception.
 */
public class SensorNotFoundException extends RuntimeException{
    /**
     * Instantiates a new Sensor not found exception.
     *
     * @param id the id
     */
    public SensorNotFoundException(Long id) {
        super("Could not find sensor " + id);
    }
}
