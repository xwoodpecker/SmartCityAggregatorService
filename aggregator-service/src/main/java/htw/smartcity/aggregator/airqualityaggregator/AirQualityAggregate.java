package htw.smartcity.aggregator.airqualityaggregator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import htw.smartcity.aggregator.sensor.Sensor;

import javax.persistence.*;

@MappedSuperclass
@JsonIgnoreProperties ({"sensor"})
public class AirQualityAggregate
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @Column(name = "value")
    private Number value;

    /**
     * Default constructor for JPA only.
     */
    public AirQualityAggregate() {

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


    @Override
    public String toString() {
        return "[" + value + "]";
    }
}
