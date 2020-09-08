package htw.smartcity.aggregator.temperature;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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


    /**
     * Find temperatures by sensor id and time between list.
     *
     * @param id        the id
     * @param startTime the start time
     * @param endTime   the end time
     * @return the list
     */
    List<Temperature> findTemperaturesBySensorIdAndTimeBetween(Long id, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Find latest page.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Query(value = "SELECT t from Temperature t JOIN Sensor s group by t.sensor order by t.time desc")
    Page<Temperature> findLatest(Pageable pageable);

    Temperature findFirstBySensorIdOrderByTimeDesc(Long sensorId);
}