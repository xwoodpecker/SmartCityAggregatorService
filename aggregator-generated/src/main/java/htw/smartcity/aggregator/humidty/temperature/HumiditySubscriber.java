package htw.smartcity.aggregator.humidty.temperature;

import htw.smartcity.aggregator.base.MQTTSubscriber;
import htw.smartcity.aggregator.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HumiditySubscriber extends MQTTSubscriber {

    private final String subTopic =  "/humidity/#";

    @Autowired
    HumidityRepository humidityRepository;

    @Override
    protected String getSubTopic() {
        return subTopic;
    }

    @Override
    protected void persistMsg(String time, String msg) {
        try {
            Humidity humidity = new Humidity(time, msg);
            humidityRepository.save(humidity);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getClientId() {
        return Utils.getMacAddress() + "-hum";
    }
}
