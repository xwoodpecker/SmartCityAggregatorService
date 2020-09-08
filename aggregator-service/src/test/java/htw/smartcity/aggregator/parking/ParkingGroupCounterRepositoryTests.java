package htw.smartcity.aggregator.parking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

/**
 * The type Parking group counter repository tests.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ParkingGroupCounterRepositoryTests {

    private static final Long PARKING_GROUP1_ID = 1L;
    private static final Long PARKING_GROUP2_ID = 2L;
    private static final LocalDateTime LastTime = LocalDateTime.of(2020, Month.JULY, 23, 19, 41, 19);

    @Autowired
    private ParkingGroupRepository parkingGroupRepository;

    @Autowired
    private ParkingGroupCounterRepository parkingGroupCounterRepository;

    /**
     * Test parking group counter entities.
     */
    @Test
    public void testParkingGroupCounterEntities() {
        ParkingGroup parkingGroup = parkingGroupRepository.findById(PARKING_GROUP2_ID).get();
        ParkingGroupCounter parkingGroupCounter = new ParkingGroupCounter(LocalDateTime.now(), 2, 0, parkingGroup);
        Long id = parkingGroupCounterRepository.save(parkingGroupCounter).getId();
        ParkingGroupCounter parkingGroupCounter1 = parkingGroupCounterRepository.findById(id).get();
        assertNotNull(parkingGroupCounter1);
        assertEquals(parkingGroupCounter1.getFree(), parkingGroupCounter.getFree());
        assertEquals(parkingGroupCounter1.getUsed(), parkingGroupCounter.getUsed());
        assertEquals(parkingGroupCounter1.getTime(), parkingGroupCounter.getTime());
        assertEquals(parkingGroupCounter1.getParkingGroup().getId(), parkingGroup.getId());
        parkingGroupCounterRepository.delete(parkingGroupCounter);
        boolean isPresent = parkingGroupCounterRepository.findById(id).isPresent();
        assertFalse(isPresent);
    }


    /**
     * Test find first by parking group order by time desc.
     */
    @Test
    public void testFindFirstByParkingGroupOrderByTimeDesc() {
        ParkingGroup parkingGroup = parkingGroupRepository.findById(PARKING_GROUP1_ID).get();
        ParkingGroupCounter firstCounter = parkingGroupCounterRepository.findFirstByParkingGroupOrderByTimeDesc(parkingGroup);
        assertNotNull(firstCounter);
        assertEquals(firstCounter.getTime(), LastTime);
        int free = firstCounter.getFree();
        int used = firstCounter.getUsed();
        assertEquals(free, 3);
        assertEquals(used, 1);
        assertEquals(firstCounter.getParkingGroup().getId(), parkingGroup.getId());
    }


    /**
     * Test find first by parking group id order by time desc.
     */
    @Test
    public void testFindFirstByParkingGroupIdOrderByTimeDesc() {
        ParkingGroup parkingGroup = parkingGroupRepository.findById(PARKING_GROUP1_ID).get();
        ParkingGroupCounter firstCounter = parkingGroupCounterRepository.findFirstByParkingGroupIdOrderByTimeDesc(PARKING_GROUP1_ID);
        assertNotNull(firstCounter);
        assertEquals(firstCounter.getTime(), LastTime);
        int free = firstCounter.getFree();
        int used = firstCounter.getUsed();
        assertEquals(free, 3);
        assertEquals(used, 1);
        assertEquals(firstCounter.getParkingGroup().getId(), parkingGroup.getId());
    }

    /**
     * Test find all by parking group id.
     */
    @Test
    public void testFindAllByParkingGroupId() {
        int elementsSensor1 = parkingGroupCounterRepository.findAllByParkingGroupId(null, PARKING_GROUP1_ID).getNumberOfElements();
        int elementsSensor2 = parkingGroupCounterRepository.findAllByParkingGroupId(null, PARKING_GROUP2_ID).getNumberOfElements();
        assertEquals(elementsSensor1, 8);
        assertEquals(elementsSensor2, 0);
    }
}