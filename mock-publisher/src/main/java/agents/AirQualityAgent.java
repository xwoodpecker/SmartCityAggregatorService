package agents;

import org.eclipse.paho.client.mqttv3.MqttException;
import publisher.Publisher;
import util.ConfigProperties;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The type Air quality agent.
 */
public class AirQualityAgent extends Agent {
    private final String TOPIC_PREFIX = ConfigProperties.TOPIC + "/airQuality/";
    private int lastAirQuality;

    /**
     * Instantiates a new Air quality agent.
     *
     * @param publisher  the publisher
     * @param sensorName the sensor name
     */
    public AirQualityAgent(Publisher publisher, String sensorName){
        super(publisher, sensorName);

        this.lastAirQuality = random.nextInt(250);
    }

    public void start(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try {
                int nextAirQuality = lastAirQuality - 5 + random.nextInt(10);
                publisher.publishAirquality(TOPIC_PREFIX + sensorName, nextAirQuality);
                lastAirQuality = nextAirQuality;
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }, initialDelay, 10000, TimeUnit.MILLISECONDS);
    }
}
