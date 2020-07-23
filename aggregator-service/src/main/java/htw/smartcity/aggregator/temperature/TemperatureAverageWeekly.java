package htw.smartcity.aggregator.temperature;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import htw.smartcity.aggregator.sensor.Sensor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "TemperatureAverageWeekly")
@JsonIgnoreProperties ({"sensor"})
public class TemperatureAverageWeekly
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "sensor_id")
    private Sensor sensor;


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
    public TemperatureAverageWeekly() {

    }

    // Average of 1 sensor in a timeframe
    public TemperatureAverageWeekly(Sensor sensor, Date beginTime, Date endTime, Double value) {
        this.sensor = sensor;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.value = value;
    }

    // Average of all sensors in a timeframe
    public TemperatureAverageWeekly(Date beginTime, Date endTime, Double value) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.value = value;
    }

    // Average of all sensors all time
    public TemperatureAverageWeekly(Double value) {
        this.value = value;
    }

    // Average of 1 sensor all time
    public TemperatureAverageWeekly(Sensor sensor, Double value) {
        this.sensor = sensor;
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
