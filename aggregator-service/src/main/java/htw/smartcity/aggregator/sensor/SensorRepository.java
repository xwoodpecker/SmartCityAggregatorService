package htw.smartcity.aggregator.sensor;

import htw.smartcity.aggregator.parking.Parking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Sensor repository.
 */
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    /**
     * Find by name and sensor type page.
     *
     * @param name       the name
     * @param sensorType the sensor type
     * @param pageable   the pageable
     * @return the page
     */
    Page<Sensor> findByNameAndSensorType(String name, SensorType sensorType, Pageable pageable);

    /**
     * Find by sensor type page.
     *
     * @param sensorType the sensor type
     * @param pageable   the pageable
     * @return the page
     */
    Page<Sensor> findBySensorType(SensorType sensorType, Pageable pageable);


    /**
     * Find by sensor type list.
     *
     * @param sensorType the sensor type
     * @return the list
     */
    List<Sensor> findBySensorType(SensorType sensorType);
}