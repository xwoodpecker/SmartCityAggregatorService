package htw.smartcity.aggregator.average;

import htw.smartcity.aggregator.sensor.Sensor;
import javax.persistence.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import htw.smartcity.aggregator.sensor.SensorController;

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

    public enum SensorType {
        AIRQUALITY,
        PARKING,
        TEMPERATURE
    }

    @Column(name ="sensor_type")
    @Enumerated(EnumType.STRING)
    private SensorType sensorType;

    @Column(name ="begin_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginTime;


    @Column(name ="end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name = "value")
    private Double value;


    /**
     * Default constructor for JPA only.
     */
    public Average() {

    }

    // Average of 1 sensor in a timeframe
    public Average(Sensor sensor, SensorType sensorType, Date beginTime, Date endTime, Double value) {
        this.sensor = sensor;
        this.sensorType = sensorType;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.value = value;
    }

    // Average of all sensors in a timeframe
    public Average(SensorType sensorType, Date beginTime, Date endTime, Double value) {
        this.sensorType = sensorType;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.value = value;
    }

    // Average of all sensors all time
    public Average(SensorType sensorType, Double value) {
        this.sensorType = sensorType;
        this.value = value;
    }

    // Average of 1 sensor all time
    public Average(Sensor sensor, SensorType sensorType, Double value) {
        this.sensor = sensor;
        this.sensorType = sensorType;
        this.value = value;
    }



    public Long getId() {
        return id;
    }

    /**
     * Set JPA id - for testing and JPA only. Not intended for normal use.
     *
     * @param id
     *            The new id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return sensor.toString() + beginTime.toString() + "-" + endTime.toString() + ":" + value.toString();
    }

}
