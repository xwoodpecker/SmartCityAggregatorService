package htw.smartcity.aggregator.humidty.temperature;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "HUMIDITY")
public class Humidity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    protected Date time;

    @Column(name = "value")
    protected String value;



    /**
     * Default constructor for JPA only.
     */
    protected Humidity() {

    }

    public Humidity(Date time, String value) {
        this.time = time;
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
