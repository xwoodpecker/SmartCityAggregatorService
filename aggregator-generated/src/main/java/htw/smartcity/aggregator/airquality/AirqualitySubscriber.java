package htw.smartcity.aggregator.airquality;

import htw.smartcity.aggregator.base.MQTTSubscriber;
import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorRepository;
import htw.smartcity.aggregator.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class AirqualitySubscriber extends MQTTSubscriber {

    private final String subTopic =  "/airquality/#";

    @Autowired
    AirqualityRepository airqualityRepository;

    @Override
    protected String getSubTopic() {
        return subTopic;
    }

    @Override
    protected Sensor.SensorType getSensorType() {
        return Sensor.SensorType.AIRQUALITY;
    }

    @Override
    protected void persistMsg(Date time, Sensor sensor, String msg) {
        try {
            Airquality airquality = new Airquality(time, sensor, msg);
            airqualityRepository.save(airquality);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getClientId() {
        return Utils.getMacAddress() + "-air";
    }
}