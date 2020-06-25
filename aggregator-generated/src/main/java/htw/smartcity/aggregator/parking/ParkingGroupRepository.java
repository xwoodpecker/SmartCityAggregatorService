package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingGroupRepository extends JpaRepository<ParkingGroup, Long> {

    List<ParkingGroup> findByName(String name);
}