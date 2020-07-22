package htw.smartcity.aggregator.average;

public class AverageNotFoundException extends RuntimeException
{
    public AverageNotFoundException(Long id) {
        super("Could not find average " + id);
    }
}
