package htw.smartcity.aggregator.base;

import htw.smartcity.aggregator.util.ConfigProperties;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.format.DateTimeFormatter.*;

public class ExceptionManager {
    private int delay = 60*60*1000;
    private int period = 60*60*1000;
    private List<MailException> mailExpcetions;
    private Timer timer;

    private static ExceptionManager instance;
    public static ExceptionManager getInstance() {
        if(instance == null)
            instance = new ExceptionManager();
        return instance;
    }

    private ExceptionManager(){
        try {
            delay = Integer.parseInt(ConfigProperties.MAIL_SEND_PERIOD)*1000;
            period = delay;
        }catch (NumberFormatException ne) {
            ne.printStackTrace();
        }
        mailExpcetions = Collections.synchronizedList(new ArrayList<>());
        timer = new Timer();
        timer.scheduleAtFixedRate(new ExceptionManagerTask(), delay, period);
    }

    public class ExceptionManagerTask extends TimerTask {

        @Override
        public void run() {
            if(mailExpcetions.size() > 0) {
                DateTimeFormatter dtf = ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime start = now.minus(Duration.ofMillis(period));
                String nowFormatted = dtf.format(now);
                String startFormatted = dtf.format(start);
                StringBuilder sb = new StringBuilder("Errors between ")
                        .append(startFormatted)
                        .append(" and ")
                        .append(nowFormatted)
                        .append(":\n\n");
                int errorCount = 0;
                synchronized (this) {
                    mailExpcetions.stream().forEach(e -> {
                        sb.append(e + "\n\n");
                    });
                    errorCount = mailExpcetions.size();
                    mailExpcetions.clear();
                }
                String subject = String.format("%d errors during AggregatorService execution", errorCount);
                String text = sb.toString();
                SendMailHelper.sendMail(subject, text);
            }
        }
    }

    public void MQTTConfigurationFailed(){
        mailExpcetions.add(new MailException(LogException.MQTT_CONFIGURATION_FAILED));
    }

    public void MQTTSubscriptionFailed(String topic){
        MailException mailException = new MailException(LogException.MQTT_SUBSCRIPTION_FAILED);
        mailException.addAdditionalInfos("Tried subscribing to Topic: " + topic);
        mailExpcetions.add(mailException);

    }

    public void MQTTSensorPersistenceFailed(String sensorName){
        MailException mailException = new MailException(LogException.MQTT_SENSOR_PERSISTENCE_FAILED);
        mailException.addAdditionalInfos("Sensor name: " + sensorName);
        mailExpcetions.add(mailException);
    }

    public void MQTTAirQualityPersistenceFailed(String sensorName, String msg){
        MailException mailException = new MailException(LogException.MQTT_AIR_QUALITY_PERSISTENCE_FAILED);
        mailException.addAdditionalInfos("Sensor Type: Air Quality Sensor");
        mailException.addAdditionalInfos("Sensor name: " + sensorName);
        mailException.addAdditionalInfos("Message: " + msg);
        mailExpcetions.add(mailException);
    }

    //can we do sth generic?
    private void addMailException(LogException type, List<String> additionalInfos){
        MailException mailException = new MailException(type);
        if(additionalInfos != null)
            additionalInfos.forEach(a -> mailException.addAdditionalInfos(a));
    }

}
