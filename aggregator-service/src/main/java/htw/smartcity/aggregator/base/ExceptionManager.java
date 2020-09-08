package htw.smartcity.aggregator.base;

import htw.smartcity.aggregator.security.UserRepository;
import htw.smartcity.aggregator.sensor.SensorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.format.DateTimeFormatter.*;

/**
 * The type Exception manager.
 */
@Component
@Scope(value = "singleton")
public class ExceptionManager {
    private int delay = 30*1000;
    private int period = 60*60*1000;
    private List<MailException> mailExpcetions;
    private Timer timer;

    @Autowired
    private UserRepository userRepository;

    private ExceptionManager(){
        try {
            period = Integer.parseInt(ConfigProperties.MAIL_SEND_PERIOD)*60*1000;
        }catch (NumberFormatException ne) {
            ne.printStackTrace();
        }
        mailExpcetions = Collections.synchronizedList(new ArrayList<>());
        timer = new Timer();
        timer.scheduleAtFixedRate(new ExceptionManagerTask(), delay, period);
    }

    /**
     * The type Exception manager task.
     */
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
                String mailList = null;
                try {
                    Optional<String> optional = userRepository.findAdmins().stream().map(a -> a.getEmail()).reduce((a1, a2) -> a1 + "," + a2);
                    if(optional.isPresent())
                        mailList = optional.get();
                }catch(Exception e) {
                    e.printStackTrace();
                }
                SendMailHelper.sendMail(subject, text, mailList);
            }
        }
    }

    /**
     * Mqtt configuration failed.
     */
    public void MQTTConfigurationFailed(){
        mailExpcetions.add(new MailException(LogException.MQTT_CONFIGURATION_FAILED));
    }

    /**
     * Mqtt subscription failed.
     *
     * @param topic the topic
     */
    public void MQTTSubscriptionFailed(String topic){
        MailException mailException = new MailException(LogException.MQTT_SUBSCRIPTION_FAILED);
        mailException.addAdditionalInfos("Tried subscribing to topic: " + topic);
        mailExpcetions.add(mailException);

    }

    /**
     * Mqtt sensor persistence failed.
     *
     * @param sensorName the sensor name
     * @param sensorType the sensor type
     */
    public void MQTTSensorPersistenceFailed(String sensorName, SensorType sensorType){
        MailException mailException = new MailException(LogException.MQTT_SENSOR_PERSISTENCE_FAILED);
        mailException.addAdditionalInfos("Sensor type: " + sensorType);
        mailException.addAdditionalInfos("Sensor name: " + sensorName);
        mailExpcetions.add(mailException);
    }

    /**
     * Mqtt air quality persistence failed.
     *
     * @param sensorName the sensor name
     * @param msg        the msg
     */
    public void MQTTAirQualityPersistenceFailed(String sensorName, String msg){
        MailException mailException = new MailException(LogException.MQTT_AIR_QUALITY_PERSISTENCE_FAILED);
        mailException.addAdditionalInfos("Sensor type: " + SensorType.AIR_QUALITY);
        MQTTMeasurementPersistenceFailed(mailException, sensorName, msg);
    }

    /**
     * Mqtt temperature persistence failed.
     *
     * @param sensorName the sensor name
     * @param msg        the msg
     */
    public void MQTTTemperaturePersistenceFailed(String sensorName, String msg){
        MailException mailException = new MailException(LogException.MQTT_TEMPERATURE_PERSISTENCE_FAILED);
        mailException.addAdditionalInfos("Sensor type: " + SensorType.TEMPERATURE);
        MQTTMeasurementPersistenceFailed(mailException, sensorName, msg);
    }

    /**
     * Mqtt parking persistence failed.
     *
     * @param sensorName the sensor name
     * @param msg        the msg
     */
    public void MQTTParkingPersistenceFailed(String sensorName, String msg){
        MailException mailException = new MailException(LogException.MQTT_PARKING_PERSISTENCE_FAILED);
        mailException.addAdditionalInfos("Sensor type: " + SensorType.PARKING);
        MQTTMeasurementPersistenceFailed(mailException, sensorName, msg);
    }

    /**
     * Mqtt parking group persistence failed.
     *
     * @param groupName the group name
     */
    public void MQTTParkingGroupPersistenceFailed(String groupName){
        MailException mailException = new MailException(LogException.MQTT_PARKING_GROUP_PERSISTENCE_FAILED);
        mailException.addAdditionalInfos("Group name: " + groupName);
        mailExpcetions.add(mailException);
    }

    /**
     * Mqtt parking group counter persistence failed.
     *
     * @param groupName   the group name
     * @param information the information
     */
    public void MQTTParkingGroupCounterPersistenceFailed(String groupName, String information){
        MailException mailException = new MailException(LogException.MQTT_PARKING_GROUP_COUNTER_PERSISTENCE_FAILED);
        mailException.addAdditionalInfos("Group name: " + groupName);
        mailException.addAdditionalInfos("Information: " + information);
        mailExpcetions.add(mailException);
    }

    private void MQTTMeasurementPersistenceFailed(MailException mailException, String sensorName, String msg){
        mailException.addAdditionalInfos("Sensor name: " + sensorName);
        mailException.addAdditionalInfos("Message: " + msg);
        mailExpcetions.add(mailException);
    }

    /**
     * Daily aggregation failed.
     */
    public void DailyAggregationFailed() {
        mailExpcetions.add(new MailException(LogException.DAILY_AGGREGATION_FAILED));
    }

    /**
     * Weekly aggregation failed.
     */
    public void WeeklyAggregationFailed() {
        mailExpcetions.add(new MailException(LogException.WEEKLY_AGGREGATION_FAILED));
    }

    /**
     * Monthly aggregation failed.
     */
    public void MonthlyAggregationFailed() {
        mailExpcetions.add(new MailException(LogException.MONTHLY_AGGREGATION_FAILED));
    }

    //can we do sth generic?
    private void addMailException(LogException type, List<String> additionalInfos){
        MailException mailException = new MailException(type);
        if(additionalInfos != null)
            additionalInfos.forEach(a -> mailException.addAdditionalInfos(a));
    }

}
