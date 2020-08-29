package htw.smartcity.aggregator.base;

import org.junit.Test;

public class ExceptionManagerTest {
    ExceptionManager exceptionManager = ExceptionManager.getInstance();

    @Test
    public void testExceptionManager() {

        try {
            exceptionManager.MQTTConfigurationFailed();
            Thread.sleep(500);
            exceptionManager.MQTTSubscriptionFailed("kartoffelsuppe");
            Thread.sleep(500);
            exceptionManager.MQTTSubscriptionFailed("hackbraten");
            Thread.sleep(500);
            exceptionManager.MQTTSensorPersistenceFailed("sensor3derdritte");
            Thread.sleep(500);
            exceptionManager.MQTTSensorPersistenceFailed("sensordensnichtgibt");
            Thread.sleep(500);
            exceptionManager.MQTTAirQualityPersistenceFailed("sensordensnichtgibt", "sinnfrei");
            Thread.sleep(500);
            exceptionManager.MQTTAirQualityPersistenceFailed("sensordensnichtgibt", "sinnfreimalzwei");
            Thread.sleep(90*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
