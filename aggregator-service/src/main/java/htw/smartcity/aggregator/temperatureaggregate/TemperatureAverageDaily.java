package htw.smartcity.aggregator.temperatureaggregate;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The type Temperature average daily.
 */
@Entity
@Table(name = "TEMPERATURE_AVERAGE_DAILY")
public class TemperatureAverageDaily extends TemperatureAggregate
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
