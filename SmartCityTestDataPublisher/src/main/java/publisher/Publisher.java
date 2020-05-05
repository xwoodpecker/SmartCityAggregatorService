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

    public static final String TOPIC_HUMIDITY = ConfigProperties.TOPIC + "/humidity";
    public static final String TOPIC_TEMPERATURE = ConfigProperties.TOPIC + "/temperature";

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

                publishHumidity();
                Thread.sleep(500);
                publishTemperature();
                Thread.sleep(500);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void publishTemperature() throws MqttException {
        final MqttTopic temperatureTopic = client.getTopic(TOPIC_TEMPERATURE);

        final int temperature = random.nextInt(39);
        final String temperatureString = temperature + "°C";

        temperatureTopic.publish(new MqttMessage(temperatureString.getBytes()));

        System.out.println("Published data. Topic: " + temperatureTopic.getName() + "  Message: " + temperatureString);
    }

    private void publishHumidity() throws MqttException {
        final MqttTopic humidityTopic = client.getTopic(TOPIC_HUMIDITY);

        final int humidity = random.nextInt(79)+20;
        final String humidityString = humidity + "%";

        humidityTopic.publish(new MqttMessage(humidityString.getBytes()));

        System.out.println("Published data. Topic: " + humidityTopic.getName() + "   Message: " + humidityString);
    }

    public static void main(String... args) {
        final Publisher publisher = new Publisher();
        publisher.start();
    }
}
