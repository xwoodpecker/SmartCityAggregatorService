package htw.smartcity.aggregator.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ExceptionManagerTest {

    @Autowired
    ExceptionManager exceptionManager;

    /**
     * Tests to debug through the computation scheduler
     * (no asserts, just for looking into the functions)
     */
    @Test
    public void testExceptionManager()
    {
        for(int i = 0; i < 10; i++)
            exceptionManager.MQTTConfigurationFailed();
    }

}
