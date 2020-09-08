package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.base.MQTTSubscriber;
import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorType;
import htw.smartcity.aggregator.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * The type Temperature subscriber.
 */
@Component
public class TemperatureSubscriber extends MQTTSubscriber {

    private final String subTopic =  "/temperature/#";

    /**
     * The Temperature repository.
     */
    final
    TemperatureRepository temperatureRepository;

    public TemperatureSubscriber(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    @Override
    protected String getSubTopic() {
        return subTopic;
    }

    @Override
    protected SensorType getSensorType() {
        return SensorType.TEMPERATURE;
    }

    @Override
    protected void persistMsg(LocalDateTime time, Sensor sensor, String msg) {
        try {
            Double value = Double.valueOf(msg);
            Temperature temperature = new Temperature(time, sensor, value);
            temperatureRepository.save(temperature);
        }catch (Exception e) {
            e.printStackTrace();
            exceptionManager.MQTTTemperaturePersistenceFailed(sensor.getName(), msg);
        }
    }

    @Override
    protected String getClientId() {
        return Utils.getMacAddress() + "-tem";
    }
}
