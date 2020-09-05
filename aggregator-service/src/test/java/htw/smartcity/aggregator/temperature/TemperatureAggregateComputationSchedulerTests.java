package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.temperatureaggregate.TemperatureAggregateComputationScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * The type Temperature aggregate computation scheduler tests.
 */
@RunWith (SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase (connection = EmbeddedDatabaseConnection.H2)
public class TemperatureAggregateComputationSchedulerTests
{
    /**
     * The Temperature aggregate computation scheduler.
     */
    @Autowired
    TemperatureAggregateComputationScheduler temperatureAggregateComputationScheduler;

    /**
     * Tests to debug through the computation scheduler
     * (no asserts, just for looking into the functions)
     */
    @Test
    public void dailyAggregation()
    {

        temperatureAggregateComputationScheduler.computeAggregatesDaily((long) 8, LocalDateTime.of(2020, Month.SEPTEMBER, 1, 0, 0, 0),
                                         LocalDateTime.of(2020, Month.SEPTEMBER, 1, 23, 59, 59));
    }

    /**
     * Weekly aggregation.
     */
    @Test
    public void weeklyAggregation()
    {

        temperatureAggregateComputationScheduler.computeAggregatesWeekly((long) 8, LocalDateTime.of(2020,
                                                                                                   Month.AUGUST, 31, 0
                , 0,
                                                                                                    0),
                                                                        LocalDateTime.of(2020, Month.SEPTEMBER, 6, 23
                                                                                , 59, 59));
    }

    /**
     * Monthly aggregation.
     */
    @Test
    public void monthlyAggregation()
    {

        temperatureAggregateComputationScheduler.computeAggregatesMonthly((long) 8, LocalDateTime.of(2020,
                                                                                                    Month.SEPTEMBER, 1, 0, 0, 0),
                                                                         LocalDateTime.of(2020, Month.OCTOBER, 1, 23, 59,
                                                                                          59));
    }
}
