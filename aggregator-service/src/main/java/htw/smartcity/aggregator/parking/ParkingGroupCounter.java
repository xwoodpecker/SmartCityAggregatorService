package htw.smartcity.aggregator.parking;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PARKING_GROUP_COUNTERS")
public class ParkingGroupCounter {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date time;

    @Column(name = "free")
    private Integer free;


    @Column(name = "used")
    private Integer used;

    @ManyToOne(fetch=FetchType.EAGER)
    private ParkingGroup parkingGroup;

    /**
     * Default constructor for JPA only.
     */
    public ParkingGroupCounter() {

    }

    public ParkingGroupCounter(Date time, Integer free, Integer used, ParkingGroup parkingGroup){
        this.time = time;
        this.free = free;
        this.used = used;
        this.parkingGroup = parkingGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ParkingGroup getParkingGroup() {
        return parkingGroup;
    }

    public void setParkingGroup(ParkingGroup parkingGroup) {
        this.parkingGroup = parkingGroup;
    }

    public void incrementFree(){
        this.free++;
    }

    public void incrementUsed(){
        this.used++;
    }

    public void spotFree(){
        if(this.used > 0) {
            this.used--;
            this.free++;
        }
    }

    public void spotUsed() {
        if (this.free > 0) {
            this.free--;
            this.used++;
        }
    }
}