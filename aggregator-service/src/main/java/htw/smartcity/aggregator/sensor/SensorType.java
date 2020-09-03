package htw.smartcity.aggregator.sensor;

/**
 * The enum Sensor type.
 */
public enum SensorType {
    /**
     * The Air quality.
     */
    AIR_QUALITY("Air Quality"),
    /**
     * Parking sensor type.
     */
    PARKING("Parking"),
    /**
     * Temperature sensor type.
     */
    TEMPERATURE("Temperature");

    private final String description;

    SensorType(String description) {
        this.description = description;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}