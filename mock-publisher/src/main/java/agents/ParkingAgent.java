package agents;

import org.eclipse.paho.client.mqttv3.MqttException;
import publisher.Publisher;
import util.ConfigProperties;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ParkingAgent extends Agent {
    private final String TOPIC_PREFIX = ConfigProperties.TOPIC + "/parking/";
    private String groupName;
    private boolean currentStatus;
    private long nextChange;

    public ParkingAgent(Publisher publisher, String sensorName, String groupName){
        super(publisher, sensorName);
        this.groupName = groupName;

        this.currentStatus = random.nextBoolean();
        this.nextChange = System.currentTimeMillis();
    }

    public void start(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try {
                if(System.currentTimeMillis() > nextChange){
                    currentStatus = random.nextBoolean();
                    nextChange = System.currentTimeMillis() + random.nextInt(3600000 );
                }
                publisher.publishParking(TOPIC_PREFIX + groupName + "/" + sensorName, currentStatus);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }, initialDelay, 10000, TimeUnit.MILLISECONDS);
    }
}
