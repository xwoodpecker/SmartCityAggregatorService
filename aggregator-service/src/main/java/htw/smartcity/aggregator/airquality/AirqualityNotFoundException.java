package htw.smartcity.aggregator.airquality;

public class AirqualityNotFoundException extends RuntimeException {
    public AirqualityNotFoundException(Long id) {
        super("Could not find air quality " + id);
    }
}
