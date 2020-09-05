package htw.smartcity.aggregator.airquality;

import htw.smartcity.aggregator.temperature.Temperature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Air quality repository.
 */
@Repository
public interface AirQualityRepository extends JpaRepository<AirQuality, Long> {

    /**
     * Find air qualities by sensor id page.
     *
     * @param sensorId the sensor id
     * @param pageable the pageable
     * @return the page
     */
    Page findAirQualitiesBySensorId(Long sensorId, Pageable pageable);

    /**
     * Find air qualities by time after and time before page.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @param pageable  the pageable
     * @return the page
     */
    Page findAirQualitiesByTimeAfterAndTimeBefore(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    List<AirQuality> findAirQualitiesBySensorIdAndTimeBetween(Long id, LocalDateTime startTime, LocalDateTime endTime);
}