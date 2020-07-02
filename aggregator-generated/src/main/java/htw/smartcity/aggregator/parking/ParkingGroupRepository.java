package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingGroupRepository extends JpaRepository<ParkingGroup, Long> {

    List<ParkingGroup> findByName(String name);

    @Query(value = "select s from ParkingGroup p join Sensor s ON s member of p.sensors")
    Page<Sensor> findParkingSensorsByGroupId(Long groupId, Pageable pageable);
}