package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.base.MQTTSubscriber;
import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class TemperatureSubscriber extends MQTTSubscriber {

    private final String subTopic =  "/temperature/#";

    @Autowired
    TemperatureRepository temperatureRepository;

    @Override
    protected String getSubTopic() {
        return subTopic;
    }

    @Override
    protected Sensor.SensorType getSensorType() {
        return Sensor.SensorType.TEMPERATURE;
    }

    @Override
    protected void persistMsg(Date time, Sensor sensor, String msg) {
        try {
            Temperature temperature = new Temperature(time, sensor, msg);
            temperatureRepository.save(temperature);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getClientId() {
        return Utils.getMacAddress() + "-tem";
    }
}
