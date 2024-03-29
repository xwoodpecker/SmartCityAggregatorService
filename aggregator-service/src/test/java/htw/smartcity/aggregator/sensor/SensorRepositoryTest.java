package htw.smartcity.aggregator.sensor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * The type Sensor repository test.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SensorRepositoryTest {
    private static final String SENSOR_NAME = "new sensor";
    private static final double SENSOR_X = 125.5;
    private static final double SENSOR_Y = 130.2;
    private static final String SENSOR_INFORMATION = "useful information";
    private static final String FIND_SENSOR_NAME1 = "parking_sensor3";
    private static final String FIND_SENSOR_NAME2 = "temperature_sensor2";


    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Test sensor entities.
     */
    @Test
    public void testSensorEntities() {
        Sensor sensor = new Sensor(SENSOR_NAME, SensorType.TEMPERATURE, SENSOR_X, SENSOR_Y, SENSOR_INFORMATION);
        Long id = sensorRepository.save(sensor).getId();
        Sensor sensor2 = sensorRepository.findById(id).get();
        assertNotNull(sensor2);
        assertEquals(sensor2.getName(), sensor.getName());
        assertEquals(sensor2.getSensorType(), sensor.getSensorType());
        assertEquals(sensor2.getX(), sensor.getX());
        assertEquals(sensor2.getY(), sensor.getY());
        assertEquals(sensor2.getInformation(), sensor.getInformation());
        sensorRepository.delete(sensor2);
        boolean isPresent = sensorRepository.findById(id).isPresent();
        assertFalse(isPresent);
    }


    /**
     * Test find by name and sensor type.
     */
    @Test
    public void testFindByNameAndSensorType() {
        Page page = sensorRepository.findByNameAndSensorType(FIND_SENSOR_NAME1, SensorType.PARKING, null);
        Sensor firstSensor = (Sensor) page.get().findFirst().get();
        assertEquals(firstSensor.getName(), FIND_SENSOR_NAME1);
        assertEquals(firstSensor.getSensorType(), SensorType.PARKING);
        page = sensorRepository.findByNameAndSensorType(FIND_SENSOR_NAME2, SensorType.TEMPERATURE, null);
        firstSensor = (Sensor) page.get().findFirst().get();
        assertEquals(firstSensor.getName(), FIND_SENSOR_NAME2);
        assertEquals(firstSensor.getSensorType(), SensorType.TEMPERATURE);
    }

}