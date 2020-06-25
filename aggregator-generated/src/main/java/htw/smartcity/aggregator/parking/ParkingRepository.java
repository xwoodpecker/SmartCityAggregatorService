package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Parking findFirstBySensorOrderByTimeDesc(Sensor sensor);
    List<Parking> findFirstBySensorInOrderByTimeDesc(List<Sensor> sensor);

}