package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.airquality.AirQuality;
import htw.smartcity.aggregator.temperature.Temperature;
import htw.smartcity.aggregator.temperature.TemperatureRepository;
import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The type Temperature repository tests.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TemperatureRepositoryTests {

    private static final Long TEMPERATURE_SENSOR1_ID = 8L;
    private static final Long TEMPERATURE_SENSOR2_ID = 9L;
    private static final LocalDateTime TimeFrom = LocalDateTime.of(2020, Month.JULY, 23, 15, 36, 0);
    private static final LocalDateTime TimeTo = LocalDateTime.of(2020, Month.JULY, 23, 15, 40, 0);


    @Autowired
    private TemperatureRepository temperatureRepository;

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Test temperature entities.
     */
    @Test
    public void testTemperatureEntities() {
        Sensor sensor = sensorRepository.findById(TEMPERATURE_SENSOR1_ID).get();
        Temperature temperature = new Temperature(LocalDateTime.now(), sensor, 37.25);
        Long id = temperatureRepository.save(temperature).getId();
        Temperature temperature2 = temperatureRepository.findById(id).get();
        assertNotNull(temperature);
        assertEquals(temperature2.getValue(), temperature.getValue());
        assertEquals(temperature2.getTime(), temperature.getTime());
        assertEquals(temperature2.getSensor().getId(), temperature.getSensor().getId());
        temperatureRepository.delete(temperature);
        boolean isPresent = temperatureRepository.findById(id).isPresent();
        assertEquals(isPresent, false);
    }

    /**
     * Test find temperatures by time before and time after.
     */
    @Test
    public void testFindTemperaturesByTimeBeforeAndTimeAfter() {
        Page temperaturesByTimeAfterAndTimeBefore = temperatureRepository.findTemperaturesByTimeAfterAndTimeBefore(TimeFrom, TimeTo, null);
        List<Temperature> temperatures = (List<Temperature>) temperaturesByTimeAfterAndTimeBefore.get().collect(Collectors.toList());
        int numberOfElements = temperaturesByTimeAfterAndTimeBefore.getNumberOfElements();
        assertEquals(numberOfElements, 2);
        double valueTemperature1 = temperatures.get(0).getValue();
        double valueTemperature2 = temperatures.get(1).getValue();
        assertEquals(valueTemperature1, 23.2, 0);
        assertEquals(valueTemperature2, 23.45, 0);
    }

    /**
     * Test find temperatures by sensor id.
     */
    @Test
    public void testFindTemperaturesBySensorId() {
        Sensor sensor1 = sensorRepository.findById(TEMPERATURE_SENSOR1_ID).get();
        Sensor sensor2 = sensorRepository.findById(TEMPERATURE_SENSOR2_ID).get();
        long elementsSensor1 = temperatureRepository.findTemperaturesBySensorId(sensor1.getId(), null).getTotalElements();
        long elementsSensor2 = temperatureRepository.findTemperaturesBySensorId(sensor2.getId(), null).getTotalElements();
        assertEquals(elementsSensor1, 20);
        assertEquals(elementsSensor2, 3);
    }

    /**
     * Test find temperatures by sensor id and time between.
     */
    @Test
    public void testFindTemperaturesBySensorIdAndTimeBetweenPaging() {
        Page temperaturesByTimeAfterAndTimeBefore = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(TEMPERATURE_SENSOR2_ID, TimeFrom, TimeTo, null);
        List<Temperature> temperatures = (List<Temperature>) temperaturesByTimeAfterAndTimeBefore.get().collect(Collectors.toList());
        int numberOfElements = temperaturesByTimeAfterAndTimeBefore.getNumberOfElements();
        assertEquals(numberOfElements, 2);
        double valueTemperature1 = temperatures.get(0).getValue();
        double valueTemperature2 = temperatures.get(1).getValue();
        assertEquals(valueTemperature1, 23.2, 0);
        assertEquals(valueTemperature2, 23.45, 0);

        temperaturesByTimeAfterAndTimeBefore = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(TEMPERATURE_SENSOR1_ID, TimeFrom, TimeTo, null);
        numberOfElements = temperaturesByTimeAfterAndTimeBefore.getNumberOfElements();
        assertEquals(numberOfElements, 0);

    }

    /**
     * Test find temperatures by sensor id and time between.
     */
    @Test
    public void testFindTemperaturesBySensorIdAndTimeBetween() {
        List<Temperature> temperatures = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(TEMPERATURE_SENSOR2_ID, TimeFrom, TimeTo);
        int numberOfElements = temperatures.size();
        assertEquals(numberOfElements, 2);
        double valueTemperature1 = temperatures.get(0).getValue();
        double valueTemperature2 = temperatures.get(1).getValue();
        assertEquals(valueTemperature1, 23.2, 0);
        assertEquals(valueTemperature2, 23.45, 0);

        temperatures = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(TEMPERATURE_SENSOR1_ID, TimeFrom, TimeTo);
        numberOfElements = temperatures.size();
        assertEquals(numberOfElements, 0);
    }


}