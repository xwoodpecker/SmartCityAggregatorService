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

    /**
     * Find temperature maximums daily by sensor id and time between list.
     *
     * @param id        the id
     * @param startTime the start time
     * @param endTime   the end time
     * @return the list
     */
    List<TemperatureMaximumDaily> findTemperatureMaximumsDailyBySensorIdAndTimeBetween(Long id, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Find temperature average daily by sensor id and time less than equal and time greater than equal temperature average daily.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the temperature average daily
     */
    TemperatureAverageDaily findTemperatureAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    /**
     * Find temperature average weekly by sensor id and begin date less than equal and end date greater than equal temperature average weekly.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the temperature average weekly
     */
    TemperatureAverageWeekly findTemperatureAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    /**
     * Find temperature average monthly by sensor id and begin date less than equal and end date greater than equal temperature average monthly.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the temperature average monthly
     */
    TemperatureAverageMonthly findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    /**
     * Find temperature maximum daily by sensor id and time less than equal and time greater than equal temperature maximum daily.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the temperature maximum daily
     */
    TemperatureMaximumDaily findTemperatureMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    /**
     * Find temperature maximum weekly by sensor id and begin date less than equal and end date greater than equal temperature maximum weekly.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the temperature maximum weekly
     */
    TemperatureMaximumWeekly findTemperatureMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                LocalDateTime date, LocalDateTime date2);

    /**
     * Find temperature maximum monthly by sensor id and begin date less than equal and end date greater than equal temperature maximum monthly.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the temperature maximum monthly
     */
    TemperatureMaximumMonthly findTemperatureMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                 LocalDateTime date, LocalDateTime date2);

    /**
     * Find temperature minimum daily by sensor id and time less than equal and time greater than equal temperature minimum daily.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the temperature minimum daily
     */
    TemperatureMinimumDaily findTemperatureMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(Long SensorId, LocalDateTime date, LocalDateTime date2);

    /**
     * Find temperature minimum weekly by sensor id and begin date less than equal and end date greater than equal temperature minimum weekly.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the temperature minimum weekly
     */
    TemperatureMinimumWeekly findTemperatureMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                                       LocalDateTime date, LocalDateTime date2);

    /**
     * Find temperature minimum monthly by sensor id and begin date less than equal and end date greater than equal temperature minimum monthly.
     *
     * @param SensorId the sensor id
     * @param date     the date
     * @param date2    the date 2
     * @return the temperature minimum monthly
     */
    TemperatureMinimumMonthly findTemperatureMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(Long SensorId,
                                                                                                                         LocalDateTime date, LocalDateTime date2);
}
