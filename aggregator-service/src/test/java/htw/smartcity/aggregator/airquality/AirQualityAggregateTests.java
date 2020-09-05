package htw.smartcity.aggregator.airquality;

import htw.smartcity.aggregator.airqualityaggregator.*;
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

/**
 * The type Air quality aggregate tests.
 */
@RunWith (SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase (connection = EmbeddedDatabaseConnection.H2)
public class AirQualityAggregateTests
{
    /**
     * Tests for AirQualityAggregateRepository
     */

    private static final long          SENSOR_1    = 1;
    private static final long          SENSOR_2    = 2;
    private static final LocalDateTime DAY_1       = LocalDateTime.of(2020, Month.AUGUST, 20, 15, 40, 0);
    private static final LocalDateTime DAY_2       = LocalDateTime.of(2020, Month.AUGUST, 21, 15, 40, 0);
    private static final LocalDateTime NO_DATA_DAY = LocalDateTime.of(2020, Month.AUGUST, 23, 15, 40, 0);

    private static final LocalDateTime DAY_IN_WEEK_1   = LocalDateTime.of(2020, Month.AUGUST, 22, 15, 40, 0);
    private static final LocalDateTime DAY_IN_WEEK_2   = LocalDateTime.of(2020, Month.AUGUST, 30, 15, 40, 0);
    private static final LocalDateTime NO_DATA_WEEK   = LocalDateTime.of(2020, Month.AUGUST, 12, 15, 40, 0);

    private static final LocalDateTime DAY_IN_MONTH_1  = LocalDateTime.of(2020, Month.JULY, 15, 15, 40, 0);
    private static final LocalDateTime DAY_IN_MONTH_2   = LocalDateTime.of(2020, Month.AUGUST, 14, 15, 40, 0);
    private static final LocalDateTime NO_DATA_MONTH  = LocalDateTime.of(2020, Month.SEPTEMBER, 14, 15, 40, 0);

    /**
     * The Air quality aggregate repository.
     */
    @Autowired
    AirQualityAggregateRepository airQualityAggregateRepository;

    /**
     * Test average functions.
     */
    @Test
    public void testAverageFunctions()
    {
        /**
         * daily
         */
        AirQualityAverageDaily airqualityAverageDaily =
                airQualityAggregateRepository.findAirQualityAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, DAY_1.with(LocalTime.MAX), DAY_1.with(LocalTime.MIN));

        assertNotNull(airqualityAverageDaily);
        assertEquals(airqualityAverageDaily.getValue(), 20.01, 0);

        airqualityAverageDaily =
                airQualityAggregateRepository.findAirQualityAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, DAY_2.with(LocalTime.MAX), DAY_2.with(LocalTime.MIN));

        assertNotNull(airqualityAverageDaily);
        assertEquals(airqualityAverageDaily.getValue(), 5, 0);

        airqualityAverageDaily =
                airQualityAggregateRepository.findAirQualityAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_2, DAY_1.with(LocalTime.MAX), DAY_1.with(LocalTime.MIN));

        assertNotNull(airqualityAverageDaily);
        assertEquals(airqualityAverageDaily.getValue(), 32, 0);

        airqualityAverageDaily =
                airQualityAggregateRepository.findAirQualityAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_2, DAY_2.with(LocalTime.MAX), DAY_2.with(LocalTime.MIN));

        assertNotNull(airqualityAverageDaily);
        assertEquals(airqualityAverageDaily.getValue(), 10.57, 0);

        airqualityAverageDaily =
                airQualityAggregateRepository.findAirQualityAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, NO_DATA_DAY.with(LocalTime.MAX), NO_DATA_DAY.with(LocalTime.MIN));

        assertNull(airqualityAverageDaily);

        /**
         * weekly
         */
        AirQualityAverageWeekly airQualityAverageWeekly =
                airQualityAggregateRepository.findAirQualityAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_WEEK_1, DAY_IN_WEEK_1);

        assertNotNull(airQualityAverageWeekly);
        assertEquals(airQualityAverageWeekly.getValue(), 18, 0);

        airQualityAverageWeekly =
                airQualityAggregateRepository.findAirQualityAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_WEEK_1, DAY_IN_WEEK_1);

        assertNotNull(airQualityAverageWeekly);
        assertEquals(airQualityAverageWeekly.getValue(), 45.87, 0);

        airQualityAverageWeekly =
                airQualityAggregateRepository.findAirQualityAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_WEEK_2, DAY_IN_WEEK_2);

        assertNotNull(airQualityAverageWeekly);
        assertEquals(airQualityAverageWeekly.getValue(), -6.71, 0);

        airQualityAverageWeekly =
                airQualityAggregateRepository.findAirQualityAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_WEEK_2, DAY_IN_WEEK_2);

        assertNotNull(airQualityAverageWeekly);
        assertEquals(airQualityAverageWeekly.getValue(), 8, 0);

        airQualityAverageWeekly =
                airQualityAggregateRepository.findAirQualityAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, NO_DATA_WEEK, NO_DATA_WEEK);

        assertNull(airQualityAverageWeekly);

        /**
         * monthly
         */
        AirQualityAverageMonthly airQualityAverageMonthly =
                airQualityAggregateRepository.findAirQualityAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_MONTH_1, DAY_IN_MONTH_1);

        assertNotNull(airQualityAverageMonthly);
        assertEquals(airQualityAverageMonthly.getValue(), 65.43, 0);

        airQualityAverageMonthly =
                airQualityAggregateRepository.findAirQualityAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_MONTH_1, DAY_IN_MONTH_1);

        assertNotNull(airQualityAverageMonthly);
        assertEquals(airQualityAverageMonthly.getValue(), 12, 0);

        airQualityAverageMonthly =
                airQualityAggregateRepository.findAirQualityAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_MONTH_2, DAY_IN_MONTH_2);

        assertNotNull(airQualityAverageMonthly);
        assertEquals(airQualityAverageMonthly.getValue(), -3, 0);

        airQualityAverageMonthly =
                airQualityAggregateRepository.findAirQualityAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_MONTH_2, DAY_IN_MONTH_2);

        assertNotNull(airQualityAverageMonthly);
        assertEquals(airQualityAverageMonthly.getValue(), 5.66, 0);

        airQualityAverageMonthly =
                airQualityAggregateRepository.findAirQualityAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, NO_DATA_MONTH, NO_DATA_MONTH);

        assertNull(airQualityAverageMonthly);
    }

    /**
     * Test minimum functions.
     */
    @Test
    public void testMinimumFunctions()
    {
        /**
         * daily
         */
        AirQualityMinimumDaily airQualityMinimumDaily =
                airQualityAggregateRepository.findAirQualityMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, DAY_1.with(LocalTime.MAX), DAY_1.with(LocalTime.MIN));

        assertNotNull(airQualityMinimumDaily);
        assertEquals(airQualityMinimumDaily.getValue(), 20, 0);

        airQualityMinimumDaily =
                airQualityAggregateRepository.findAirQualityMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, DAY_2.with(LocalTime.MAX), DAY_2.with(LocalTime.MIN));

        assertNotNull(airQualityMinimumDaily);
        assertEquals(airQualityMinimumDaily.getValue(), 5, 0);

        airQualityMinimumDaily =
                airQualityAggregateRepository.findAirQualityMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_2, DAY_1.with(LocalTime.MAX), DAY_1.with(LocalTime.MIN));

        assertNotNull(airQualityMinimumDaily);
        assertEquals(airQualityMinimumDaily.getValue(), 32, 0);

        airQualityMinimumDaily =
                airQualityAggregateRepository.findAirQualityMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_2, DAY_2.with(LocalTime.MAX), DAY_2.with(LocalTime.MIN));

        assertNotNull(airQualityMinimumDaily);
        assertEquals(airQualityMinimumDaily.getValue(), 10, 0);

        airQualityMinimumDaily =
                airQualityAggregateRepository.findAirQualityMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, NO_DATA_DAY.with(LocalTime.MAX), NO_DATA_DAY.with(LocalTime.MIN));

        assertNull(airQualityMinimumDaily);

        /**
         * weekly
         */
        AirQualityMinimumWeekly airQualityMinimumWeekly =
                airQualityAggregateRepository.findAirQualityMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_WEEK_1, DAY_IN_WEEK_1);

        assertNotNull(airQualityMinimumWeekly);
        assertEquals(airQualityMinimumWeekly.getValue(), 18, 0);

        airQualityMinimumWeekly =
                airQualityAggregateRepository.findAirQualityMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_WEEK_1, DAY_IN_WEEK_1);

        assertNotNull(airQualityMinimumWeekly);
        assertEquals(airQualityMinimumWeekly.getValue(), 45, 0);

        airQualityMinimumWeekly =
                airQualityAggregateRepository.findAirQualityMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_WEEK_2, DAY_IN_WEEK_2);

        assertNotNull(airQualityMinimumWeekly);
        assertEquals(airQualityMinimumWeekly.getValue(), -6, 0);

        airQualityMinimumWeekly =
                airQualityAggregateRepository.findAirQualityMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_WEEK_2, DAY_IN_WEEK_2);

        assertNotNull(airQualityMinimumWeekly);
        assertEquals(airQualityMinimumWeekly.getValue(), 8, 0);

        airQualityMinimumWeekly =
                airQualityAggregateRepository.findAirQualityMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, NO_DATA_WEEK, NO_DATA_WEEK);

        assertNull(airQualityMinimumWeekly);

        /**
         * monthly
         */
        AirQualityMinimumMonthly airQualityMinimumMonthly =
                airQualityAggregateRepository.findAirQualityMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_MONTH_1, DAY_IN_MONTH_1);

        assertNotNull(airQualityMinimumMonthly);
        assertEquals(airQualityMinimumMonthly.getValue(), 65, 0);

        airQualityMinimumMonthly =
                airQualityAggregateRepository.findAirQualityMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_MONTH_1, DAY_IN_MONTH_1);

        assertNotNull(airQualityMinimumMonthly);
        assertEquals(airQualityMinimumMonthly.getValue(), 12, 0);

        airQualityMinimumMonthly =
                airQualityAggregateRepository.findAirQualityMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_MONTH_2, DAY_IN_MONTH_2);

        assertNotNull(airQualityMinimumMonthly);
        assertEquals(airQualityMinimumMonthly.getValue(), -3, 0);

        airQualityMinimumMonthly =
                airQualityAggregateRepository.findAirQualityMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_MONTH_2, DAY_IN_MONTH_2);

        assertNotNull(airQualityMinimumMonthly);
        assertEquals(airQualityMinimumMonthly.getValue(), 5, 0);

        airQualityMinimumMonthly =
                airQualityAggregateRepository.findAirQualityMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, NO_DATA_MONTH, NO_DATA_MONTH);

        assertNull(airQualityMinimumMonthly);
    }

    /**
     * Test maximum functions.
     */
    @Test
    public void testMaximumFunctions()
    {
        /**
         * daily
         */
        AirQualityMaximumDaily airQualityMaximumDaily =
                airQualityAggregateRepository.findAirQualityMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, DAY_1.with(LocalTime.MAX), DAY_1.with(LocalTime.MIN));

        assertNotNull(airQualityMaximumDaily);
        assertEquals(airQualityMaximumDaily.getValue(), 20, 0);

        airQualityMaximumDaily =
                airQualityAggregateRepository.findAirQualityMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, DAY_2.with(LocalTime.MAX), DAY_2.with(LocalTime.MIN));

        assertNotNull(airQualityMaximumDaily);
        assertEquals(airQualityMaximumDaily.getValue(), 5, 0);

        airQualityMaximumDaily =
                airQualityAggregateRepository.findAirQualityMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_2, DAY_1.with(LocalTime.MAX), DAY_1.with(LocalTime.MIN));

        assertNotNull(airQualityMaximumDaily);
        assertEquals(airQualityMaximumDaily.getValue(), 32, 0);

        airQualityMaximumDaily =
                airQualityAggregateRepository.findAirQualityMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_2, DAY_2.with(LocalTime.MAX), DAY_2.with(LocalTime.MIN));

        assertNotNull(airQualityMaximumDaily);
        assertEquals(airQualityMaximumDaily.getValue(), 10, 0);

        airQualityMaximumDaily =
                airQualityAggregateRepository.findAirQualityMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(SENSOR_1, NO_DATA_DAY.with(LocalTime.MAX), NO_DATA_DAY.with(LocalTime.MIN));

        assertNull(airQualityMaximumDaily);

        /**
         * weekly
         */
        AirQualityMaximumWeekly airQualityMaximumWeekly =
                airQualityAggregateRepository.findAirQualityMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_WEEK_1, DAY_IN_WEEK_1);

        assertNotNull(airQualityMaximumWeekly);
        assertEquals(airQualityMaximumWeekly.getValue(), 18, 0);

        airQualityMaximumWeekly =
                airQualityAggregateRepository.findAirQualityMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_WEEK_1, DAY_IN_WEEK_1);

        assertNotNull(airQualityMaximumWeekly);
        assertEquals(airQualityMaximumWeekly.getValue(), 45, 0);

        airQualityMaximumWeekly =
                airQualityAggregateRepository.findAirQualityMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_WEEK_2, DAY_IN_WEEK_2);

        assertNotNull(airQualityMaximumWeekly);
        assertEquals(airQualityMaximumWeekly.getValue(), -6, 0);

        airQualityMaximumWeekly =
                airQualityAggregateRepository.findAirQualityMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_WEEK_2, DAY_IN_WEEK_2);

        assertNotNull(airQualityMaximumWeekly);
        assertEquals(airQualityMaximumWeekly.getValue(), 8, 0);

        airQualityMaximumWeekly =
                airQualityAggregateRepository.findAirQualityMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, NO_DATA_WEEK, NO_DATA_WEEK);

        assertNull(airQualityMaximumWeekly);

        /**
         * monthly
         */
        AirQualityMaximumMonthly airQualityMaximumMonthly =
                airQualityAggregateRepository.findAirQualityMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_MONTH_1, DAY_IN_MONTH_1);

        assertNotNull(airQualityMaximumMonthly);
        assertEquals(airQualityMaximumMonthly.getValue(), 65, 0);

        airQualityMaximumMonthly =
                airQualityAggregateRepository.findAirQualityMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_MONTH_1, DAY_IN_MONTH_1);

        assertNotNull(airQualityMaximumMonthly);
        assertEquals(airQualityMaximumMonthly.getValue(), 12, 0);

        airQualityMaximumMonthly =
                airQualityAggregateRepository.findAirQualityMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, DAY_IN_MONTH_2, DAY_IN_MONTH_2);

        assertNotNull(airQualityMaximumMonthly);
        assertEquals(airQualityMaximumMonthly.getValue(), -3, 0);

        airQualityMaximumMonthly =
                airQualityAggregateRepository.findAirQualityMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_2, DAY_IN_MONTH_2, DAY_IN_MONTH_2);

        assertNotNull(airQualityMaximumMonthly);
        assertEquals(airQualityMaximumMonthly.getValue(), 5, 0);

        airQualityMaximumMonthly =
                airQualityAggregateRepository.findAirQualityMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(SENSOR_1, NO_DATA_MONTH, NO_DATA_MONTH);

        assertNull(airQualityMaximumMonthly);
    }
}
