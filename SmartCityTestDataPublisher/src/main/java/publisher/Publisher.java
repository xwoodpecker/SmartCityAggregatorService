package publisher;

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
import java.util.Random;

public class Publisher {

    public static final String TOPIC_AIRQUALITY = ConfigProperties.TOPIC + "/airquality/sensor1";
    public static final String TOPIC_TEMPERATURE_SENSOR1 = ConfigProperties.TOPIC + "/temperature/sensor1";
    public static final String TOPIC_TEMPERATURE_SENSOR2 = ConfigProperties.TOPIC + "/temperature/sensor2";
    public static final String TOPIC_PARKING_SENSOR1 = ConfigProperties.TOPIC + "/parking/sensor1";
    public static final String TOPIC_PARKING_SENSOR2 = ConfigProperties.TOPIC + "/parking/sensor2";
    public static final String TOPIC_PARKING_SENSOR3 = ConfigProperties.TOPIC + "/parking/sensor3";

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

            while (true) {

                publishAirquality();
                Thread.sleep(500);
                publishTemperature(TOPIC_TEMPERATURE_SENSOR1);
                Thread.sleep(500);
                publishTemperature(TOPIC_TEMPERATURE_SENSOR2);
                Thread.sleep(500);
                publishParking(TOPIC_PARKING_SENSOR1);
                Thread.sleep(500);
                publishParking(TOPIC_PARKING_SENSOR2);
                Thread.sleep(500);
                publishParking(TOPIC_PARKING_SENSOR3);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void publishTemperature(String topic) throws MqttException {
        final MqttTopic temperatureTopic = client.getTopic(topic);

        final int temperature = random.nextInt(39);
        final String temperatureString = temperature + "°C";

        temperatureTopic.publish(new MqttMessage(temperatureString.getBytes()));

        System.out.println("Published data. Topic: " + temperatureTopic.getName() + "  Message: " + temperatureString);
    }

    private void publishAirquality() throws MqttException {
        final MqttTopic airqualityTopic = client.getTopic(TOPIC_AIRQUALITY);

        final int airquality = random.nextInt(79)+20;
        final String airqualityString = airquality + "%";

        airqualityTopic.publish(new MqttMessage(airqualityString.getBytes()));

        System.out.println("Published data. Topic: " + airqualityTopic.getName() + "   Message: " + airqualityString);
    }

    private void publishParking(String topic) throws MqttException {
        final MqttTopic parkingTopic = client.getTopic(topic);

        final boolean parking = random.nextBoolean();
        final String parkingString = String.valueOf(parking);

        parkingTopic.publish(new MqttMessage(parkingString.getBytes()));

        System.out.println("Published data. Topic: " + parkingTopic.getName() + "   Message: " + parkingString);
    }


    public static void main(String... args) {
        final Publisher publisher = new Publisher();
        publisher.start();
    }
}
