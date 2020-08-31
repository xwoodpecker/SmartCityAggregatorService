package htw.smartcity.aggregator.average;

import com.fasterxml.jackson.annotation.JsonFormat;
import htw.smartcity.aggregator.sensor.Sensor;
import javax.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Average.
 */
@Entity
@Table(name = "AVERAGE_DATA")
@JsonIgnoreProperties({"sensor"})
public class Average
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    /**
     * The enum Sensor type.
     */
    public enum SensorType {
        /**
         * Air quality sensor type.
         */
        AirQuality,
        /**
         * Parking sensor type.
         */
        PARKING,
        /**
         * Temperature sensor type.
         */
        TEMPERATURE
    }

    @Column(name ="sensor_type")
    @Enumerated(EnumType.STRING)
    private SensorType sensorType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name ="begin_time")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name ="end_time")
    private LocalDateTime endTime;

    @Column(name = "value")
    private Double value;


    /**
     * Default constructor for JPA only.
     */
    public Average() {

    }

    /**
     * Instantiates a new Average.
     *
     * @param sensor     the sensor
     * @param sensorType the sensor type
     * @param beginTime  the begin time
     * @param endTime    the end time
     * @param value      the value
     */
// Average of 1 sensor in a timeframe
    public Average(Sensor sensor, SensorType sensorType, LocalDateTime beginTime, LocalDateTime endTime, Double value) {
        this.sensor = sensor;
        this.sensorType = sensorType;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.value = value;
    }

    /**
     * Instantiates a new Average.
     *
     * @param sensorType the sensor type
     * @param beginTime  the begin time
     * @param endTime    the end time
     * @param value      the value
     */
// Average of all sensors in a timeframe
    public Average(SensorType sensorType, LocalDateTime beginTime, LocalDateTime endTime, Double value) {
        this.sensorType = sensorType;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.value = value;
    }

    /**
     * Instantiates a new Average.
     *
     * @param sensorType the sensor type
     * @param value      the value
     */
// Average of all sensors all time
    public Average(SensorType sensorType, Double value) {
        this.sensorType = sensorType;
        this.value = value;
    }

    /**
     * Instantiates a new Average.
     *
     * @param sensor     the sensor
     * @param sensorType the sensor type
     * @param value      the value
     */
// Average of 1 sensor all time
    public Average(Sensor sensor, SensorType sensorType, Double value) {
        this.sensor = sensor;
        this.sensorType = sensorType;
        this.value = value;
    }


    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set JPA id - for testing and JPA only. Not intended for normal use.
     *
     * @param id The new id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets sensor.
     *
     * @return the sensor
     */
    public Sensor getSensor() {
        return sensor;
    }

    /**
     * Sets sensor.
     *
     * @param sensor the sensor
     */
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    /**
     * Gets sensor type.
     *
     * @return the sensor type
     */
    public SensorType getSensorType() {
        return sensorType;
    }

    /**
     * Sets sensor type.
     *
     * @param sensorType the sensor type
     */
    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    /**
     * Gets begin time.
     *
     * @return the begin time
     */
    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    /**
     * Sets begin time.
     *
     * @param beginTime the begin time
     */
    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public Double getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return sensor.toString() + beginTime.toString() + "-" + endTime.toString() + ":" + value.toString();
    }

}
