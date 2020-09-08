package htw.smartcity.aggregator.sensor;

import javax.persistence.*;

/**
 * The type Sensor.
 */
@Entity
@Table(name = "SENSORS")
public class Sensor {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name ="sensor_type")
    @Enumerated(EnumType.STRING)
    private SensorType sensorType;

    @Column(name ="x")
    private Double x;


    @Column(name ="y")
    private Double y;

    @Column(name = "information")
    private String information;


    /**
     * Default constructor for JPA only.
     */
    public Sensor() {

    }

    /**
     * Instantiates a new Sensor.
     *
     * @param name        the name
     * @param sensorType  the sensor type
     * @param x           the x
     * @param y           the y
     * @param information the information
     */
    public Sensor(String name, SensorType sensorType, Double x, Double y, String information) {
        this.name = name;
        this.sensorType = sensorType;
        this.x = x;
        this.y = y;
        this.information = information;
    }


    /**
     * Instantiates a new Sensor.
     *
     * @param name       the name
     * @param sensorType the sensor type
     */
    public Sensor(String name, SensorType sensorType) {
        this.name = name;
        this.sensorType = sensorType;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
     * Gets x.
     *
     * @return the x
     */
    public Double getX() {
        return x;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(Double y) {
        this.y = y;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public Double getY() {
        return y;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * Gets information.
     *
     * @return the information
     */
    public String getInformation() {
        return information;
    }

    /**
     * Sets information.
     *
     * @param information the information
     */
    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return name;
    }
}
