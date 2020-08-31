package htw.smartcity.aggregator.temperature;

/**
 * The type Temperature average not found exception.
 */
public class TemperatureAverageNotFoundException extends RuntimeException
{
    /**
     * Instantiates a new Temperature average not found exception.
     *
     * @param id the id
     */
    public TemperatureAverageNotFoundException(Long id) {
        super("Could not find temperature " + id);
    }
}
