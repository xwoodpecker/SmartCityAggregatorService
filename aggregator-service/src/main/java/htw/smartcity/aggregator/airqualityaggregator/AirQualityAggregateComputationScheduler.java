package htw.smartcity.aggregator.airqualityaggregator;

import htw.smartcity.aggregator.airquality.AirQuality;
import htw.smartcity.aggregator.airquality.AirQualityRepository;
import htw.smartcity.aggregator.base.ExceptionManager;
import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorRepository;
import htw.smartcity.aggregator.sensor.SensorType;
import htw.smartcity.aggregator.temperature.Temperature;
import htw.smartcity.aggregator.temperature.TemperatureRepository;
import htw.smartcity.aggregator.temperatureaggregate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * The type Air quality aggregate computation scheduler.
 */
@Component
public class AirQualityAggregateComputationScheduler
{
    /**
     * The Sensor repository.
     */
    @Autowired
    SensorRepository sensorRepository;

    /**
     * The Air quality repository.
     */
    @Autowired
    AirQualityRepository airQualityRepository;

    /**
     * The Air quality aggregate repository.
     */
    @Autowired
    AirQualityAggregateRepository airQualityAggregateRepository;

    /**
     * The Exception manager.
     */
    @Autowired
    ExceptionManager exceptionManager;

    @Scheduled (cron="0 1 * * *")
    private void computeDaily()
    {
        List<Sensor> sensors = sensorRepository.findBySensorType(SensorType.AIR_QUALITY);

        for(Sensor sensor : sensors)
        {
            LocalDateTime start = LocalDateTime.now().minusDays(1).with(LocalTime.MIN);
            LocalDateTime end   = LocalDateTime.now().minusDays(1).with(LocalTime.MAX);
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
            List<AirQuality> tList  = airQualityRepository.findAirQualitiesBySensorIdAndTimeBetween(sensorId, start, end);
            Sensor            sensor = sensorRepository.getOne(sensorId);

            Optional<Integer> sum   = tList.stream().map(t -> t.getValue()).reduce((v1, v2) -> v1 + v2);
            Integer          count = tList.size();
            if (count > 0) {
                if (sum.isPresent()) {
                    AirQualityAverageDaily airQualityAverageDaily = new AirQualityAverageDaily();
                    airQualityAverageDaily.setValue( (double)sum.get() / count);
                    airQualityAverageDaily.setSensor(sensor);
                    airQualityAverageDaily.setTime(LocalDateTime.now().minusDays(1));
                    airQualityAggregateRepository.save(airQualityAverageDaily);
                }

                //compute maximum
                Optional<AirQuality> maxValue = getMaxAirQuality(tList);
                if (maxValue.isPresent()) {
                    AirQualityMaximumDaily maximum = new AirQualityMaximumDaily();
                    maximum.setSensor(sensor);
                    maximum.setValue(maxValue.get().getValue());
                    maximum.setTime(LocalDateTime.now().minusDays(1));
                    airQualityAggregateRepository.save(maximum);
                }

                //compute minimum
                Optional<AirQuality> minValue = getMinAirQuality(tList);
                if (minValue.isPresent()) {
                    AirQualityMinimumDaily minimum = new AirQualityMinimumDaily();
                    minimum.setSensor(sensor);
                    minimum.setValue(minValue.get().getValue());
                    minimum.setTime(LocalDateTime.now().minusDays(1));
                    airQualityAggregateRepository.save(minimum);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            exceptionManager.DailyAggregationFailed();
        }
    }

    @Scheduled (cron="0 1 */7 * *")
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
            List<AirQuality> tList = airQualityRepository.findAirQualitiesBySensorIdAndTimeBetween(sensorId, start, end);
            Sensor sensor = sensorRepository.getOne(sensorId);

            Optional<Integer> sum = tList.stream().map(t -> t.getValue()).reduce((v1, v2) -> v1 + v2);
            Integer count = tList.size();
            if (count > 0) {
                if (sum.isPresent()) {
                    AirQualityAverageWeekly airQualityAverageWeekly = new AirQualityAverageWeekly();
                    airQualityAverageWeekly.setValue( (double)sum.get() / count);
                    airQualityAverageWeekly.setSensor(sensor);
                    airQualityAverageWeekly.setBeginDate(LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN));
                    airQualityAverageWeekly.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
                    airQualityAggregateRepository.save(airQualityAverageWeekly);
                }


                Optional<AirQuality> maxValue = getMaxAirQuality(tList);
                if (maxValue.isPresent()) {
                    AirQualityMaximumWeekly maximum = new AirQualityMaximumWeekly();
                    maximum.setSensor(sensor);
                    maximum.setValue(maxValue.get().getValue());
                    maximum.setBeginDate(LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN));
                    maximum.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
                    airQualityAggregateRepository.save(maximum);
                }


                Optional<AirQuality> minValue = getMinAirQuality(tList);
                if (minValue.isPresent()) {
                    AirQualityMinimumWeekly minimum = new AirQualityMinimumWeekly();
                    minimum.setSensor(sensor);
                    minimum.setValue(minValue.get().getValue());
                    minimum.setBeginDate(LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN));
                    minimum.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
                    airQualityAggregateRepository.save(minimum);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            exceptionManager.WeeklyAggregationFailed();
        }
    }

    @Scheduled (cron="0 1 1 * *")
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
            List<AirQuality> tList = airQualityRepository.findAirQualitiesBySensorIdAndTimeBetween(sensorId, start,
                                                                                                   end);
            Sensor sensor = sensorRepository.getOne(sensorId);

            Optional<Integer> sum = tList.stream().map(t -> t.getValue()).reduce((v1, v2) -> v1 + v2);
            Integer count = tList.size();
            if (count > 0) {
                if (sum.isPresent()) {
                    AirQualityAverageMonthly airQualityAverageMonthly = new AirQualityAverageMonthly();
                    airQualityAverageMonthly.setValue( (double)sum.get() / count);
                    airQualityAverageMonthly.setSensor(sensor);
                    airQualityAverageMonthly.setBeginDate(LocalDateTime.now().minusMonths(1).with(LocalTime.MIN));
                    airQualityAverageMonthly.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
                    airQualityAggregateRepository.save(airQualityAverageMonthly);
                }


                Optional<AirQuality> maxValue = getMaxAirQuality(tList);
                if (maxValue.isPresent()) {
                    AirQualityMaximumMonthly maximum = new AirQualityMaximumMonthly();
                    maximum.setSensor(sensor);
                    maximum.setValue(maxValue.get().getValue());
                    maximum.setBeginDate(LocalDateTime.now().minusMonths(1).with(LocalTime.MIN));
                    maximum.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
                    airQualityAggregateRepository.save(maximum);
                }


                Optional<AirQuality> minValue = getMinAirQuality(tList);
                if (minValue.isPresent()) {
                    AirQualityMaximumMonthly minimum = new AirQualityMaximumMonthly();
                    minimum.setSensor(sensor);
                    minimum.setValue(minValue.get().getValue());
                    minimum.setBeginDate(LocalDateTime.now().minusMonths(1).with(LocalTime.MIN));
                    minimum.setEndDate(LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
                    airQualityAggregateRepository.save(minimum);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            exceptionManager.MonthlyAggregationFailed();
        }
    }

    /**
     * Get max air quality optional.
     *
     * @param tList the t list
     * @return the optional
     */
    Optional<AirQuality> getMaxAirQuality(List<AirQuality> tList){
        return tList.stream().max(Comparator.comparing(AirQuality::getValue));
    }

    /**
     * Get min air quality optional.
     *
     * @param tList the t list
     * @return the optional
     */
    Optional<AirQuality> getMinAirQuality(List<AirQuality> tList){
        return tList.stream().min(Comparator.comparing(AirQuality::getValue));
    }
}
