package htw.smartcity.aggregator.parking;


import javax.persistence.*;

@Entity
@Table(name = "PARKING_GROUP_COUNTERS")
public class ParkingGroupCounter {

    /**
     * Default constructor for JPA only.
     */

    @Id
    @OneToOne
    @PrimaryKeyJoinColumn
    private ParkingGroup parkingGroup;

    @Column(name = "free")
    private Integer free;


    @Column(name = "used")
    private Integer used;

    public ParkingGroupCounter() {

    }

    public ParkingGroup getParkingGroup() {
        return parkingGroup;
    }

    public void setParkingGroup(ParkingGroup parkingGroup) {
        this.parkingGroup = parkingGroup;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }
}
