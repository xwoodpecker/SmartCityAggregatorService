package htw.smartcity.aggregator.temperatureaggregate;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * The type Temperature minimum daily.
 */
@Entity
@Table(name = "TEMPERATURE_MINIMUM_DAILY")
public class TemperatureMinimumDaily extends TemperatureAggregate
{
    @Column(name = "time")
    @JsonFormat (pattern = "yyyy-MM-dd")
    private LocalDateTime time;

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
}
