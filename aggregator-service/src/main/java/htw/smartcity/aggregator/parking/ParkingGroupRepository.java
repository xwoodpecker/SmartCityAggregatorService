package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Parking group repository.
 */
@Repository
public interface ParkingGroupRepository extends JpaRepository<ParkingGroup, Long> {

    /**
     * Find by name list.
     *
     * @param name the name
     * @return the list
     */
    List<ParkingGroup> findByName(String name);

    /**
     * Find parking sensors by group id page.
     *
     * @param groupId  the group id
     * @param pageable the pageable
     * @return the page
     */
    @Query(value = "select s from ParkingGroup p join Sensor s ON s member of p.sensors")
    Page<Sensor> findParkingSensorsByGroupId(Long groupId, Pageable pageable);
}