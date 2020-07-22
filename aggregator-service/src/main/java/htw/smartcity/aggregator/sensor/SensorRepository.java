package htw.smartcity.aggregator.sensor;

import htw.smartcity.aggregator.parking.Parking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Page<Sensor> findByName(String name, Pageable pageable);

    Page<Sensor> findByNameAndSensorType(String name, Sensor.SensorType sensorType, Pageable pageable);
}