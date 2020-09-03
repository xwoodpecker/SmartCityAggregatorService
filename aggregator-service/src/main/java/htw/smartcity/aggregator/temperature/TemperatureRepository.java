package htw.smartcity.aggregator.temperature;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * The interface Temperature repository.
 */
@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Long> {
    /**
     * Find temperatures by time after and time before page.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @param pageable  the pageable
     * @return the page
     */
    Page<Temperature> findTemperaturesByTimeAfterAndTimeBefore(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    /**
     * Find temperatures by sensor id page.
     *
     * @param id       the id
     * @param pageable the pageable
     * @return the page
     */
    Page<Temperature> findTemperaturesBySensorId(Long id, Pageable pageable);

    /**
     * Find temperatures by sensor id and time between page.
     *
     * @param id        the id
     * @param startTime the start time
     * @param endTime   the end time
     * @param pageable  the pageable
     * @return the page
     */
    Page<Temperature> findTemperaturesBySensorIdAndTimeBetween(Long id, LocalDateTime startTime, LocalDateTime endTime, Pageable
            pageable);
    Page<Temperature> findTemperaturesBySensorIdAndTimeBetween(Long id, Date startTime, Date endTime, Pageable
            pageable);
}