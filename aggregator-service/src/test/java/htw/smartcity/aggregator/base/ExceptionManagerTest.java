package htw.smartcity.aggregator.base;

import htw.smartcity.aggregator.sensor.SensorType;
import htw.smartcity.aggregator.temperatureaggregate.TemperatureAggregateComputationScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceptionManagerTest {

    @Autowired
    ExceptionManager exceptionManager;

    /**
     * Tests to debug through the computation scheduler
     * (no asserts, just for looking into the functions)
     */
    @Test
    public void testSendMails() {
        try {
            for (int i = 0; i < 10; i++)
                exceptionManager.MQTTConfigurationFailed();
            Thread.sleep(2*60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testVariousErrors() {
        try {
            exceptionManager.MQTTSubscriptionFailed("testTopic");
            exceptionManager.MQTTSensorPersistenceFailed("testSens1", SensorType.TEMPERATURE);
            exceptionManager.MQTTSensorPersistenceFailed("testSens2", SensorType.AIR_QUALITY);
            exceptionManager.MQTTSensorPersistenceFailed("testSens2", SensorType.PARKING);
            exceptionManager.MQTTAirQualityPersistenceFailed("testAir", "testMsg");
            exceptionManager.MQTTTemperaturePersistenceFailed("testTemp", "testMsg");
            exceptionManager.MQTTParkingGroupPersistenceFailed("testGrp");
            exceptionManager.MQTTParkingPersistenceFailed("testPark", "testMsg");
            exceptionManager.MQTTParkingGroupCounterPersistenceFailed("testParkCount", "testMsg");
            exceptionManager.MonthlyAggregationFailed();
            exceptionManager.WeeklyAggregationFailed();
            exceptionManager.DailyAggregationFailed();
            Thread.sleep(2*60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testTimeBetween() {
        try {

            for (int i = 0; i < 10; i++)
                exceptionManager.MQTTConfigurationFailed();
            Thread.sleep(2*60*1000);


            for (int i = 0; i < 10; i++)
                exceptionManager.MQTTConfigurationFailed();
            Thread.sleep(2*60*1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
