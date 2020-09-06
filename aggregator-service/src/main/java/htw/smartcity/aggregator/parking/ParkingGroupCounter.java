package htw.smartcity.aggregator.parking;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The type Parking group counter.
 */
@Entity
@Table(name = "PARKING_GROUP_COUNTERS")
public class ParkingGroupCounter {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(name = "time")
    private LocalDateTime time;

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

    /**
     * Instantiates a new Parking group counter.
     *
     * @param time         the time
     * @param free         the free
     * @param used         the used
     * @param parkingGroup the parking group
     */
    public ParkingGroupCounter(LocalDateTime time, Integer free, Integer used, ParkingGroup parkingGroup){
        this.time = time;
        this.free = free;
        this.used = used;
        this.parkingGroup = parkingGroup;
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
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Gets free.
     *
     * @return the free
     */
    public Integer getFree() {
        return free;
    }

    /**
     * Sets free.
     *
     * @param free the free
     */
    public void setFree(Integer free) {
        this.free = free;
    }

    /**
     * Gets used.
     *
     * @return the used
     */
    public Integer getUsed() {
        return used;
    }

    /**
     * Sets used.
     *
     * @param used the used
     */
    public void setUsed(Integer used) {
        this.used = used;
    }

    /**
     * Gets parking group.
     *
     * @return the parking group
     */
    public ParkingGroup getParkingGroup() {
        return parkingGroup;
    }

    /**
     * Sets parking group.
     *
     * @param parkingGroup the parking group
     */
    public void setParkingGroup(ParkingGroup parkingGroup) {
        this.parkingGroup = parkingGroup;
    }

    /**
     * Increment free.
     */
    public void incrementFree(){
            this.free++;
    }

    /**
     * Increment used.
     */
    public void incrementUsed(){
            this.used++;
    }

    /**
     * Spot free.
     */
    public void spotFree(){
        if(this.used > 0) {
            this.used--;
            this.free++;
        }
    }

    /**
     * Spot used.
     */
    public void spotUsed() {
        if (this.free > 0) {
            this.free--;
            this.used++;
        }
    }
}
