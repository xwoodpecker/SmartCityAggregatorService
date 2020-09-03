package htw.smartcity.aggregator.temperature;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith (SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase (connection = EmbeddedDatabaseConnection.H2)
public class TemperatureAverageRepositoryTests
{
    @Autowired
    private TemperatureAverageRepository temperatureAverageRepository;

    @Test
    public void gradmoentest()
    {
        TemperatureAverageDaily temperatureAverageDaily = new TemperatureAverageDaily();
        temperatureAverageDaily.setDate(LocalDateTime.now());
        //temperatureAverageDaily.setSensor(sensorId);
        temperatureAverageDaily.setValue(1.0);

        temperatureAverageRepository.save(temperatureAverageDaily);
    }
}
