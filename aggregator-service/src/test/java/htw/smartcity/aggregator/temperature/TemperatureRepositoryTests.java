package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.airquality.Airquality;
import htw.smartcity.aggregator.airquality.AirqualityRepository;
import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TemperatureRepositoryTests {

    private static final Long AIRQUALITYSENSOR1_ID = 1L;
    private static final Long AIRQUALITYSENSOR2_ID = 2L;
    private static final LocalDateTime TimeFrom = LocalDateTime.of(2020, Month.JULY, 23, 15, 36, 0);
    private static final LocalDateTime TimeTo = LocalDateTime.of(2020, Month.JULY, 23, 15, 40, 0);


    @Autowired
    private AirqualityRepository airqualityRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Test
    public void testAirqualityEntities() {
        Sensor sensor = sensorRepository.findById(AIRQUALITYSENSOR1_ID).get();
        Airquality airquality = new Airquality(LocalDateTime.now(), sensor, 100);
        Long id = airqualityRepository.save(airquality).getId();
        Airquality airquality2 = airqualityRepository.findById(id).get();
        assertNotNull(airquality);
        assertEquals(airquality2.getValue(), airquality.getValue());
        assertEquals(airquality2.getTime(), airquality.getTime());
        assertEquals(airquality2.getSensor().getId(), airquality.getSensor().getId());
        airqualityRepository.delete(airquality);
        boolean isPresent = airqualityRepository.findById(id).isPresent();
        assertEquals(isPresent, false);
    }

    @Test
    public void testFindAirqualitiesBySensorId() {
        Sensor sensor1 = sensorRepository.findById(AIRQUALITYSENSOR1_ID).get();
        Sensor sensor2 = sensorRepository.findById(AIRQUALITYSENSOR2_ID).get();
        long elementsSensor1 = airqualityRepository.findAirqualitiesBySensorId(sensor1.getId(), null).getTotalElements();
        long elementsSensor2 = airqualityRepository.findAirqualitiesBySensorId(sensor2.getId(), null).getTotalElements();
        assertEquals(elementsSensor1, 2);
        assertEquals(elementsSensor2, 3);
    }

    @Test
    public void testFindAirqualitiesByTimeBeforeAndTimeAfter() {
        List<Airquality> airqualities = (List<Airquality>) airqualityRepository.findAirqualitiesByTimeAfterAndTimeBefore(TimeFrom, TimeTo, null).get().collect(Collectors.toList());
        long elements = airqualityRepository.findAirqualitiesByTimeAfterAndTimeBefore(TimeFrom, TimeTo, null).getTotalElements();
        assertEquals(elements, 2);
        int valueAirquality1 = airqualities.get(0).getValue();
        int valueAirquality2 = airqualities.get(1).getValue();
        assertEquals(valueAirquality1, 80);
        assertEquals(valueAirquality2, 92);

    }

}