package htw.smartcity.aggregator.parking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Parking group counter repository.
 */
public interface ParkingGroupCounterRepository extends JpaRepository<ParkingGroupCounter, Long> {

    /**
     * Find first by parking group order by time desc parking group counter.
     *
     * @param parkingGroup the parking group
     * @return the parking group counter
     */
    ParkingGroupCounter findFirstByParkingGroupOrderByTimeDesc(ParkingGroup parkingGroup);

    /**
     * Find first by parking group id order by time desc parking group counter.
     *
     * @param groupId the group id
     * @return the parking group counter
     */
    ParkingGroupCounter findFirstByParkingGroupIdOrderByTimeDesc(Long groupId);

    /**
     * Find all by parking group id page.
     *
     * @param pageable the pageable
     * @param groupId  the group id
     * @return the page
     */
    Page<ParkingGroupCounter> findAllByParkingGroupId(Pageable pageable, long groupId);
}
