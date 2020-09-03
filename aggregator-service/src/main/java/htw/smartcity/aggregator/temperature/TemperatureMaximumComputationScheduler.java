package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorRepository;
import htw.smartcity.aggregator.sensor.SensorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class TemperatureMaximumComputationScheduler
{
    @Autowired
    TemperatureRepository temperatureRepository;
    @Autowired
    TemperatureMaximumRepository temperatureMaximumRepository;
    @Autowired
    SensorRepository sensorRepository;

    @Scheduled (cron="0 1 * * *")
    private void computeDaily()
    {
        LocalDateTime end = LocalDateTime.now().minusHours(1).minusSeconds(1);
        LocalDateTime start = end.minusHours(1).minusDays(1);
        computeMaximumForDay(start, end);
    }

    public void computeMaximumForDay(LocalDateTime start, LocalDateTime end){
        try{
            List<Sensor> sensors = sensorRepository.findBySensorType(SensorType.TEMPERATURE);
            for(Sensor sensor : sensors){
                List<Temperature> temperatures = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(sensor.getId(), start, end);
                Optional<Temperature> maxValue = temperatures.stream().max(Comparator.comparing(Temperature::getValue));
                if(maxValue.isPresent()) {
                    TemperatureAverage maximum = new TemperatureAverage();
                    maximum.setSensor(sensor);
                    maximum.setValue(maxValue.get().getValue());
                    temperatureMaximumRepository.save(maximum);
                }
            }
        }catch(Exception e)
        {

        }
    }

    @Scheduled (cron="0 1 */7 * *")
    public void computeWeekly()
    {
        
    }

    @Scheduled (cron="0 1 1 * *")
    public void computeMonthly()
    {

    }
}
