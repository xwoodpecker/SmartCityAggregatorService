package htw.smartcity.aggregator.temperature;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TEMPERATURE_AVERAGE_DAILY")
public class TemperatureAverageDaily extends TemperatureAggregate
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
