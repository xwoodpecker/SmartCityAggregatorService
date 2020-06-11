package htw.smartcity.aggregator.temperature;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
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
@RequestMapping(path = "/temperatures")
@Tag(name = "temperatures", description = "Everything about temperatures")
public class TemperatureController {
    private TemperatureRepository temperatureRepository;

    private TemperatureResourceAssembler temperatureResourceAssembler;

    private TemperaturePageResourceAssembler temperaturePageResourceAssembler;

    public TemperatureController(TemperatureRepository temperatureRepository, TemperatureResourceAssembler temperatureResourceAssembler, TemperaturePageResourceAssembler temperaturePageResourceAssembler) {
        this.temperatureRepository = temperatureRepository;
        this.temperatureResourceAssembler = temperatureResourceAssembler;
        this.temperaturePageResourceAssembler = temperaturePageResourceAssembler;
    }

    @Operation(summary = "List all temperature measurements")
    @PageableAsQueryParam
    @GetMapping("/")
    ResponseEntity<PagedModel<Temperature>> all(@Parameter(hidden = true) Pageable pageable)
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
    @Operation(summary = "List all temperature measurements in a given timeframe")
    @PageableAsQueryParam
    @GetMapping("/timeframe")
    ResponseEntity<PagedModel<Temperature>> between(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime, @Parameter(hidden = true) Pageable pageable)
    {
        Page p = temperatureRepository.findTemperaturesByTimeBeforeAndTimeAfter(endTime, startTime, pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Returns a single temperature")
    @GetMapping("/{id}")
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
