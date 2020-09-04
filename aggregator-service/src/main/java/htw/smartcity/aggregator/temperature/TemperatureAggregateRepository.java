package htw.smartcity.aggregator.temperature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Temperature average repository.
 */
@Repository
public interface TemperatureAggregateRepository extends JpaRepository<TemperatureAggregate, Long>
{
    TemperatureAggregate save(TemperatureAggregate temperatureAggregate);

    List<TemperatureMaximumDaily> findTemperatureMaximumsDailyBySensorIdAndTimeBetween(Long id, LocalDateTime startTime, LocalDateTime endTime);

    TemperatureAverageDaily findTemperatureAverageDailyBySensorIdAndTime(Long SensorId, LocalDateTime time);

    TemperatureAverageWeekly findTemperatureAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    TemperatureAverageMonthly findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);
}
