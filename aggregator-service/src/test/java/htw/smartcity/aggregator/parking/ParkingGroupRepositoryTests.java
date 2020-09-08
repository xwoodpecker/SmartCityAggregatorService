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

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * The type Parking group repository tests.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ParkingGroupRepositoryTests {

    private static final Long PARKING_SENSOR1_ID = 3L;
    private static final Long PARKING_SENSOR2_ID = 4L;
    private static final Long PARKING_GROUP1_ID = 1L;
    private static final Long PARKING_GROUP2_ID = 2L;

    @Autowired
    private ParkingGroupRepository parkingGroupRepository;

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Test parking group entities.
     */
    @Test
    public void testParkingGroupEntities() {
        Sensor sensor1 = sensorRepository.findById(PARKING_SENSOR1_ID).get();
        Sensor sensor2 = sensorRepository.findById(PARKING_SENSOR2_ID).get();
        ParkingGroup parkingGroup = new ParkingGroup("loremipsum", "myparkinggroup1");
        parkingGroup.addToSensors(sensor1);
        parkingGroup.addToSensors(sensor2);
        Long id = parkingGroupRepository.save(parkingGroup).getId();
        ParkingGroup parkingGroup1 = parkingGroupRepository.findById(id).get();
        assertNotNull(parkingGroup1);
        assertEquals(parkingGroup1.getName(), parkingGroup.getName());
        assertEquals(parkingGroup1.getInformation(), parkingGroup.getInformation());
        assert(parkingGroup.getSensors().contains(sensor1));
        assert(parkingGroup.getSensors().contains(sensor2));
        assertEquals(parkingGroup.getSensors().size(), 2);
        parkingGroupRepository.delete(parkingGroup);
        boolean isPresent = parkingGroupRepository.findById(id).isPresent();
        assertFalse(isPresent);
    }

    /**
     * Test find by name.
     */
    @Test
    public void testFindByName() {
        List<ParkingGroup> parkingGroups = parkingGroupRepository.findByName("parking_group1");
        assertEquals(parkingGroups.size(), 1);
        ParkingGroup parkingGroup = parkingGroups.get(0);
        assertEquals((long)parkingGroup.getId(), 1L);
        assertEquals(parkingGroup.getName(), "parking_group1");
        assertNull(parkingGroup.getInformation());
        int numberOfSensors = parkingGroup.getSensors().size();
        assertEquals(numberOfSensors, 4);
    }

    /**
     * Test find parking sensors by group id.
     */
    @Test
    public void testFindParkingSensorsByGroupId() {
        Page page1 = parkingGroupRepository.findParkingSensorsByGroupId(PARKING_GROUP1_ID, null);
        Page page2 = parkingGroupRepository.findParkingSensorsByGroupId(PARKING_GROUP2_ID, null);
        List<Sensor> sensorList1 = (List<Sensor>) page1.get().collect(Collectors.toList());
        List<Sensor> sensorList2 = (List<Sensor>) page2.get().collect(Collectors.toList());
        assertEquals(sensorList1.size(), 4);
        assertEquals(sensorList2.size(), 1);
        Sensor sensor1Group1 = sensorList1.get(0);
        assertEquals(sensor1Group1.getName(), "parking_sensor1");
        Sensor sensor4Group1 = sensorList1.get(3);
        assertEquals(sensor4Group1.getName(), "parking_sensor4");
        Sensor sensor1Group2 = sensorList2.get(0);
        assertEquals(sensor1Group2.getName(), "parking_sensor5");

    }

}