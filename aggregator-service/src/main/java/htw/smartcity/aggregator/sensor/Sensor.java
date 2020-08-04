package htw.smartcity.aggregator.sensor;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SENSORS")
public class Sensor {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public enum SensorType {
        AirQuality,
        PARKING,
        TEMPERATURE
    }
    
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

    public Sensor(String name, SensorType sensorType, Double x, Double y, String information) {
        this.name = name;
        this.sensorType = sensorType;
        this.x = x;
        this.y = y;
        this.information = information;
    }


    public Sensor(String name, SensorType sensorType) {
        this.name = name;
        this.sensorType = sensorType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public Double getX() {
        return x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getY() {
        return y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
