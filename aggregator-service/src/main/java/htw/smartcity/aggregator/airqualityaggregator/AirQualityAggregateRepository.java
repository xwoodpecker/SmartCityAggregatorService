package htw.smartcity.aggregator.airqualityaggregator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AirQualityAggregateRepository extends JpaRepository<AirQualityAggregate, Long>
{
    AirQualityAggregate save(AirQualityAggregate temperatureAggregate);

    List<AirQualityMaximumDaily> findAirQualityMaximumsDailyBySensorIdAndTimeBetween(Long id, LocalDateTime startTime, LocalDateTime endTime);

    AirQualityAverageDaily findAirQualityAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    AirQualityAverageWeekly findAirQualityAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    AirQualityAverageMonthly findAirQualityAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    AirQualityMaximumDaily findAirQualityMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    AirQualityMaximumWeekly findAirQualityMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                                      LocalDateTime date, LocalDateTime date2);

    AirQualityMaximumMonthly findAirQualityMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                                        LocalDateTime date, LocalDateTime date2);

    AirQualityMinimumDaily findAirQualityMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    AirQualityMinimumWeekly findAirQualityMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                                      LocalDateTime date, LocalDateTime date2);

    AirQualityMinimumMonthly findAirQualityMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                                        LocalDateTime date, LocalDateTime date2);
}
