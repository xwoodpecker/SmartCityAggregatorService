package htw.smartcity.aggregator.temperature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Temperature average repository.
 */
@Repository
public interface TemperatureAggregateRepository extends JpaRepository<TemperatureAggregate, Long>
{
    TemperatureAggregate save(TemperatureAggregate temperatureAggregate);
}
