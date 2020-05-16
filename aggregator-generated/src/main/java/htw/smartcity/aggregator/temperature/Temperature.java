package htw.smartcity.aggregator.temperature;

import javax.persistence.*;

@Entity
@Table(name = "TEMPERATURE")
public class Temperature {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;

    @Column(name = "time")
    protected String time;

    @Column(name = "value")
    protected Double value;



    /**
     * Default constructor for JPA only.
     */
    protected Temperature() {

    }

    public Temperature(String time, Double value) {
        this.time = time;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    /**
     * Set JPA id - for testing and JPA only. Not intended for normal use.
     *
     * @param id
     *            The new id.
     */
    protected void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
