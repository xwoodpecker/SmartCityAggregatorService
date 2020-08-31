package htw.smartcity.aggregator.base;

public enum LogException {
    MQTT_CONFIGURATION_FAILED(0, "Could not configure MQTT Connection"),
    MQTT_SUBSCRIPTION_FAILED(1, "MQTT Subscription failed"),
    MQTT_SENSOR_PERSISTENCE_FAILED(2, "Sensor could not be saved in the database"),
    MQTT_AIR_QUALITY_PERSISTENCE_FAILED(3, "Air Quality measurement could not be saved in the database"),
    MQTT_TEMPERATURE_PERSISTENCE_FAILED(4, "Temperature measurement could not be saved in the database"),
    MQTT_PARKING_PERSISTENCE_FAILED(4, "Parking measurement could not be saved in the database"),
    MQTT_PARKING_GROUP_PERSISTENCE_FAILED(4, "Parking Group could not be saved in the database"),
    MQTT_PARKING_GROUP_COUNTER_PERSISTENCE_FAILED(4, "Parking group counter could not be saved in the database");

    private final int code;
    private final String description;

    LogException(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + " - " + description;
    }
}