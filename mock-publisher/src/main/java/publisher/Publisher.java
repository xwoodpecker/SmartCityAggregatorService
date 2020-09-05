package publisher;

import agents.Agent;
import agents.AirQualityAgent;
import agents.ParkingAgent;
import agents.TemperatureAgent;
import util.ConfigProperties;
import util.Utils;
import org.eclipse.paho.client.mqttv3.*;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Random;

public class Publisher {

    public static final String TOPIC_AIRQUALITY = ConfigProperties.TOPIC + "/airQuality/sensor1";
    public static final String TOPIC_TEMPERATURE_SENSOR1 = ConfigProperties.TOPIC + "/temperature/sensor1";
    public static final String TOPIC_TEMPERATURE_SENSOR2 = ConfigProperties.TOPIC + "/temperature/sensor2";
    public static final String TOPIC_PARKING_SENSOR1 = ConfigProperties.TOPIC + "/parking/group1/sensor1";
    public static final String TOPIC_PARKING_SENSOR2 = ConfigProperties.TOPIC + "/parking/group2/sensor1";
    public static final String TOPIC_PARKING_SENSOR3 = ConfigProperties.TOPIC + "/parking/group2/sensor2";
    public static final String TOPIC_PARKING_SENSOR4 = ConfigProperties.TOPIC + "/parking/group2/sensor3";
    public static final String TOPIC_PARKING_SENSOR5 = ConfigProperties.TOPIC + "/parking/group2/sensor4";

    private MqttClient client;
    private Random random;

    public Publisher() {

        random = new Random();
        String clientId = Utils.getMacAddress() + "-pub";
        try {
            client = new MqttClient(ConfigProperties.BROKER, clientId);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void start() {

        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setWill(client.getTopic(ConfigProperties.TOPIC), "I just disconnected. :-(".getBytes(), 0, false);

            options.setUserName(ConfigProperties.USERNAME);
            options.setPassword(ConfigProperties.PASSWORD.toCharArray());
            options.setSocketFactory(Utils.getSocketFactoryForCaCertificate(ConfigProperties.CERTIFICATE));

            client.connect(options);

            ArrayList<Agent> agents = new ArrayList<>();
            agents.add(new TemperatureAgent(this, "Sensor1"));
            agents.add(new TemperatureAgent(this, "Sensor2"));
            agents.add(new TemperatureAgent(this, "Sensor3"));
            agents.add(new AirQualityAgent(this, "Sensor1"));
            agents.add(new AirQualityAgent(this, "Sensor2"));
            agents.add(new AirQualityAgent(this, "Sensor3"));
            agents.add(new ParkingAgent(this, "Sensor1", "group1"));
            agents.add(new ParkingAgent(this, "Sensor2", "group1"));
            agents.add(new ParkingAgent(this, "Sensor1", "group2"));
            agents.add(new ParkingAgent(this, "Sensor2", "group2"));

            agents.forEach(Agent::start);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void publishTemperature(String topic, Double temperature) throws MqttException {
        final MqttTopic mqttTopic = client.getTopic(topic);
        mqttTopic.publish(new MqttMessage(temperature.toString().getBytes()));
        System.out.println("Published data. Topic: " + mqttTopic.getName() + "  Message: " + temperature.toString());
    }

    public void publishAirquality(String topic, Integer airquality) throws MqttException {
        final MqttTopic mqttTopic = client.getTopic(topic);
        mqttTopic.publish(new MqttMessage(airquality.toString().getBytes()));
        System.out.println("Published data. Topic: " + mqttTopic.getName() + "  Message: " + airquality.toString());
    }

    public void publishParking(String topic, boolean parking) throws MqttException {
        final MqttTopic mqttTopic = client.getTopic(topic);
        mqttTopic.publish(new MqttMessage(String.valueOf(parking).getBytes()));
        System.out.println("Published data. Topic: " + mqttTopic.getName() + "  Message: " + parking);
    }


    public static void main(String... args) {
        final Publisher publisher = new Publisher();
        publisher.start();
    }
}
