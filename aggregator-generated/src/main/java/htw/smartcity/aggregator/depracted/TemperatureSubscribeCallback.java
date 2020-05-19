package htw.smartcity.aggregator.depracted;

import htw.smartcity.aggregator.temperature.Temperature;
import htw.smartcity.aggregator.util.ConfigProperties;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Timestamp;


public class TemperatureSubscribeCallback implements MqttCallback {


    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {

        System.out.println("Message arrived. Topic: " + topic + "  Message: " + message.toString());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Temperature temperature = new Temperature(timestamp.toString(), message.toString());


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();
        try{
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(temperature);
            transaction.commit();
        }
        finally {
            em.close();
        }

        
        if (ConfigProperties.TOPIC.equals(topic)) {
            System.err.println("Sensor disconnected!");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}
