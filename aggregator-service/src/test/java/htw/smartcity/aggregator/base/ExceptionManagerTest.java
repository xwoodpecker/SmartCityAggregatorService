package htw.smartcity.aggregator.base;

import htw.smartcity.aggregator.sensor.Sensor;
import org.junit.Test;

/**
 * The type Exception manager test.
 */
public class ExceptionManagerTest {
    /**
     * The Exception manager.
     */
    ExceptionManager exceptionManager = ExceptionManager.getInstance();

    /**
     * Test exception manager.
     */
    @Test
    public void testExceptionManager() {

        /**try {
            exceptionManager.MQTTConfigurationFailed();
            Thread.sleep(500);
            exceptionManager.MQTTSubscriptionFailed("kartoffelsuppe");
            Thread.sleep(500);
            exceptionManager.MQTTSubscriptionFailed("hackbraten");
            Thread.sleep(500);
            exceptionManager.MQTTSensorPersistenceFailed("sensor3derdritte", Sensor.SensorType.AIR_QUALITY);
            Thread.sleep(500);
            exceptionManager.MQTTSensorPersistenceFailed("sensordensnichtgibt", Sensor.SensorType.AIR_QUALITY);
            Thread.sleep(500);
            exceptionManager.MQTTAirQualityPersistenceFailed("sensordensnichtgibt", "sinnfrei");
            Thread.sleep(500);
            exceptionManager.MQTTAirQualityPersistenceFailed("sensordensnichtgibt", "sinnfreimalzwei");
            Thread.sleep(90*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }**/
    }
}
