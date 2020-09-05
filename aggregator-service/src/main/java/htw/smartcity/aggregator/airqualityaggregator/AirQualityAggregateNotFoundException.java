package htw.smartcity.aggregator.airqualityaggregator;

/**
 * The type Air quality aggregate not found exception.
 */
public class AirQualityAggregateNotFoundException extends RuntimeException
{
    /**
     * Instantiates a new Temperature average not found exception.
     *
     * @param id the id
     */
    public AirQualityAggregateNotFoundException(Long id) {
        super("Could not find air quality aggregation " + id);
    }
}
