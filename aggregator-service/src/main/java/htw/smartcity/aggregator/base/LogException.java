package htw.smartcity.aggregator.base;

public enum LogException {
    MQTT_CONFIGURATION_FAILED(0, ""),
    MQTT_SUBSCRIPTION_FAILED(1, ""),
    MQTT_SENSOR_PERSISTENCE_FAILED(2, ""),
    MQTT_AIR_QUALITY_PERSISTENCE_FAILED(3, "");

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