package htw.smartcity.aggregator.airquality;

import htw.smartcity.aggregator.base.MQTTSubscriber;
import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AirQualitySubscriber extends MQTTSubscriber {

    private final String subTopic =  "/airQuality/#";

    @Autowired
    AirQualityRepository airQualityRepository;

    @Override
    protected String getSubTopic() {
        return subTopic;
    }

    @Override
    protected Sensor.SensorType getSensorType() {
        return Sensor.SensorType.AIR_QUALITY;
    }

    @Override
    protected void persistMsg(LocalDateTime time, Sensor sensor, String msg) {
        try {
            Integer value = Integer.valueOf(msg);
            AirQuality airQuality = new AirQuality(time, sensor, value);
            airQualityRepository.save(airQuality);
        }catch (Exception e) {
            e.printStackTrace();
            exceptionManager.MQTTAirQualityPersistenceFailed(sensor.getName(), msg);
        }
    }

    @Override
    protected String getClientId() {
        return Utils.getMacAddress() + "-air";
    }
}
