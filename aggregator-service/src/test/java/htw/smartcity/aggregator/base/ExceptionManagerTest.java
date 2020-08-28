package htw.smartcity.aggregator.base;

import org.junit.Test;

public class ExceptionManagerTest {
    ExceptionManager exceptionManager = new ExceptionManager();

    @Test
    public void testExceptionManager() {
        exceptionManager.MQTTConfigurationFailed();
        exceptionManager.MQTTConfigurationFailed();
        exceptionManager.MQTTConfigurationFailed();
        exceptionManager.MQTTConfigurationFailed();
        exceptionManager.MQTTConfigurationFailed();
        exceptionManager.MQTTConfigurationFailed();
        exceptionManager.MQTTConfigurationFailed();
        exceptionManager.MQTTConfigurationFailed();
        try {
            Thread.sleep(60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
