package htw.smartcity.aggregator.temperatureaggregate;

import htw.smartcity.aggregator.base.ExceptionManager;
import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorRepository;
import htw.smartcity.aggregator.sensor.SensorType;
import htw.smartcity.aggregator.temperature.Temperature;
import htw.smartcity.aggregator.temperature.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * The type Temperature aggregate computation scheduler.
 */
@Component
public class TemperatureAggregateComputationScheduler
{
    /**
     * The Sensor repository.
     */
    SensorRepository sensorRepository;

    /**
     * The Temperature repository.
     */
    TemperatureRepository temperatureRepository;

    /**
     * The Temperature aggregate repository.
     */
    TemperatureAggregateRepository temperatureAggregateRepository;

    /**
     * The Exception manager.
     */
    final
    ExceptionManager exceptionManager;

    public TemperatureAggregateComputationScheduler(SensorRepository sensorRepository, TemperatureRepository temperatureRepository, TemperatureAggregateRepository temperatureAggregateRepository, ExceptionManager exceptionManager) {
        this.sensorRepository = sensorRepository;
        this.temperatureRepository = temperatureRepository;
        this.temperatureAggregateRepository = temperatureAggregateRepository;
        this.exceptionManager = exceptionManager;
    }

    @Scheduled (cron="0 0 1 * * ?")
    private void computeDaily()
    {
        List<Sensor> sensors = sensorRepository.findBySensorType(SensorType.TEMPERATURE);

        for(Sensor sensor : sensors)
        {
            LocalDateTime start = LocalDateTime.now().minusDays(1).with(LocalTime.MIN);
            LocalDateTime end = LocalDateTime.now().minusDays(1).with(LocalTime.MAX);
            computeAggregatesDaily(sensor.getId(), start, end);
        }
    }

    /**
     * Compute aggregates daily.
     *
     * @param sensorId the sensor id
     * @param start    the start
     * @param end      the end
     */
    public void computeAggregatesDaily(Long sensorId, LocalDateTime start, LocalDateTime end){
        try {
            List<Temperature> tList  = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(sensorId, start, end);
            Sensor            sensor = sensorRepository.getOne(sensorId);

            Optional<Double> sum = tList.stream().map(t -> t.getValue()).reduce((v1, v2) -> v1 + v2);
            Integer count = tList.size();
            if (count > 0) {
                if (sum.isPresent()) {
                    TemperatureAverageDaily temperatureAverageDaily = new TemperatureAverageDaily();
                    temperatureAverageDaily.setValue(sum.get() / count);
                    temperatureAverageDaily.setSensor(sensor);
                    temperatureAverageDaily.setTime(LocalDateTime.now().minusDays(1));
                    temperatureAggregateRepository.save(temperatureAverageDaily);
                }

                //compute maximum
                Optional<Temperature> maxValue = getMaxTemperature(tList);
                if (maxValue.isPresent()) {
                    TemperatureMaximumDaily maximum = new TemperatureMaximumDaily();
                    maximum.setSensor(sensor);
                    maximum.setValue(maxValue.get().getValue());
                    maximum.setTime(LocalDateTime.now().minusDays(1));
                    temperatureAggregateRepository.save(maximum);
                }

                //compute minimum
                Optional<Temperature> minValue = getMinTemperature(tList);
                if (minValue.isPresent()) {
                    TemperatureMinimumDaily minimum = new TemperatureMinimumDaily();
                    minimum.setSensor(sensor);
                    minimum.setValue(minValue.get().getValue());
                    minimum.setTime(LocalDateTime.now().minusDays(1));
                    temperatureAggregateRepository.save(minimum);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            exceptionManager.DailyAggregationFailed();
        }
    }

    @Scheduled (cron="0 0 1 * * MON")
    private void computeWeekly()
    {
        List<Sensor> sensors = sensorRepository.findBySensorType(SensorType.TEMPERATURE);

        for(Sensor sensor : sensors)
        {
            LocalDateTime start = LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN);
            LocalDateTime end = LocalDateTime.now().minusDays(1).with(LocalTime.MAX);
            computeAggregatesWeekly(sensor.getId(), start, end);
        }
    }

    /**
     * Compute aggregates weekly.
     *
     * @param sensorId the sensor id
     * @param start    the start
     * @param end      the end
     */
    public void computeAggregatesWeekly(Long sensorId, LocalDateTime start, LocalDateTime end){
        try {
            List<Temperature> tList = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(sensorId, start, end);
            Sensor sensor = sensorRepository.getOne(sensorId);

            Optional<Double> sum = tList.stream().map(t -> t.getValue()).reduce((v1, v2) -> v1 + v2);
            Integer count = tList.size();
            if (count > 0) {
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
        }catch (Exception e) {
            e.printStackTrace();
            exceptionManager.WeeklyAggregationFailed();
        }
    }

    @Scheduled (cron="0 0 1 1 * ?")
    private void computeMonthly()
    {
        List<Sensor> sensors = sensorRepository.findBySensorType(SensorType.TEMPERATURE);

        for(Sensor sensor : sensors)
        {
            LocalDateTime start = LocalDateTime.now().minusMonths(1).with(LocalTime.MIN);
            LocalDateTime end = LocalDateTime.now().minusDays(1).with(LocalTime.MAX);
            computeAggregatesMonthly(sensor.getId(), start, end);
        }
    }

    /**
     * Compute aggregates monthly.
     *
     * @param sensorId the sensor id
     * @param start    the start
     * @param end      the end
     */
    public void computeAggregatesMonthly(Long sensorId, LocalDateTime start, LocalDateTime end) {
        try {
            List<Temperature> tList = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(sensorId, start, end);
            Sensor sensor = sensorRepository.getOne(sensorId);

            Optional<Double> sum = tList.stream().map(t -> t.getValue()).reduce((v1, v2) -> v1 + v2);
            Integer count = tList.size();
            if (count > 0) {
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
        }catch (Exception e) {
            e.printStackTrace();
            exceptionManager.MonthlyAggregationFailed();
        }
    }

    /**
     * Get max temperature optional.
     *
     * @param tList the t list
     * @return the optional
     */
    Optional<Temperature> getMaxTemperature(List<Temperature> tList){
        return tList.stream().max(Comparator.comparing(Temperature::getValue));
    }

    /**
     * Get min temperature optional.
     *
     * @param tList the t list
     * @return the optional
     */
    Optional<Temperature> getMinTemperature(List<Temperature> tList){
        return tList.stream().min(Comparator.comparing(Temperature::getValue));
    }
}
