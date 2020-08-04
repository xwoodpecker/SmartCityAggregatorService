package htw.smartcity.aggregator.airquality;

public class AirQualityNotFoundException extends RuntimeException {
    public AirQualityNotFoundException(Long id) {
        super("Could not find air quality " + id);
    }
}
