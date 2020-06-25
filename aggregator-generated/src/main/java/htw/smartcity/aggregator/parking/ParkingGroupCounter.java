package htw.smartcity.aggregator.parking;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PARKING_GROUP_COUNTERS")
public class ParkingGroupCounter {
    @Id
    @OneToOne
    @PrimaryKeyJoinColumn
    private ParkingGroup parkingGroup;


    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date time;

    @Column(name = "free")
    private Integer free;


    @Column(name = "used")
    private Integer used;

    /**
     * Default constructor for JPA only.
     */
    public ParkingGroupCounter() {

    }

    public ParkingGroup getParkingGroup() {
        return parkingGroup;
    }

    public void setParkingGroup(ParkingGroup parkingGroup) {
        this.parkingGroup = parkingGroup;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
