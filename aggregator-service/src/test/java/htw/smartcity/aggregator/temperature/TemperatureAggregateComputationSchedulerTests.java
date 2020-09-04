package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorRepository;
import htw.smartcity.aggregator.sensor.SensorType;
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
import java.util.List;

@RunWith (SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase (connection = EmbeddedDatabaseConnection.H2)
public class TemperatureAggregateComputationSchedulerTests
{
    @Autowired
    TemperatureAggregateComputationScheduler scheduler;

    @Test
    public void dailyAggregation()
    {
        //TemperatureAggregateComputationScheduler scheduler = new TemperatureAggregateComputationScheduler();

        scheduler.computeAggregatesDaily((long) 8, LocalDateTime.of(2020, Month.SEPTEMBER, 1, 0, 0, 0),
                                         LocalDateTime.of(2020, Month.SEPTEMBER, 1, 23, 59, 59));

    }

    @Test
    public void weeklyAggregation()
    {
        //TemperatureAggregateComputationScheduler scheduler = new TemperatureAggregateComputationScheduler();

        scheduler.computeAggregatesWeekly((long) 8, LocalDateTime.of(2020, Month.AUGUST, 31, 0, 0, 0),
                                         LocalDateTime.of(2020, Month.SEPTEMBER, 6, 23, 59, 59));

    }

    @Test
    public void monthlyAggregation()
    {
        //TemperatureAggregateComputationScheduler scheduler = new TemperatureAggregateComputationScheduler();

        scheduler.computeAggregatesMonthly((long) 8, LocalDateTime.of(2020, Month.SEPTEMBER, 1, 0, 0, 0),
                                          LocalDateTime.of(2020, Month.OCTOBER, 1, 23, 59, 59));

    }
}
