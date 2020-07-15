package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingGroupCounterRepository extends JpaRepository<ParkingGroupCounter, Long> {

    ParkingGroupCounter findFirstByParkingGroupOrderByTimeDesc(ParkingGroup parkingGroup);
    ParkingGroupCounter findFirstByParkingGroupIdOrderByTimeDesc(Long groupId);
    Page<ParkingGroupCounter> findAllByParkingGroupId(Pageable pageable, long groupId);
}
