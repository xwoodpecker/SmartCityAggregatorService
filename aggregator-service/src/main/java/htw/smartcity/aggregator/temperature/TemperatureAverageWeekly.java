package htw.smartcity.aggregator.temperature;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import htw.smartcity.aggregator.sensor.Sensor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TEMPERATURE_AVERAGE_WEEKLY")
public class TemperatureAverageWeekly extends TemperatureAverage
{
}