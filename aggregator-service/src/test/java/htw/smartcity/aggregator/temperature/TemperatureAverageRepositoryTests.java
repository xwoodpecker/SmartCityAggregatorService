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
import java.util.List;

@RunWith (SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase (connection = EmbeddedDatabaseConnection.H2)
public class TemperatureAverageRepositoryTests
{
    @Autowired
    SensorRepository sensorRepository;

    @Autowired
    TemperatureRepository temperatureRepository;

    @Autowired
    TemperatureAggregateRepository temperatureAggregateRepository;

    @Test
    public void gradmoentest()
    {
        List<Sensor> sensors = sensorRepository.findBySensorType(SensorType.TEMPERATURE);
        double       sum     = 0;
        int          count   = 0;

        for(Sensor s : sensors)
        {
            List<Temperature> tList = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(s.getId(),
                                                                                                     LocalDateTime.now().minusDays(1).with(LocalTime.MIN),
                                                                                                     LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
            sum = 0;
            count = 0;
            for(Temperature t : tList)
            {
                sum += t.getValue();
                count++;
            }
            TemperatureAverageDaily temperatureAverageDaily = new TemperatureAverageDaily();
            temperatureAverageDaily.setValue(sum/count);
            temperatureAverageDaily.setSensor(sensorRepository.getOne(s.getId()));
            temperatureAverageDaily.setDate(LocalDateTime.now().minusDays(1));

            temperatureAggregateRepository.save(temperatureAverageDaily);
        }
    }
}
