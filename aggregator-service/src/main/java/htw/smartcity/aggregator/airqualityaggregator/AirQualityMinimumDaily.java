package htw.smartcity.aggregator.airqualityaggregator;

import com.fasterxml.jackson.annotation.JsonFormat;
import htw.smartcity.aggregator.temperatureaggregate.TemperatureAggregate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * The type Air quality minimum daily.
 */
@Entity
@Table(name = "AIR_QUALITY_MINIMUM_DAILY")
public class AirQualityMinimumDaily extends AirQualityAggregate
{
    @Column(name = "time")
    @JsonFormat (pattern = "yyyy-MM-dd")
    private LocalDateTime time;

    @Column(name = "value")
    private Integer value;

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
     * Gets value.
     *
     * @return the value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(Integer value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "[" + value + "]";
    }
}
