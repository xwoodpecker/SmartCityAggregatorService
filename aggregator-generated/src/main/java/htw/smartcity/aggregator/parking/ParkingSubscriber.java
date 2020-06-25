package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.base.MQTTSubscriber;
import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Component
public class ParkingSubscriber extends MQTTSubscriber {
    private final String subTopic =  "/parking/#";

    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    ParkingGroupRepository parkingGroupRepository;

    @Override
    protected String getSubTopic() {
        return subTopic;
    }

    @Override
    protected Sensor.SensorType getSensorType() {
        return Sensor.SensorType.PARKING;
    }

    @Override
    protected Sensor getOrPersistSensor(String sensorName) {
        boolean newSensor = false;
        Sensor sensor = getSensor(sensorName);
        if(sensor == null) {
            newSensor = true;
            sensor = persistSensor(sensorName);
        }

        String[] parts = sensorName.split("/", 2);

        String groupName = parts[0];

        List<ParkingGroup> parkingGroups = parkingGroupRepository.findByName(groupName);
        ParkingGroup parkingGroup;
        if(parkingGroups.isEmpty()) {
            parkingGroup = new ParkingGroup(groupName, null);
            parkingGroup = parkingGroupRepository.save(parkingGroup);
        }else {
            if(newSensor) {
                parkingGroup = parkingGroups.get(0);
                parkingGroup.addToSensors(sensor);
                parkingGroup = parkingGroupRepository.save(parkingGroup);
            }
        }

        return sensor;
    }

    @Override
    protected void persistMsg(Date time, Sensor sensor, String msg) {
        try {
            Parking parking = new Parking(time, sensor, msg);
            parkingRepository.save(parking);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getClientId() {
        return Utils.getMacAddress() + "-par";
    }
}
