package htw.smartcity.aggregator.airquality;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AirqualityRepository extends JpaRepository<Airquality, Long> {

    Page findAirqualitiesBySensorId(Long sensorId, Pageable pageable);

    Page findAirqualitiesByTimeBeforeAndTimeAfter(Date endTime, Date startTime, Pageable pageable);
}