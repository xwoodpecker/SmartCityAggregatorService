package htw.smartcity.aggregator.temperature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class TemperatureController {
    //todo
    // "Field injection is not recommended"
    @Autowired
    private TemperatureRepository temperatureRepository;

    @Autowired
    private TemperatureResourceAssembler temperatureResourceAssembler;

    @Autowired
    private TemperaturePageResourceAssembler temperaturePageResourceAssembler;

    @GetMapping("/temperature")
    ResponseEntity<PagedModel<Temperature>> all(Pageable pageable)
    {
        Page p = temperatureRepository.findAll(pageable);
        //todo
        // Unchecked assignment: 'org.springframework.hateoas.PagedModel' to 'org.springframework.hateoas.PagedModel<htw.smartcity.aggregator.temperature.Temperature>'
        // doesnt make sense since TemperaturePageResourceAssembler extends PagedResourcesAssembler<Temperature>
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);

    }
    //todo
    // this DateTimeFormat is still stupid. for some reason the spring.jackson.date-format
    // property in resources/application.properties is not used for this conversion
    @GetMapping("/temperature/byDate")
    ResponseEntity<PagedModel<Temperature>> between(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime, Pageable pageable)
    {
        Page p = temperatureRepository.findTemperaturesByTimeBeforeAndTimeAfter(endTime, startTime, pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }

    //todo remove (not used)
    @PostMapping("/temperature")
    Temperature newTemperature(@RequestBody Temperature newTemperature)
    {
        return temperatureRepository.save(newTemperature);
    }

    @GetMapping("/temperature/{id}")
    EntityModel<Temperature> one(@PathVariable Long id)
    {
        Temperature temperature = temperatureRepository.findById(id)
                .orElseThrow(() -> new TemperatureNotFoundException(id));

        return temperatureResourceAssembler.toModel(temperature);
    }

    @GetMapping("temperature/bySensor")
    public ResponseEntity<PagedModel<Temperature>> bySensor(@RequestParam Long sensorId, Pageable pageable) {
        Page p = temperatureRepository.findTemperaturesBySensorId(sensorId, pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }

    //todo remove (not used)
    @PutMapping("temperature/{id}")
    Temperature replaceTemperature(@RequestBody Temperature newTemperature, @PathVariable Long id)
    {
        return temperatureRepository.findById(id)
                .map(temperature -> {
                    temperature.setTime(newTemperature.getTime());
                    temperature.setValue(newTemperature.getValue());
                    return temperatureRepository.save(temperature);
                })
                .orElseGet(() -> {
                    newTemperature.setId(id);
                    return temperatureRepository.save(newTemperature);
                });
    }

    //todo remove? (tbd)
    @DeleteMapping("/temperature/{id}")
    void deleteTemperature(@PathVariable Long id)
    {
        temperatureRepository.deleteById(id);
    }
}
