package htw.smartcity.aggregator.temperature;

import org.springframework.data.repository.CrudRepository;

public interface TemperatureRepository extends CrudRepository<Temperature, Integer> {

}