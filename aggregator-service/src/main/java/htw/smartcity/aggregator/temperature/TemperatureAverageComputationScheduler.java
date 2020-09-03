package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorRepository;
import htw.smartcity.aggregator.sensor.SensorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class TemperatureAverageComputationScheduler
{
    @Autowired
    SensorRepository sensorRepository;

    @Autowired
    TemperatureRepository temperatureRepository;

    @Autowired
    TemperatureAverageRepository temperatureAverageRepository;

    @Scheduled (cron="0 1 * * *")
    public void computeDaily()
    {
        List<Sensor> sensors = sensorRepository.findBySensorType(SensorType.TEMPERATURE);
        double sum = 0;
        int count = 0;

        for(Sensor s : sensors)
        {
            List<Temperature> tList = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(s.getId(),
                                                                           LocalDateTime.now().minusDays(1).with(LocalTime.MIN),
                                                                           LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
            sum = 0;
            count = 0;
            for(Temperature t : tList)
            {
                sum += t.getValue();
                count++;
            }
            TemperatureAverageDaily temperatureAverageDaily = new TemperatureAverageDaily();
            temperatureAverageDaily.setValue(sum/count);
            temperatureAverageDaily.setSensor(sensorRepository.getOne(s.getId()));
            temperatureAverageDaily.setDate(LocalDateTime.now().minusDays(1));

            temperatureAverageRepository.save(temperatureAverageDaily);
        }
    }


    @Scheduled (cron="0 1 */7 * *")
    public void computeWeekly()
    {
        List<Sensor> sensors = sensorRepository.findBySensorType(SensorType.TEMPERATURE);
        double sum = 0;
        int count = 0;

        for(Sensor s : sensors)
        {
            List<Temperature> tList = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(s.getId(),
                                                                                                     LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN),
                                                                                                     LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
            sum = 0;
            count = 0;
            for(Temperature t : tList)
            {
                sum += t.getValue();
                count++;
            }
            TemperatureAverageWeekly temperatureAverageWeekly = new TemperatureAverageWeekly();
            temperatureAverageWeekly.setValue(sum/count);
            temperatureAverageWeekly.setSensor(sensorRepository.getOne(s.getId()));
            temperatureAverageWeekly.setBeginDate(LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN));
            temperatureAverageWeekly.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));

            temperatureAverageRepository.save(temperatureAverageWeekly);
        }
    }

    @Scheduled (cron="0 1 1 * *")
    public void computeMonthly()
    {
        List<Sensor> sensors = sensorRepository.findBySensorType(SensorType.TEMPERATURE);
        double sum = 0;
        int count = 0;

        for(Sensor s : sensors)
        {
            List<Temperature> tList = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(s.getId(),
                                                                                                     LocalDateTime.now().minusMonths(1).with(LocalTime.MIN),
                                                                                                     LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
            sum = 0;
            count = 0;
            for(Temperature t : tList)
            {
                sum += t.getValue();
                count++;
            }
            TemperatureAverageMonthly temperatureAverageMonthly = new TemperatureAverageMonthly();
            temperatureAverageMonthly.setValue(sum/count);
            temperatureAverageMonthly.setSensor(sensorRepository.getOne(s.getId()));
            temperatureAverageMonthly.setBeginDate(LocalDateTime.now().minusMonths(1).with(LocalTime.MIN));
            temperatureAverageMonthly.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));

            temperatureAverageRepository.save(temperatureAverageMonthly);
        }
    }
}
