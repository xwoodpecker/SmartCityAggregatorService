package htw.smartcity.aggregator.base;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.format.DateTimeFormatter.*;

public class ExceptionManager {
    private int delay = 0;//60*60*1000;
    private int period = 10*1000;//60*60*1000;
    private List<LogException> logExceptions;
    private Timer timer;

    public ExceptionManager(){
        logExceptions = Collections.synchronizedList(new ArrayList<>());
        timer = new Timer();
        timer.scheduleAtFixedRate(new ExceptionManagerTask(), delay, period);
    }

    public class ExceptionManagerTask extends TimerTask {

        @Override
        public void run() {
            DateTimeFormatter dtf = ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = now.minus(Duration.ofHours(1));
            String nowFormatted = dtf.format(now);
            String startFormatted = dtf.format(start);
            StringBuilder sb = new StringBuilder("Errors between ")
                    .append(startFormatted)
                    .append(" and ")
                    .append(nowFormatted)
                    .append(":");
            synchronized(this) {
                logExceptions.stream().forEach(e -> sb.append(e + "\n"));
                logExceptions.clear();
            }
            String text = sb.toString();
            System.out.println("hallo");
        }
    }

    public void MQTTConfigurationFailed(){
        logExceptions.add(LogException.MQTT_CONFIGURATION_FAILED);
    }

    public void MQTTSubscriptionFailed(){
        logExceptions.add(LogException.MQTT_SUBSCRIPTION_FAILED);
    }

    public void MQTTSensorPersistenceFailed(){
        logExceptions.add(LogException.MQTT_SENSOR_PERSISTENCE_FAILED);
    }

    public void MQTTAirQualityPersistenceFailed(){
        logExceptions.add(LogException.MQTT_AIR_QUALITY_PERSISTENCE_FAILED);
    }

}
