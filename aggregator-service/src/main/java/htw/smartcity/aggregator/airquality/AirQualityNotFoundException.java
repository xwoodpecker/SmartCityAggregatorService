package htw.smartcity.aggregator.airquality;

/**
 * The type Air quality not found exception.
 */
public class AirQualityNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Air quality not found exception.
     *
     * @param id the id
     */
    public AirQualityNotFoundException(Long id) {
        super("Could not find air quality " + id);
    }
}
