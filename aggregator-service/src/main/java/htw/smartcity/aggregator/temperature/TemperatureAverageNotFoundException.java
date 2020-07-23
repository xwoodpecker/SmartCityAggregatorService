package htw.smartcity.aggregator.temperature;

public class TemperatureAverageNotFoundException extends RuntimeException
{
    public TemperatureAverageNotFoundException(Long id) {
        super("Could not find temperature " + id);
    }
}
