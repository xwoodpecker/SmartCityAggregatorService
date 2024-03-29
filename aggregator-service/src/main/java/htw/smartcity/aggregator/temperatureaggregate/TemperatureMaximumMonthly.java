package htw.smartcity.aggregator.temperatureaggregate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The type Temperature maximum monthly.
 */
@Entity
@Table(name = "TEMPERATURE_MAXIMUM_MONTHLY")
public class TemperatureMaximumMonthly extends TemperatureAggregate
{
    @Column(name = "begin_date")
    private LocalDateTime beginDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * Gets begin date.
     *
     * @return the begin date
     */
    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    /**
     * Sets begin date.
     *
     * @param beginDate the begin date
     */
    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
