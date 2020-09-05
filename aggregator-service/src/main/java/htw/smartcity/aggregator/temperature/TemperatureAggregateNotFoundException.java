package htw.smartcity.aggregator.temperature;

/**
 * The type Temperature average not found exception.
 */
public class TemperatureAggregateNotFoundException extends RuntimeException
{
    /**
     * Instantiates a new Temperature average not found exception.
     *
     * @param id the id
     */
    public TemperatureAggregateNotFoundException(Long id) {
        super("Could not find temperature aggregation " + id);
    }
}
