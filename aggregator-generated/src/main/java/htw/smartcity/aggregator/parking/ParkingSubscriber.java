package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.base.MQTTSubscriber;
import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParkingSubscriber extends MQTTSubscriber {
    private final String subTopic =  "/parking/#";
    private Map<Sensor, Parking> sensorParkingMap = new HashMap<>();
    private Map<String, ParkingGroup> parkingGroupNameParkingGroupMap = new HashMap<>();
    private Map<Sensor, ParkingGroup> sensorParkingGroupMap = new HashMap<>();
    private Map<Long, ParkingGroupCounter> parkingGroupIdParkingGroupCounterMap = new HashMap<>();

    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    ParkingGroupRepository parkingGroupRepository;

    @Autowired
    ParkingGroupCounterRepository parkingGroupCounterRepository;

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


        ParkingGroup parkingGroup = getParkingGroup(groupName);

        if(newSensor){
            if(parkingGroup == null )
                parkingGroup = new ParkingGroup(groupName, null);

            parkingGroup.addToSensors(sensor);
            persistParkingGroup(parkingGroup);
        }

        if(!sensorParkingGroupMap.containsKey(sensor))
            sensorParkingGroupMap.put(sensor, parkingGroup);

        return sensor;
    }

    private ParkingGroup getParkingGroup(String groupName) {
        ParkingGroup parkingGroup = null;
        if(parkingGroupNameParkingGroupMap.containsKey(groupName)) {
            parkingGroup = parkingGroupNameParkingGroupMap.get(groupName);
        }else{
            List<ParkingGroup> parkingGroups = parkingGroupRepository.findByName(groupName);
            if(!parkingGroups.isEmpty()) {
                parkingGroup = parkingGroups.get(0);
                parkingGroupNameParkingGroupMap.put(groupName, parkingGroup);
            }
        }
        return parkingGroup;
    }

    private ParkingGroup persistParkingGroup(ParkingGroup parkingGroup) {
        parkingGroup = parkingGroupRepository.save(parkingGroup);
        parkingGroupNameParkingGroupMap.put(parkingGroup.getName(), parkingGroup);
        return parkingGroup;
    }

    @Override
    protected void persistMsg(Date time, Sensor sensor, String msg) {
        try {
            Parking parking;
            if (!sensorParkingMap.containsKey(sensor))
                parking = parkingRepository.findFirstBySensorOrderByTimeDesc(sensor);
            else
                parking = sensorParkingMap.get(sensor);

            if(parking == null || !parking.getValue().equals(msg)){ //TODO:TEST
                parking = new Parking(time, sensor, msg);
                parkingRepository.save(parking);
                sensorParkingMap.put(sensor, parking);
                persistParkingGroupCounter(time, sensorParkingGroupMap.get(sensor), msg);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void persistParkingGroupCounter(Date time, ParkingGroup parkingGroup, String msg) {
        try {
            ParkingGroupCounter parkingGroupCounter;
            if (!parkingGroupIdParkingGroupCounterMap.containsKey(parkingGroup.getId()))
                parkingGroupCounter = parkingGroupCounterRepository.findFirstByParkingGroupOrderByTimeDesc(parkingGroup);
            else
                parkingGroupCounter = parkingGroupIdParkingGroupCounterMap.get(parkingGroup.getId());

            if(parkingGroupCounter == null)
                parkingGroupCounter = new ParkingGroupCounter(time, parkingGroup.getSensors().size(), 0, parkingGroup);

            boolean free = Boolean.parseBoolean(msg);
            if(free)
                parkingGroupCounter.spotFree();
            else
                parkingGroupCounter.spotUsed();

            parkingGroupCounter.setId(null);
            parkingGroupCounter = parkingGroupCounterRepository.save(parkingGroupCounter);
            parkingGroupIdParkingGroupCounterMap.put(parkingGroup.getId(), parkingGroupCounter);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected String getClientId() {
        return Utils.getMacAddress() + "-par";
    }
}
