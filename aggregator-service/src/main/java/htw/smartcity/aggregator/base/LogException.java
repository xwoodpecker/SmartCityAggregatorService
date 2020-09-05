package htw.smartcity.aggregator.base;

/**
 * The enum Log exception.
 */
public enum LogException {
    /**
     * The Mqtt configuration failed.
     */
    MQTT_CONFIGURATION_FAILED(0, "Could not configure MQTT Connection"),
    /**
     * The Mqtt subscription failed.
     */
    MQTT_SUBSCRIPTION_FAILED(1, "MQTT Subscription failed"),
    /**
     * The Mqtt sensor persistence failed.
     */
    MQTT_SENSOR_PERSISTENCE_FAILED(2, "Sensor could not be saved in the database"),
    /**
     * The Mqtt air quality persistence failed.
     */
    MQTT_AIR_QUALITY_PERSISTENCE_FAILED(3, "Air Quality measurement could not be saved in the database"),
    /**
     * The Mqtt temperature persistence failed.
     */
    MQTT_TEMPERATURE_PERSISTENCE_FAILED(4, "Temperature measurement could not be saved in the database"),
    /**
     * The Mqtt parking persistence failed.
     */
    MQTT_PARKING_PERSISTENCE_FAILED(4, "Parking measurement could not be saved in the database"),
    /**
     * The Mqtt parking group persistence failed.
     */
    MQTT_PARKING_GROUP_PERSISTENCE_FAILED(4, "Parking Group could not be saved in the database"),
    /**
     * The Mqtt parking group counter persistence failed.
     */
    MQTT_PARKING_GROUP_COUNTER_PERSISTENCE_FAILED(4, "Parking group counter could not be saved in the database"),

    /**
     * The Daily aggregation failed.
     */
    DAILY_AGGREGATION_FAILED(5, "Failed computing daily aggregates"),

    /**
     * The Weekly aggregation failed.
     */
    WEEKLY_AGGREGATION_FAILED(6, "Failed computing weekly aggregates"),

    /**
     * The Monthly aggregation failed.
     */
    MONTHLY_AGGREGATION_FAILED(7, "Failed computing monthly aggregates");


    private final int code;
    private final String description;

    LogException(int code, String description) {
        this.code = code;
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

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + " - " + description;
    }
}