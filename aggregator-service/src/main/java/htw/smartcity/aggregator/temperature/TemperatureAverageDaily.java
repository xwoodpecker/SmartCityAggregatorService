package htw.smartcity.aggregator.temperature;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import htw.smartcity.aggregator.sensor.Sensor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "TEMPERATURE_AVERAGE_DAILY")
public class TemperatureAverageDaily extends TemperatureAverage
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
