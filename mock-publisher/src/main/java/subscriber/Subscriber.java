package subscriber;

import util.ConfigProperties;
import util.Utils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * The type Subscriber.
 */
public class Subscriber {

    /**
     * The constant ALL_SUBTOPICS.
     */
    public static final String ALL_SUBTOPICS = ConfigProperties.TOPIC + "/#";

    /**
     * The Client id.
     */
    String clientId = Utils.getMacAddress() + "-sub";
    private MqttClient mqttClient;

    /**
     * Instantiates a new Subscriber.
     */
    public Subscriber() {

        try {
            mqttClient = new MqttClient(ConfigProperties.BROKER, clientId);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Start.
     */
    public void start() {

        try {
            mqttClient.setCallback(new SubscribeCallback());

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

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String... args) {
        final Subscriber subscriber = new Subscriber();
        subscriber.start();
    }



}
