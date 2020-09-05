package htw.smartcity.aggregator.temperature;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TEMPERATURE_MINIMUM_DAILY")
public class TemperatureMinimumDaily extends TemperatureAggregate
{
    @Column(name = "time")
    @JsonFormat (pattern = "yyyy-MM-dd")
    private LocalDateTime time;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
