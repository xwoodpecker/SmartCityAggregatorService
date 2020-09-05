package htw.smartcity.aggregator.airqualityaggregator;

import com.fasterxml.jackson.annotation.JsonFormat;
import htw.smartcity.aggregator.temperatureaggregate.TemperatureAggregate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "AIR_QUALITY_MAXIMUM_DAILY")
public class AirQualityMaximumDaily extends AirQualityAggregate
{
    @Column(name = "time")
    @JsonFormat (pattern = "yyyy-MM-dd")
    private LocalDateTime time;

    @Column(name = "value")
    private Integer value;

    public LocalDateTime getTime() {
        return time;
    }

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


}
