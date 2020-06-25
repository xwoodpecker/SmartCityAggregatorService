package htw.smartcity.aggregator.airquality;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirqualityRepository extends CrudRepository<Airquality, Long> {

}