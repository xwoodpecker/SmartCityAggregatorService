package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorRepository;
import htw.smartcity.aggregator.sensor.SensorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class TemperatureAggregateComputationScheduler
{
    @Autowired
    SensorRepository sensorRepository;

    @Autowired
    TemperatureRepository temperatureRepository;

    @Autowired
    TemperatureAggregateRepository temperatureAggregateRepository;

    @Scheduled (cron="0 1 * * *")
    public void computeDaily()
    {
        List<Sensor> sensors = sensorRepository.findBySensorType(SensorType.TEMPERATURE);

        for(Sensor sensor : sensors)
        {
            List<Temperature> tList = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(sensor.getId(),
                                                                           LocalDateTime.now().minusDays(1).with(LocalTime.MIN),
                                                                           LocalDateTime.now().minusDays(1).with(LocalTime.MAX));

            Optional<Double> sum = tList.stream().map(t -> t.getValue()).reduce((v1, v2) -> v1 + v2);
            Integer count = tList.size();
            if(count > 0) {
                if (sum.isPresent()) {
                    TemperatureAverageDaily temperatureAverageDaily = new TemperatureAverageDaily();
                    temperatureAverageDaily.setValue(sum.get() / count);
                    temperatureAverageDaily.setSensor(sensor);
                    temperatureAverageDaily.setDate(LocalDateTime.now().minusDays(1));
                    temperatureAggregateRepository.save(temperatureAverageDaily);
                }

                //compute maximum
                Optional<Temperature> maxValue = getMaxTemperature(tList);
                if (maxValue.isPresent()) {
                    TemperatureMaximumDaily maximum = new TemperatureMaximumDaily();
                    maximum.setSensor(sensor);
                    maximum.setValue(maxValue.get().getValue());
                    maximum.setDate(LocalDateTime.now().minusDays(1));
                    temperatureAggregateRepository.save(maximum);
                }

                //compute minimum
                Optional<Temperature> minValue = getMinTemperature(tList);
                if (minValue.isPresent()) {
                    TemperatureMinimumDaily minimum = new TemperatureMinimumDaily();
                    minimum.setSensor(sensor);
                    minimum.setValue(minValue.get().getValue());
                    minimum.setDate(LocalDateTime.now().minusDays(1));
                    temperatureAggregateRepository.save(minimum);
                }
            }
        }
    }


    @Scheduled (cron="0 1 */7 * *")
    public void computeWeekly()
    {
        List<Sensor> sensors = sensorRepository.findBySensorType(SensorType.TEMPERATURE);

        for(Sensor sensor : sensors)
        {
            List<Temperature> tList = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(sensor.getId(),
                                                                                                     LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN),
                                                                                                     LocalDateTime.now().minusDays(1).with(LocalTime.MAX));

            Optional<Double> sum = tList.stream().map(t -> t.getValue()).reduce((v1, v2) -> v1 + v2);
            Integer count = tList.size();
            if(count > 0) {
                if (sum.isPresent()) {
                    TemperatureAverageWeekly temperatureAverageWeekly = new TemperatureAverageWeekly();
                    temperatureAverageWeekly.setValue(sum.get() / count);
                    temperatureAverageWeekly.setSensor(sensor);
                    temperatureAverageWeekly.setBeginDate(LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN));
                    temperatureAverageWeekly.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
                    temperatureAggregateRepository.save(temperatureAverageWeekly);
                }


                Optional<Temperature> maxValue = getMaxTemperature(tList);
                if (maxValue.isPresent()) {
                    TemperatureMaximumWeekly maximum = new TemperatureMaximumWeekly();
                    maximum.setSensor(sensor);
                    maximum.setValue(maxValue.get().getValue());
                    maximum.setBeginDate(LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN));
                    maximum.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
                    temperatureAggregateRepository.save(maximum);
                }


                Optional<Temperature> minValue = getMinTemperature(tList);
                if (minValue.isPresent()) {
                    TemperatureMinimumWeekly minimum = new TemperatureMinimumWeekly();
                    minimum.setSensor(sensor);
                    minimum.setValue(minValue.get().getValue());
                    minimum.setBeginDate(LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN));
                    minimum.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
                    temperatureAggregateRepository.save(minimum);
                }
            }
        }
    }

    @Scheduled (cron="0 1 1 * *")
    public void computeMonthly()
    {
        List<Sensor> sensors = sensorRepository.findBySensorType(SensorType.TEMPERATURE);

        for(Sensor sensor : sensors)
        {
            List<Temperature> tList = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(sensor.getId(),
                                                                                                    LocalDateTime.now().minusMonths(1).with(LocalTime.MIN),
                                                                                                    LocalDateTime.now().minusDays(1).with(LocalTime.MAX));

            Optional<Double> sum = tList.stream().map(t -> t.getValue()).reduce((v1, v2) -> v1 + v2);
            Integer count = tList.size();
            if(count > 0) {
                if (sum.isPresent()) {
                    TemperatureAverageMonthly temperatureAverageMonthly = new TemperatureAverageMonthly();
                    temperatureAverageMonthly.setValue(sum.get() / count);
                    temperatureAverageMonthly.setSensor(sensor);
                    temperatureAverageMonthly.setBeginDate(LocalDateTime.now().minusMonths(1).with(LocalTime.MIN));
                    temperatureAverageMonthly.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
                    temperatureAggregateRepository.save(temperatureAverageMonthly);
                }


                Optional<Temperature> maxValue = getMaxTemperature(tList);
                if (maxValue.isPresent()) {
                    TemperatureMaximumMonthly maximum = new TemperatureMaximumMonthly();
                    maximum.setSensor(sensor);
                    maximum.setValue(maxValue.get().getValue());
                    maximum.setBeginDate(LocalDateTime.now().minusMonths(1).with(LocalTime.MIN));
                    maximum.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
                    temperatureAggregateRepository.save(maximum);
                }


                Optional<Temperature> minValue = getMinTemperature(tList);
                if (minValue.isPresent()) {
                    TemperatureMinimumMonthly minimum = new TemperatureMinimumMonthly();
                    minimum.setSensor(sensor);
                    minimum.setValue(minValue.get().getValue());
                    minimum.setBeginDate(LocalDateTime.now().minusMonths(1).with(LocalTime.MIN));
                    minimum.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
                    temperatureAggregateRepository.save(minimum);
                }
            }
        }
    }

    Optional<Temperature> getMaxTemperature(List<Temperature> tList){
        return tList.stream().max(Comparator.comparing(Temperature::getValue));
    }
    Optional<Temperature> getMinTemperature(List<Temperature> tList){
        return tList.stream().min(Comparator.comparing(Temperature::getValue));
    }
}
