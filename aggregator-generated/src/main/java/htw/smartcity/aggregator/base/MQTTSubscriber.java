package htw.smartcity.aggregator.base;

import htw.smartcity.aggregator.util.ConfigProperties;
import htw.smartcity.aggregator.util.Utils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.sql.Date;
import java.sql.Timestamp;




public abstract class MQTTSubscriber implements MqttCallback {

    private final String brokerUrl = ConfigProperties.BROKER;
    private final String userName = ConfigProperties.USERNAME;
    private final String password = ConfigProperties.PASSWORD;
    private final String certifcate = ConfigProperties.CERTIFICATE;
    private final String topic = ConfigProperties.TOPIC + getSubTopic();



    private MqttClient mqttClient = null;
    private MqttConnectOptions connectionOptions = null;
    private MemoryPersistence persistence = null;


    public MQTTSubscriber() {
        this.config();
        this.subscribeMessage(this.topic);
    }

    protected abstract String getSubTopic();


    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Date time = new Date(System.currentTimeMillis());
        String msg = message.toString();
        System.out.println("Message arrived. Topic: " + topic + "  Message: " + msg);
        persistMsg(time, msg);
    }

    protected abstract void persistMsg(Date time, String msg);

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }


    public void subscribeMessage(String topic) {
        try {
            this.mqttClient.subscribe(topic);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }


    protected void config() {
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            this.mqttClient = new MqttClient(brokerUrl, getClientId(), persistence);
            this.connectionOptions.setCleanSession(true);
            if (this.password != null) {
                this.connectionOptions.setPassword(this.password.toCharArray());
            }
            if (this.userName != null) {
                this.connectionOptions.setUserName(this.userName);
            }
            if (this.certifcate != null) {
                this.connectionOptions.setSocketFactory(Utils.getSocketFactoryForCaCertificate(certifcate));
            }
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
        } catch (MqttException me) {
            me.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract String getClientId();
}


