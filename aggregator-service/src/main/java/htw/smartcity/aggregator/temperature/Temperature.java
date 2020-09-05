package htw.smartcity.aggregator.temperature;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import htw.smartcity.aggregator.sensor.Sensor;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * The type Temperature.
 */
@Entity
@Table(name = "TEMPERATURE_DATA")
@JsonIgnoreProperties({"sensor"})
public class Temperature {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(name = "time")
    private OffsetDateTime time;

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

    /**
     * Instantiates a new Temperature.
     *
     * @param time   the time
     * @param sensor the sensor
     * @param value  the value
     */
    public Temperature(OffsetDateTime time, Sensor sensor, Double value) {
        this.time = time;
        this.sensor = sensor;
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
     * Gets time.
     *
     * @return the time
     */
    public OffsetDateTime getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(OffsetDateTime time) {
        this.time = time;
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
        return time + " [" + value + "]";
    }

}
