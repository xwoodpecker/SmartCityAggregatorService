package htw.smartcity.aggregator.temperature;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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
    //todo discuss if this is even needed, possible to get same result just from pageable
    @Operation(summary = "List all temperature measurements in a given timeframe")
    @PageableAsQueryParam
    @GetMapping("/inTimeframe")
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

    @Operation(summary = "Returns all temperatures from a specific sensor")
    @GetMapping("/bySensor/{id}")
    public ResponseEntity<PagedModel<Temperature>> bySensor(@PathVariable Long sensorId, @Parameter(hidden = true) Pageable pageable) {
        Page p = temperatureRepository.findTemperaturesBySensorId(sensorId, pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Returns the average temperature from all sensors within the given timeframe")
    @GetMapping("/average")
    ResponseEntity<PagedModel<Temperature>> average(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime, @Parameter(hidden = true) Pageable pageable)
    {
        //todo implement
        Page p = temperatureRepository.findTemperaturesByTimeBeforeAndTimeAfter(endTime, startTime, pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Returns the average temperature from a specific sensor within the given timeframe")
    @GetMapping("average/{sensorId}")
    ResponseEntity<PagedModel<Temperature>> averageBySensor(
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime,
            @PathVariable Long sensorId,
            @Parameter(hidden = true) Pageable pageable)
    {
        //todo implement
        Page p = temperatureRepository.findTemperaturesByTimeBeforeAndTimeAfter(endTime, startTime, pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }
}
