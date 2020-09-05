package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.temperatureaggregate.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith (SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase (connection = EmbeddedDatabaseConnection.H2)
public class TemperatureAggregateTests
{
    /**
     * Tests for TemperatureAggregateRepository
     */

    private static final long          SENSOR_1 = 8;
    private static final long          SENSOR_2 = 9;
    private static final LocalDateTime DAY_1   = LocalDateTime.of(2020, Month.AUGUST, 20, 15, 40, 0);
    private static final LocalDateTime DAY_2   = LocalDateTime.of(2020, Month.AUGUST, 21, 15, 40, 0);
    private static final LocalDateTime NO_DATA_DAY   = LocalDateTime.of(2020, Month.AUGUST, 23, 15, 40, 0);

    private static final LocalDateTime DAY_IN_WEEK_1   = LocalDateTime.of(2020, Month.AUGUST, 22, 15, 40, 0);
    private static final LocalDateTime DAY_IN_WEEK_2   = LocalDateTime.of(2020, Month.AUGUST, 30, 15, 40, 0);
    private static final LocalDateTime NO_DATA_WEEK   = LocalDateTime.of(2020, Month.AUGUST, 12, 15, 40, 0);

    private static final LocalDateTime DAY_IN_MONTH_1  = LocalDateTime.of(2020, Month.JULY, 15, 15, 40, 0);
    private static final LocalDateTime DAY_IN_MONTH_2   = LocalDateTime.of(2020, Month.AUGUST, 14, 15, 40, 0);
    private static final LocalDateTime NO_DATA_MONTH  = LocalDateTime.of(2020, Month.SEPTEMBER, 14, 15, 40, 0);

    @Autowired
    TemperatureAggregateRepository temperatureAggregateRepository;

    @Test
    public void testAverageFunctions()
    {
        /**
         * daily
         */
        TemperatureAverageDaily temperatureAverageDaily =
                temperatureAggregateRepository.findTemperatureAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, DAY_1.with(LocalTime.MAX), DAY_1.with(LocalTime.MIN));

        assertNotNull(temperatureAverageDaily);
        assertEquals(temperatureAverageDaily.getValue(), 20.25, 0);

        temperatureAverageDaily =
                temperatureAggregateRepository.findTemperatureAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, DAY_2.with(LocalTime.MAX), DAY_2.with(LocalTime.MIN));

        assertNotNull(temperatureAverageDaily);
        assertEquals(temperatureAverageDaily.getValue(), 5.0, 0);

        temperatureAverageDaily =
                temperatureAggregateRepository.findTemperatureAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_2, DAY_1.with(LocalTime.MAX), DAY_1.with(LocalTime.MIN));

        assertNotNull(temperatureAverageDaily);
        assertEquals(temperatureAverageDaily.getValue(), 32.01, 0);

        temperatureAverageDaily =
                temperatureAggregateRepository.findTemperatureAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_2, DAY_2.with(LocalTime.MAX), DAY_2.with(LocalTime.MIN));

        assertNotNull(temperatureAverageDaily);
        assertEquals(temperatureAverageDaily.getValue(), 10.11, 0);

        temperatureAverageDaily =
                temperatureAggregateRepository.findTemperatureAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, NO_DATA_DAY.with(LocalTime.MAX), NO_DATA_DAY.with(LocalTime.MIN));

        assertNull(temperatureAverageDaily);

        /**
         * weekly
         */
        TemperatureAverageWeekly temperatureAverageWeekly =
                temperatureAggregateRepository.findTemperatureAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_WEEK_1, DAY_IN_WEEK_1);

        assertNotNull(temperatureAverageWeekly);
        assertEquals(temperatureAverageWeekly.getValue(), 18.35, 0);

        temperatureAverageWeekly =
                temperatureAggregateRepository.findTemperatureAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_WEEK_1, DAY_IN_WEEK_1);

        assertNotNull(temperatureAverageWeekly);
        assertEquals(temperatureAverageWeekly.getValue(), 45.02, 0);

        temperatureAverageWeekly =
                temperatureAggregateRepository.findTemperatureAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_WEEK_2, DAY_IN_WEEK_2);

        assertNotNull(temperatureAverageWeekly);
        assertEquals(temperatureAverageWeekly.getValue(), -6.1, 0);

        temperatureAverageWeekly =
                temperatureAggregateRepository.findTemperatureAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_WEEK_2, DAY_IN_WEEK_2);

        assertNotNull(temperatureAverageWeekly);
        assertEquals(temperatureAverageWeekly.getValue(), 8.04, 0);

        temperatureAverageWeekly =
                temperatureAggregateRepository.findTemperatureAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, NO_DATA_WEEK, NO_DATA_WEEK);

        assertNull(temperatureAverageWeekly);

        /**
         * monthly
         */
        TemperatureAverageMonthly temperatureAverageMonthly =
                temperatureAggregateRepository.findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_MONTH_1, DAY_IN_MONTH_1);

        assertNotNull(temperatureAverageMonthly);
        assertEquals(temperatureAverageMonthly.getValue(), 65.35, 0);

        temperatureAverageMonthly =
                temperatureAggregateRepository.findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_MONTH_1, DAY_IN_MONTH_1);

        assertNotNull(temperatureAverageMonthly);
        assertEquals(temperatureAverageMonthly.getValue(), 12.02, 0);

        temperatureAverageMonthly =
                temperatureAggregateRepository.findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_MONTH_2, DAY_IN_MONTH_2);

        assertNotNull(temperatureAverageMonthly);
        assertEquals(temperatureAverageMonthly.getValue(), -3.1, 0);

        temperatureAverageMonthly =
                temperatureAggregateRepository.findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_MONTH_2, DAY_IN_MONTH_2);

        assertNotNull(temperatureAverageMonthly);
        assertEquals(temperatureAverageMonthly.getValue(), 5.04, 0);

        temperatureAverageMonthly =
                temperatureAggregateRepository.findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, NO_DATA_MONTH, NO_DATA_MONTH);

        assertNull(temperatureAverageMonthly);
    }

    @Test
    public void testMinimumFunctions()
    {
        /**
         * daily
         */
        TemperatureMinimumDaily temperatureMinimumDaily =
                temperatureAggregateRepository.findTemperatureMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, DAY_1.with(LocalTime.MAX), DAY_1.with(LocalTime.MIN));

        assertNotNull(temperatureMinimumDaily);
        assertEquals(temperatureMinimumDaily.getValue(), 20.25, 0);

        temperatureMinimumDaily =
                temperatureAggregateRepository.findTemperatureMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, DAY_2.with(LocalTime.MAX), DAY_2.with(LocalTime.MIN));

        assertNotNull(temperatureMinimumDaily);
        assertEquals(temperatureMinimumDaily.getValue(), 5.0, 0);

        temperatureMinimumDaily =
                temperatureAggregateRepository.findTemperatureMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_2, DAY_1.with(LocalTime.MAX), DAY_1.with(LocalTime.MIN));

        assertNotNull(temperatureMinimumDaily);
        assertEquals(temperatureMinimumDaily.getValue(), 32.01, 0);

        temperatureMinimumDaily =
                temperatureAggregateRepository.findTemperatureMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_2, DAY_2.with(LocalTime.MAX), DAY_2.with(LocalTime.MIN));

        assertNotNull(temperatureMinimumDaily);
        assertEquals(temperatureMinimumDaily.getValue(), 10.11, 0);

        temperatureMinimumDaily =
                temperatureAggregateRepository.findTemperatureMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, NO_DATA_DAY.with(LocalTime.MAX), NO_DATA_DAY.with(LocalTime.MIN));

        assertNull(temperatureMinimumDaily);

        /**
         * weekly
         */
        TemperatureMinimumWeekly temperatureMinimumWeekly =
                temperatureAggregateRepository.findTemperatureMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_WEEK_1, DAY_IN_WEEK_1);

        assertNotNull(temperatureMinimumWeekly);
        assertEquals(temperatureMinimumWeekly.getValue(), 18.35, 0);

        temperatureMinimumWeekly =
                temperatureAggregateRepository.findTemperatureMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_WEEK_1, DAY_IN_WEEK_1);

        assertNotNull(temperatureMinimumWeekly);
        assertEquals(temperatureMinimumWeekly.getValue(), 45.02, 0);

        temperatureMinimumWeekly =
                temperatureAggregateRepository.findTemperatureMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_WEEK_2, DAY_IN_WEEK_2);

        assertNotNull(temperatureMinimumWeekly);
        assertEquals(temperatureMinimumWeekly.getValue(), -6.1, 0);

        temperatureMinimumWeekly =
                temperatureAggregateRepository.findTemperatureMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_WEEK_2, DAY_IN_WEEK_2);

        assertNotNull(temperatureMinimumWeekly);
        assertEquals(temperatureMinimumWeekly.getValue(), 8.04, 0);

        temperatureMinimumWeekly =
                temperatureAggregateRepository.findTemperatureMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, NO_DATA_WEEK, NO_DATA_WEEK);

        assertNull(temperatureMinimumWeekly);

        /**
         * monthly
         */
        TemperatureMinimumMonthly temperatureMinimumMonthly =
                temperatureAggregateRepository.findTemperatureMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_MONTH_1, DAY_IN_MONTH_1);

        assertNotNull(temperatureMinimumMonthly);
        assertEquals(temperatureMinimumMonthly.getValue(), 65.35, 0);

        temperatureMinimumMonthly =
                temperatureAggregateRepository.findTemperatureMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_MONTH_1, DAY_IN_MONTH_1);

        assertNotNull(temperatureMinimumMonthly);
        assertEquals(temperatureMinimumMonthly.getValue(), 12.02, 0);

        temperatureMinimumMonthly =
                temperatureAggregateRepository.findTemperatureMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_MONTH_2, DAY_IN_MONTH_2);

        assertNotNull(temperatureMinimumMonthly);
        assertEquals(temperatureMinimumMonthly.getValue(), -3.1, 0);

        temperatureMinimumMonthly =
                temperatureAggregateRepository.findTemperatureMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_MONTH_2, DAY_IN_MONTH_2);

        assertNotNull(temperatureMinimumMonthly);
        assertEquals(temperatureMinimumMonthly.getValue(), 5.04, 0);

        temperatureMinimumMonthly =
                temperatureAggregateRepository.findTemperatureMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, NO_DATA_MONTH, NO_DATA_MONTH);

        assertNull(temperatureMinimumMonthly);
    }

    @Test
    public void testMaximumFunctions()
    {
        /**
         * daily
         */
        TemperatureMaximumDaily temperatureMaximumDaily =
                temperatureAggregateRepository.findTemperatureMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, DAY_1.with(LocalTime.MAX), DAY_1.with(LocalTime.MIN));

        assertNotNull(temperatureMaximumDaily);
        assertEquals(temperatureMaximumDaily.getValue(), 20.25, 0);

        temperatureMaximumDaily =
                temperatureAggregateRepository.findTemperatureMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, DAY_2.with(LocalTime.MAX), DAY_2.with(LocalTime.MIN));

        assertNotNull(temperatureMaximumDaily);
        assertEquals(temperatureMaximumDaily.getValue(), 5.0, 0);

        temperatureMaximumDaily =
                temperatureAggregateRepository.findTemperatureMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_2, DAY_1.with(LocalTime.MAX), DAY_1.with(LocalTime.MIN));

        assertNotNull(temperatureMaximumDaily);
        assertEquals(temperatureMaximumDaily.getValue(), 32.01, 0);

        temperatureMaximumDaily =
                temperatureAggregateRepository.findTemperatureMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_2, DAY_2.with(LocalTime.MAX), DAY_2.with(LocalTime.MIN));

        assertNotNull(temperatureMaximumDaily);
        assertEquals(temperatureMaximumDaily.getValue(), 10.11, 0);

        temperatureMaximumDaily =
                temperatureAggregateRepository.findTemperatureMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, NO_DATA_DAY.with(LocalTime.MAX), NO_DATA_DAY.with(LocalTime.MIN));

        assertNull(temperatureMaximumDaily);

        /**
         * weekly
         */
        TemperatureMaximumWeekly temperatureMaximumWeekly =
                temperatureAggregateRepository.findTemperatureMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_WEEK_1, DAY_IN_WEEK_1);

        assertNotNull(temperatureMaximumWeekly);
        assertEquals(temperatureMaximumWeekly.getValue(), 18.35, 0);

        temperatureMaximumWeekly =
                temperatureAggregateRepository.findTemperatureMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_WEEK_1, DAY_IN_WEEK_1);

        assertNotNull(temperatureMaximumWeekly);
        assertEquals(temperatureMaximumWeekly.getValue(), 45.02, 0);

        temperatureMaximumWeekly =
                temperatureAggregateRepository.findTemperatureMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_WEEK_2, DAY_IN_WEEK_2);

        assertNotNull(temperatureMaximumWeekly);
        assertEquals(temperatureMaximumWeekly.getValue(), -6.1, 0);

        temperatureMaximumWeekly =
                temperatureAggregateRepository.findTemperatureMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_WEEK_2, DAY_IN_WEEK_2);

        assertNotNull(temperatureMaximumWeekly);
        assertEquals(temperatureMaximumWeekly.getValue(), 8.04, 0);

        temperatureMaximumWeekly =
                temperatureAggregateRepository.findTemperatureMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, NO_DATA_WEEK, NO_DATA_WEEK);

        assertNull(temperatureMaximumWeekly);

        /**
         * monthly
         */
        TemperatureMaximumMonthly temperatureMaximumMonthly =
                temperatureAggregateRepository.findTemperatureMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_MONTH_1, DAY_IN_MONTH_1);

        assertNotNull(temperatureMaximumMonthly);
        assertEquals(temperatureMaximumMonthly.getValue(), 65.35, 0);

        temperatureMaximumMonthly =
                temperatureAggregateRepository.findTemperatureMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_MONTH_1, DAY_IN_MONTH_1);

        assertNotNull(temperatureMaximumMonthly);
        assertEquals(temperatureMaximumMonthly.getValue(), 12.02, 0);

        temperatureMaximumMonthly =
                temperatureAggregateRepository.findTemperatureMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_MONTH_2, DAY_IN_MONTH_2);

        assertNotNull(temperatureMaximumMonthly);
        assertEquals(temperatureMaximumMonthly.getValue(), -3.1, 0);

        temperatureMaximumMonthly =
                temperatureAggregateRepository.findTemperatureMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_MONTH_2, DAY_IN_MONTH_2);

        assertNotNull(temperatureMaximumMonthly);
        assertEquals(temperatureMaximumMonthly.getValue(), 5.04, 0);

        temperatureMaximumMonthly =
                temperatureAggregateRepository.findTemperatureMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, NO_DATA_MONTH, NO_DATA_MONTH);

        assertNull(temperatureMaximumMonthly);
    }
}
