package htw.smartcity.aggregator.temperature;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Long> {
    Page<Temperature> findTemperaturesByTimeBeforeAndTimeAfter(Date endTime, Date startTime, Pageable pageable);
    Page<Temperature> findTemperaturesByTimeBetween(Date endTime, Date startTime, Pageable pageable);
    Page<Temperature> findTemperaturesBySensorId(Long id, Pageable pageable);
}