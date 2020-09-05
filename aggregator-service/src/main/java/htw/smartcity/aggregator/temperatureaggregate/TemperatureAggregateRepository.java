package htw.smartcity.aggregator.temperatureaggregate;

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

    TemperatureAverageDaily findTemperatureAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    TemperatureAverageWeekly findTemperatureAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    TemperatureAverageMonthly findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    TemperatureMaximumDaily findTemperatureMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    TemperatureMaximumWeekly findTemperatureMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                LocalDateTime date, LocalDateTime date2);

    TemperatureMaximumMonthly findTemperatureMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                 LocalDateTime date, LocalDateTime date2);

    TemperatureMinimumDaily findTemperatureMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    TemperatureMinimumWeekly findTemperatureMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                                       LocalDateTime date, LocalDateTime date2);

    TemperatureMinimumMonthly findTemperatureMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                                         LocalDateTime date, LocalDateTime date2);
}
