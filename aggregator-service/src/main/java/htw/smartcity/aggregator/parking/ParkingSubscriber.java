package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.base.MQTTSubscriber;
import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Parking subscriber.
 */
@Component
public class ParkingSubscriber extends MQTTSubscriber {
    private final String subTopic =  "/parking/#";
    private Map<Sensor, Parking> sensorParkingMap = new HashMap<>();
    private Map<String, ParkingGroup> parkingGroupNameParkingGroupMap = new HashMap<>();
    private Map<Sensor, ParkingGroup> sensorParkingGroupMap = new HashMap<>();
    private Map<Long, ParkingGroupCounter> parkingGroupIdParkingGroupCounterMap = new HashMap<>();

    /**
     * The Parking repository.
     */
    @Autowired
    ParkingRepository parkingRepository;

    /**
     * The Parking group repository.
     */
    @Autowired
    ParkingGroupRepository parkingGroupRepository;

    /**
     * The Parking group counter repository.
     */
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
        try{
            parkingGroup = parkingGroupRepository.save(parkingGroup);
            parkingGroupNameParkingGroupMap.put(parkingGroup.getName(), parkingGroup);

        }catch (Exception e) {
            e.printStackTrace();
            exceptionManager.MQTTParkingGroupPersistenceFailed(parkingGroup.getName());
        }
        return parkingGroup;
    }

    @Override
    protected void persistMsg(LocalDateTime time, Sensor sensor, String msg) {
        try {
            Parking parking;
            if (!sensorParkingMap.containsKey(sensor))
                parking = parkingRepository.findFirstBySensorOrderByTimeDesc(sensor);
            else
                parking = sensorParkingMap.get(sensor);

            boolean newSensor = parking == null;
            if(newSensor || !parking.getValue().equals(msg)){
                Boolean parked = Boolean.getBoolean(msg);
                parking = new Parking(time, sensor, parked);
                parkingRepository.save(parking);
                sensorParkingMap.put(sensor, parking);
                persistParkingGroupCounter(time, sensorParkingGroupMap.get(sensor), parked, newSensor);
            }
        }catch (Exception e) {
            e.printStackTrace();
            exceptionManager.MQTTParkingPersistenceFailed(sensor.getName(), msg);
        }
    }

    private void persistParkingGroupCounter(LocalDateTime time, ParkingGroup parkingGroup, Boolean free, boolean newSensor) {
        try {
            ParkingGroupCounter parkingGroupCounter;
            if (!parkingGroupIdParkingGroupCounterMap.containsKey(parkingGroup.getId()))
                parkingGroupCounter = parkingGroupCounterRepository.findFirstByParkingGroupOrderByTimeDesc(parkingGroup);
            else
                parkingGroupCounter = parkingGroupIdParkingGroupCounterMap.get(parkingGroup.getId());

            boolean newGroup = parkingGroupCounter == null;
            if(newGroup)
                parkingGroupCounter = new ParkingGroupCounter(time, parkingGroup.getSensors().size(), 0, parkingGroup);


            if(newSensor && !newGroup) {
                if (free)
                    parkingGroupCounter.incrementFree();
                else
                    parkingGroupCounter.incrementUsed();
            }else {
                if (free)
                    parkingGroupCounter.spotFree();
                else
                    parkingGroupCounter.spotUsed();
            }

            parkingGroupCounter.setId(null);
            parkingGroupCounter = parkingGroupCounterRepository.save(parkingGroupCounter);
            parkingGroupIdParkingGroupCounterMap.put(parkingGroup.getId(), parkingGroupCounter);
        }catch (Exception e) {
            e.printStackTrace();
            exceptionManager.MQTTParkingGroupCounterPersistenceFailed(parkingGroup.getName(), parkingGroup.getInformation());
        }
    }


    @Override
    protected String getClientId() {
        return Utils.getMacAddress() + "-par";
    }
}
