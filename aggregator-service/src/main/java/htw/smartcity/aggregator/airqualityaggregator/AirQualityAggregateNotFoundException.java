package htw.smartcity.aggregator.airqualityaggregator;

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
