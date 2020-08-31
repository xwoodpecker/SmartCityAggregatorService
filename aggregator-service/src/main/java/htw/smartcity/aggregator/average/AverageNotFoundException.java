package htw.smartcity.aggregator.average;

/**
 * The type Average not found exception.
 */
public class AverageNotFoundException extends RuntimeException
{
    /**
     * Instantiates a new Average not found exception.
     *
     * @param id the id
     */
    public AverageNotFoundException(Long id) {
        super("Could not find average " + id);
    }
}
