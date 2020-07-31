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
import org.springframework.data.domain.Pageable;
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
public class ParkingGroupCounterRepositoryTests {

    @Autowired
    private ParkingGroupCounterRepository parkingGroupCounterRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Test
    public void testParkingGroupCounterEntities() {

    }


    @Test
    public void testFindFirstByParkingGroupOrderByTimeDesc() {

    }


    @Test
    public void testFindFirstByParkingGroupIdOrderByTimeDesc() {

    }

    @Test
    public void testFindAllByParkingGroupId() {

    }
}