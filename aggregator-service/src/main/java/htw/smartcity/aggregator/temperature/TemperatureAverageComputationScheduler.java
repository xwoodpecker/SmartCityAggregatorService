package htw.smartcity.aggregator.temperature;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TemperatureAverageComputationScheduler
{
    @Scheduled (cron="0 1 * * *")
    public void computeDaily()
    {

    }

    @Scheduled (cron="0 1 */7 * *")
    public void computeWeekly()
    {

    }

    @Scheduled (cron="0 1 1 * *")
    public void computeMonthly()
    {

    }
}
