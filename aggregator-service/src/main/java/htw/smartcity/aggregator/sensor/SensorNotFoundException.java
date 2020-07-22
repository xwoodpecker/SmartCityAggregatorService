package htw.smartcity.aggregator.sensor;

public class SensorNotFoundException extends RuntimeException{
    public SensorNotFoundException(Long id) {
        super("Could not find sensor " + id);
    }
}
