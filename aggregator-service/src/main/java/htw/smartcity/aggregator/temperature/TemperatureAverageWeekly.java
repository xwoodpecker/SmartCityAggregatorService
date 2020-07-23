package htw.smartcity.aggregator.temperature;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import htw.smartcity.aggregator.sensor.Sensor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TEMPERATURE_AVERAGE_WEEKLY")
public class TemperatureAverageWeekly extends TemperatureAverage
{
    @Column(name = "begin_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
