package htw.smartcity.aggregator.sensor;

import htw.smartcity.aggregator.parking.Parking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Integer> {
    List<Sensor> findByName(String name);

    List<Sensor> findByNameAndSensorType(String name, Sensor.SensorType sensorType);
}