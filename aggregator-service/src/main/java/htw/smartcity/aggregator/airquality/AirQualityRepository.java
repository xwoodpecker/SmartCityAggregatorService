package htw.smartcity.aggregator.airquality;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AirQualityRepository extends JpaRepository<AirQuality, Long> {

    Page findAirQualitiesBySensorId(Long sensorId, Pageable pageable);

    Page findAirQualitiesByTimeAfterAndTimeBefore(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
}