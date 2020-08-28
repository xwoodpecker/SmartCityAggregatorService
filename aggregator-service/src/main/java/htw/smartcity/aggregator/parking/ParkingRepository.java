package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.temperature.Temperature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Parking findFirstBySensorOrderByTimeDesc(Sensor sensor);
    Parking findFirstBySensorIdOrderByTimeDesc(Long sensorId);

    Page<Parking> findParkingsBySensorId(Long sensorId, Pageable pageable);
    Page<Parking> findParkingsByTimeAfterAndTimeBeforeAndSensorId(LocalDateTime startTime, LocalDateTime endTime, Long sensorId, Pageable pageable);


}