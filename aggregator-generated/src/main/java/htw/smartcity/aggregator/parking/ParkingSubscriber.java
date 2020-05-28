package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.base.MQTTSubscriber;
import htw.smartcity.aggregator.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class ParkingSubscriber extends MQTTSubscriber {

    private final String subTopic =  "/parking/#";

    @Autowired
    ParkingRepository parkingRepository;

    @Override
    protected String getSubTopic() {
        return subTopic;
    }

    @Override
    protected void persistMsg(Date time, String sensorType, String msg) {
        try {
            Parking parking = new Parking(time, sensorType, msg);
            parkingRepository.save(parking);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getClientId() {
        return Utils.getMacAddress() + "-hum";
    }
}
