package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.Sensor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private Set<Sensor> sensors  = new HashSet<>();;

    /**
     * Default constructor for JPA only.
     */
    public ParkingGroup() {

    }

    public ParkingGroup(String name, String information) {
        this.name = name;
        this.information = information;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Set<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(Set<Sensor> sensors) {
        this.sensors = sensors;
    }

    public void addToSensors(Sensor sensor){
        this.sensors.add(sensor);
    }

    @Override
    public String toString() {
        return name;
    }
}
