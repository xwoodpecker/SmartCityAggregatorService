package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingGroupCounterRepository extends JpaRepository<ParkingGroupCounter, Long> {

    ParkingGroupCounter findFirstByParkingGroupOrderByTimeDesc(Sensor sensor);
}
