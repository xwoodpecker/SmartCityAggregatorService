package htw.smartcity.aggregator.temperature;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The interface Temperature average repository.
 */
@Repository
public interface TemperatureAverageRepository extends JpaRepository<TemperatureAggregate, Long>
{
    TemperatureAggregate save(TemperatureAggregate temperatureaverage);

    List<TemperatureMaximumDaily> findTemperatureMaximumsDailyBySensorIdAndTimeBetween(Long id, LocalDateTime startTime, LocalDateTime endTime);

    TemperatureAverageDaily findTemperatureAverageDailyBySensorIdAndTime(Long SensorId, LocalDateTime time);

    TemperatureAverageWeekly findTemperatureAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    TemperatureAverageMonthly findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);
}

