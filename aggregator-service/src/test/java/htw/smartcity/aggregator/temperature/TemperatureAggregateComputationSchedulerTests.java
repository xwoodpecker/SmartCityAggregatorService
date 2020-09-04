package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorRepository;
import htw.smartcity.aggregator.sensor.SensorType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

@RunWith (SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase (connection = EmbeddedDatabaseConnection.H2)
public class TemperatureAggregateComputationSchedulerTests
{
    @Autowired
    TemperatureAggregateComputationScheduler temperatureAggregateComputationScheduler;

    @Test
    public void dailyAggregation()
    {

        temperatureAggregateComputationScheduler.computeAggregatesDaily((long) 8, LocalDateTime.of(2020, Month.SEPTEMBER, 1, 0, 0, 0),
                                         LocalDateTime.of(2020, Month.SEPTEMBER, 1, 23, 59, 59));

    }

}
