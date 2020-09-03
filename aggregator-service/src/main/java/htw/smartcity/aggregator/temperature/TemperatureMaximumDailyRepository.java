package htw.smartcity.aggregator.temperature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Temperature average repository.
 */
@Repository
public interface TemperatureMaximumDailyRepository<TemperatureMaximumDaily, Long> extends TemperatureAverageRepository
{

}
