package htw.smartcity.aggregator.temperature;

/**
 * The type Temperature not found exception.
 */
public class TemperatureNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Temperature not found exception.
     *
     * @param id the id
     */
    public TemperatureNotFoundException(Long id) {
        super("Could not find temperature " + id);
    }
}
