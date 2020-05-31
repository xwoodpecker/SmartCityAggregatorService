package htw.smartcity.aggregator.temperature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Long> {
    List<Temperature> findTemperaturesByTimeBeforeAndTimeAfter(Date endTime, Date startTime);
}