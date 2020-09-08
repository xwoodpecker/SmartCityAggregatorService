package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.Sensor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Parking group.
 */
@Entity
@Table(name = "PARKING_GROUPS")
public class ParkingGroup {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "information")
    private String information;

    @JoinTable(name = "PARKING_GROUP_SENSORS")
    @OneToMany(fetch=FetchType.EAGER)
    private Set<Sensor> sensors  = new HashSet<>();

    /**
     * Default constructor for JPA only.
     */
    public ParkingGroup() {

    }

    /**
     * Instantiates a new Parking group.
     *
     * @param name        the name
     * @param information the information
     */
    public ParkingGroup(String name, String information) {
        this.name = name;
        this.information = information;
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

    /**
     * Gets sensors.
     *
     * @return the sensors
     */
    public Set<Sensor> getSensors() {
        return sensors;
    }

    /**
     * Sets sensors.
     *
     * @param sensors the sensors
     */
    public void setSensors(Set<Sensor> sensors) {
        this.sensors = sensors;
    }

    /**
     * Add to sensors.
     *
     * @param sensor the sensor
     */
    public void addToSensors(Sensor sensor){
        this.sensors.add(sensor);
    }

    @Override
    public String toString() {
        return name;
    }
}
