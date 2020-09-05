package htw.smartcity.aggregator.parking;

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
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The type Parking repository tests.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ParkingRepositoryTests {

    private static final Long PARKING_SENSOR1_ID = 3L;
    private static final Long PARKING_SENSOR2_ID = 4L;
    private static final LocalDateTime TimeFrom = LocalDateTime.of(2020, Month.JULY, 23, 12, 00, 0);
    private static final LocalDateTime TimeTo = LocalDateTime.of(2020, Month.JULY, 23, 18, 00, 0);
    private static final LocalDateTime LastTime = LocalDateTime.of(2020, Month.JULY, 23, 19, 41, 19);


    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Test parking entities.
     */
    @Test
    public void testParkingEntities() {
        Sensor sensor = sensorRepository.findById(PARKING_SENSOR1_ID).get();
        Parking parking = new Parking(OffsetDateTime.now(), sensor, false);
        Long id = parkingRepository.save(parking).getId();
        Parking parking2 = parkingRepository.findById(id).get();
        assertNotNull(parking2);
        assertEquals(parking2.getValue(), parking.getValue());
        assertEquals(parking2.getTime(), parking.getTime());
        assertEquals(parking2.getSensor().getId(), parking.getSensor().getId());
        parkingRepository.delete(parking2);
        boolean isPresent = parkingRepository.findById(id).isPresent();
        assertEquals(isPresent, false);
    }

    /**
     * Test find first by sensor order by time desc.
     */
    @Test
    public void testFindFirstBySensorOrderByTimeDesc(){
        Sensor sensor1 = sensorRepository.findById(PARKING_SENSOR2_ID).get();
        Parking firstParking = parkingRepository.findFirstBySensorOrderByTimeDesc(sensor1);
        assertEquals(firstParking.getValue(), false);
        assertEquals(firstParking.getTime(), LastTime);
        assertEquals(firstParking.getSensor(), sensor1);
    }


    /**
     * Test find first by sensor id order by time desc.
     */
    @Test
    public void testFindFirstBySensorIdOrderByTimeDesc(){
        Sensor sensor1 = sensorRepository.findById(PARKING_SENSOR2_ID).get();
        Parking firstParking = parkingRepository.findFirstBySensorIdOrderByTimeDesc(PARKING_SENSOR2_ID);
        assertEquals(firstParking.getValue(), false);
        assertEquals(firstParking.getTime(), LastTime);
        assertEquals(firstParking.getSensor(), sensor1);
    }

    /**
     * Test find parking by sensor id.
     */
    @Test
    public void testFindParkingBySensorId() {
        int elementsSensor1 = parkingRepository.findParkingsBySensorId(PARKING_SENSOR1_ID, null).getNumberOfElements();
        int elementsSensor2 = parkingRepository.findParkingsBySensorId(PARKING_SENSOR2_ID, null).getNumberOfElements();
        assertEquals(elementsSensor1, 2);
        assertEquals(elementsSensor2, 6);
    }

    /**
     * Test find parking by time before and time after and sensor id.
     */
    @Test
    public void testFindParkingByTimeBeforeAndTimeAfterAndSensorId() {
        Page parkingByTimeBeforeAndTimeAfterAndSensorId = parkingRepository.findParkingsByTimeAfterAndTimeBeforeAndSensorId(TimeFrom, TimeTo, PARKING_SENSOR2_ID, null);
        List<Parking> parkingList = (List<Parking>) parkingByTimeBeforeAndTimeAfterAndSensorId.get().collect(Collectors.toList());
        int numberOfElements = parkingByTimeBeforeAndTimeAfterAndSensorId.getNumberOfElements();
        assertEquals(numberOfElements, 3);
        boolean valueParking1 = parkingList.get(0).getValue();
        boolean valueParking2 = parkingList.get(1).getValue();
        boolean valueParking3 = parkingList.get(2).getValue();
        assertEquals(valueParking1, true);
        assertEquals(valueParking2, false);
        assertEquals(valueParking3, true);

    }

}