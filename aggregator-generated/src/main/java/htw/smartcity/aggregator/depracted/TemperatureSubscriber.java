package htw.smartcity.aggregator.depracted;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import htw.smartcity.aggregator.util.ConfigProperties;
import htw.smartcity.aggregator.util.Utils;


public class TemperatureSubscriber {

    public static final String ALL_SUBTOPICS = ConfigProperties.TOPIC + "/#";

    String clientId = Utils.getMacAddress() + "-sub";
    private MqttClient mqttClient;

    public TemperatureSubscriber() {

        try {
            mqttClient = new MqttClient(ConfigProperties.BROKER, clientId);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start() {

        try {
            mqttClient.setCallback(new TemperatureSubscribeCallback());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(ConfigProperties.USERNAME);
            options.setPassword(ConfigProperties.PASSWORD.toCharArray());
            options.setSocketFactory(Utils.getSocketFactoryForCaCertificate(ConfigProperties.CERTIFICATE));

            mqttClient.connect(options);

            //# = wildcard for all subtopics
            mqttClient.subscribe(ALL_SUBTOPICS);
            System.out.println("Subscribed to " + ALL_SUBTOPICS);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        final TemperatureSubscriber subscriber = new TemperatureSubscriber();
        subscriber.start();
    }



}
