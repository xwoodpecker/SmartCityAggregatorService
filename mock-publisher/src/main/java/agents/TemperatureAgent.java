package agents;

import org.eclipse.paho.client.mqttv3.MqttException;
import publisher.Publisher;
import util.ConfigProperties;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The type Temperature agent.
 */
public class TemperatureAgent extends Agent {
    private final String TOPIC_PREFIX = ConfigProperties.TOPIC + "/temperature/";
    private double lastTemp;

    /**
     * Instantiates a new Temperature agent.
     *
     * @param publisher  the publisher
     * @param sensorName the sensor name
     */
    public TemperatureAgent(Publisher publisher, String sensorName){
        super(publisher, sensorName);

        this.lastTemp = 20 * random.nextDouble();
    }

    public void start(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try {
                Double nextTemp = lastTemp - 0.5 + random.nextDouble();
                publisher.publishTemperature(TOPIC_PREFIX + sensorName, nextTemp);
                lastTemp = nextTemp;
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }, initialDelay, 10000, TimeUnit.MILLISECONDS);
    }
}
