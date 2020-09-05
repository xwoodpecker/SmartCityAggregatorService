package htw.smartcity.aggregator.airqualityaggregator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Air quality aggregate repository.
 */
@Repository
public interface AirQualityAggregateRepository extends JpaRepository<AirQualityAggregate, Long>
{
    AirQualityAggregate save(AirQualityAggregate temperatureAggregate);

    /**
     * Find air quality maximums daily by sensor id and time between list.
     *
     * @param id        the id
     * @param startTime the start time
     * @param endTime   the end time
     * @return the list
     */
    List<AirQualityMaximumDaily> findAirQualityMaximumsDailyBySensorIdAndTimeBetween(Long id, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Find air quality average daily by sensor id and time less than equal and time greater than equal air quality average daily.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the air quality average daily
     */
    AirQualityAverageDaily findAirQualityAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    /**
     * Find air quality average weekly by sensor id and begin date less than equal and end date greater than equal air quality average weekly.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the air quality average weekly
     */
    AirQualityAverageWeekly findAirQualityAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    /**
     * Find air quality average monthly by sensor id and begin date less than equal and end date greater than equal air quality average monthly.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the air quality average monthly
     */
    AirQualityAverageMonthly findAirQualityAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    /**
     * Find air quality maximum daily by sensor id and time less than equal and time greater than equal air quality maximum daily.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the air quality maximum daily
     */
    AirQualityMaximumDaily findAirQualityMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    /**
     * Find air quality maximum weekly by sensor id and begin date less than equal and end date greater than equal air quality maximum weekly.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the air quality maximum weekly
     */
    AirQualityMaximumWeekly findAirQualityMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                                      LocalDateTime date, LocalDateTime date2);

    /**
     * Find air quality maximum monthly by sensor id and begin date less than equal and end date greater than equal air quality maximum monthly.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the air quality maximum monthly
     */
    AirQualityMaximumMonthly findAirQualityMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                                        LocalDateTime date, LocalDateTime date2);

    /**
     * Find air quality minimum daily by sensor id and time less than equal and time greater than equal air quality minimum daily.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the air quality minimum daily
     */
    AirQualityMinimumDaily findAirQualityMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    /**
     * Find air quality minimum weekly by sensor id and begin date less than equal and end date greater than equal air quality minimum weekly.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the air quality minimum weekly
     */
    AirQualityMinimumWeekly findAirQualityMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                                      LocalDateTime date, LocalDateTime date2);

    /**
     * Find air quality minimum monthly by sensor id and begin date less than equal and end date greater than equal air quality minimum monthly.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the air quality minimum monthly
     */
    AirQualityMinimumMonthly findAirQualityMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                                        LocalDateTime date, LocalDateTime date2);
}
