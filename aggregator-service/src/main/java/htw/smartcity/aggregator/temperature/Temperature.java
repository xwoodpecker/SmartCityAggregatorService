package htw.smartcity.aggregator.temperature;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import htw.smartcity.aggregator.sensor.Sensor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TEMPERATURE_DATA")
@JsonIgnoreProperties({"sensor"})
public class Temperature {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @Column(name = "value")
    private Double value;



    /**
     * Default constructor for JPA only.
     */
    public Temperature() {

    }

    public Temperature(Date time, Sensor sensor, Double value) {
        this.time = time;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return time + " [" + value + "]";
    }

}
