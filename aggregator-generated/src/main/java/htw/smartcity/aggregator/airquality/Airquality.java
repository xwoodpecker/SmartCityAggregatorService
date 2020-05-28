package htw.smartcity.aggregator.airquality;

import com.fasterxml.jackson.annotation.JsonFormat;
import htw.smartcity.aggregator.sensor.Sensor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AIR_QUALITY_DATA")
public class Airquality {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @Column(name = "value")
    private String value;



    /**
     * Default constructor for JPA only.
     */
    public Airquality() {

    }

    public Airquality(Date time, Sensor sensor, String value) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return time + " [" + value + "]";
    }
}
