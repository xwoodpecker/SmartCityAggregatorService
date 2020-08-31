package htw.smartcity.aggregator.airquality;

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
 * The type Air quality repository tests.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AirQualityRepositoryTests {

    private static final Long AIRQUALITY_SENSOR1_ID = 1L;
    private static final Long AIRQUALITY_SENSOR2_ID = 2L;
    private static final LocalDateTime TimeFrom = LocalDateTime.of(2020, Month.JULY, 23, 15, 36, 0);
    private static final LocalDateTime TimeTo = LocalDateTime.of(2020, Month.JULY, 23, 15, 40, 0);


    @Autowired
    private AirQualityRepository airQualityRepository;

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Test air quality entities.
     */
    @Test
    public void testAirQualityEntities() {
        Sensor sensor = sensorRepository.findById(AIRQUALITY_SENSOR1_ID).get();
        AirQuality airQuality = new AirQuality(LocalDateTime.now(), sensor, 100);
        Long id = airQualityRepository.save(airQuality).getId();
        AirQuality airQuality2 = airQualityRepository.findById(id).get();
        assertNotNull(airQuality2);
        assertEquals(airQuality2.getValue(), airQuality.getValue());
        assertEquals(airQuality2.getTime(), airQuality.getTime());
        assertEquals(airQuality2.getSensor().getId(), airQuality.getSensor().getId());
        airQualityRepository.delete(airQuality2);
        boolean isPresent = airQualityRepository.findById(id).isPresent();
        assertEquals(isPresent, false);
    }

    /**
     * Test find air qualities by sensor id.
     */
    @Test
    public void testFindAirQualitiesBySensorId() {
        Sensor sensor1 = sensorRepository.findById(AIRQUALITY_SENSOR1_ID).get();
        Sensor sensor2 = sensorRepository.findById(AIRQUALITY_SENSOR2_ID).get();
        long elementsSensor1 = airQualityRepository.findAirQualitiesBySensorId(sensor1.getId(), null).getTotalElements();
        long elementsSensor2 = airQualityRepository.findAirQualitiesBySensorId(sensor2.getId(), null).getTotalElements();
        assertEquals(elementsSensor1, 2);
        assertEquals(elementsSensor2, 3);
    }

    /**
     * Test find air qualities by time before and time after.
     */
    @Test
    public void testFindAirQualitiesByTimeBeforeAndTimeAfter() {
        Page airQualitiesByTimeAfterAndTimeBefore = airQualityRepository.findAirQualitiesByTimeAfterAndTimeBefore(TimeFrom, TimeTo, null);
        List<AirQuality> airQualities = (List<AirQuality>) airQualitiesByTimeAfterAndTimeBefore.get().collect(Collectors.toList());
        int numberOfElements = airQualitiesByTimeAfterAndTimeBefore.getNumberOfElements();
        assertEquals(numberOfElements, 2);
        int valueAirQuality1 = airQualities.get(0).getValue();
        int valueAirQuality2 = airQualities.get(1).getValue();
        assertEquals(valueAirQuality1, 80);
        assertEquals(valueAirQuality2, 92);

    }

}