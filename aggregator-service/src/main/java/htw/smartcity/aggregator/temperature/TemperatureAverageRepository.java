package htw.smartcity.aggregator.temperature;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * The interface Temperature average repository.
 */
@Repository
public interface TemperatureAverageRepository extends JpaRepository<TemperatureAverage, Long>
{
    TemperatureAverage save(TemperatureAverage temperatureaverage);
}
