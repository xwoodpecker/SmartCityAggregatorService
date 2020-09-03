package htw.smartcity.aggregator.temperature;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TEMPERATURE_MAXIMUM_DAILY")
public class TemperatureMaximumDaily extends TemperatureAverage
{
    @Column(name = "date")
    @JsonFormat (pattern = "yyyy-MM-dd")
    private LocalDateTime time;

    public LocalDateTime getDate() {
        return time;
    }

    public void setDate(LocalDateTime time) {
        this.time = time;
    }
}
