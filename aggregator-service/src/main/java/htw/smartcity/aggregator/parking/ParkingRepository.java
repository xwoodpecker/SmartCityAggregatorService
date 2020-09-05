package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.temperature.Temperature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

/**
 * The interface Parking repository.
 */
@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    /**
     * Find first by sensor order by time desc parking.
     *
     * @param sensor the sensor
     * @return the parking
     */
    Parking findFirstBySensorOrderByTimeDesc(Sensor sensor);

    /**
     * Find first by sensor id order by time desc parking.
     *
     * @param sensorId the sensor id
     * @return the parking
     */
    Parking findFirstBySensorIdOrderByTimeDesc(Long sensorId);

    /**
     * Find parkings by sensor id page.
     *
     * @param sensorId the sensor id
     * @param pageable the pageable
     * @return the page
     */
    Page<Parking> findParkingsBySensorId(Long sensorId, Pageable pageable);

    /**
     * Find parkings by time after and time before and sensor id page.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @param sensorId  the sensor id
     * @param pageable  the pageable
     * @return the page
     */
    Page<Parking> findParkingsByTimeAfterAndTimeBeforeAndSensorId(OffsetDateTime startTime, OffsetDateTime endTime, Long sensorId, Pageable pageable);


}