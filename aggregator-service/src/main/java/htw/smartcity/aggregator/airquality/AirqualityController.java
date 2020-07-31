package htw.smartcity.aggregator.airquality;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/airqualities")
@Tag(name = "Air Quality Measures", description = "Endpoint to get air quality measures")
public class AirqualityController {
    private AirqualityRepository airqualityRepository;
    private AirqualityResourceAssembler airqualityResourceAssembler;
    private AirqualityPageResourceAssembler airqualityPageResourceAssembler;

    public AirqualityController(AirqualityRepository airqualityRepository, AirqualityResourceAssembler airqualityResourceAssembler, AirqualityPageResourceAssembler airqualityPageResourceAssembler) {
        this.airqualityRepository = airqualityRepository;
        this.airqualityResourceAssembler = airqualityResourceAssembler;
        this.airqualityPageResourceAssembler = airqualityPageResourceAssembler;
    }

    @Operation(summary = "Get all air quality measurements")
    @PageableAsQueryParam
    @GetMapping("/")
    ResponseEntity<PagedModel<Airquality>> all(@Parameter(hidden = true) Pageable pageable)
    {
        Page p = airqualityRepository.findAll(pageable);
        return new ResponseEntity<PagedModel<Airquality>>(airqualityPageResourceAssembler.toModel(p, airqualityResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get the latest measurements of all air quality sensors")
    @PageableAsQueryParam
    @GetMapping("/latest")
    ResponseEntity<PagedModel<Airquality>> latest(@Parameter(hidden = true) Pageable pageable)
    {
        //todo
        return all(pageable);
    }

    @Operation(summary = "Get the average air quality of the latest measurements of all sensors")
    @GetMapping("/latest/average")
    EntityModel<Airquality> latestAverage()
    {
        //todo
        //todo average entity?
        return one((long) 1);
    }

    @Operation(summary = "Get all air quality measurements of all sensors in a given timeframe")
    @PageableAsQueryParam
    @GetMapping("/timeframe")
    ResponseEntity<PagedModel<Airquality>> between(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime, @Parameter(hidden = true) Pageable pageable)
    {
        Page p = airqualityRepository.findAirqualitiesByTimeAfterAndTimeBefore(startTime, endTime, pageable);
        return new ResponseEntity<PagedModel<Airquality>>(airqualityPageResourceAssembler.toModel(p, airqualityResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get the average air quality of all sensors for the given timeframe")
    @GetMapping("/timeframe/average")
    EntityModel<Airquality> betweenAverage(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime)
    {
        //todo
        //todo avg entity
        return one((long) 1);
    }

    @Operation(summary = "Get a single air quality measurement")
    @GetMapping("/{airqualityId}")
    EntityModel<Airquality> one(@PathVariable Long airqualityId)
    {
        Airquality airquality = airqualityRepository.findById(airqualityId)
                .orElseThrow(() -> new AirqualityNotFoundException(airqualityId));

        return airqualityResourceAssembler.toModel(airquality);
    }

    @Operation(summary = "Get all air quality measurements of a specific sensor")
    @GetMapping("/bySensor/{sensorId}")
    public ResponseEntity<PagedModel<Airquality>> bySensor(@PathVariable Long sensorId, @Parameter(hidden = true) Pageable pageable) {
        Page p = airqualityRepository.findAirqualitiesBySensorId(sensorId, pageable);
        return new ResponseEntity<PagedModel<Airquality>>(airqualityPageResourceAssembler.toModel(p, airqualityResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get all air quality measures of a specific sensor within a given timeframe")
    @GetMapping("/bySensor/{sensorId}/timeframe")
    public ResponseEntity<PagedModel<Airquality>> bySensorInTimeframe(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime, @PathVariable Long sensorId, @Parameter(hidden = true) Pageable pageable)
    {
        //todo
        return all(pageable);
    }

    @Operation(summary = "Get the latest air quality measurement of a specific sensor")
    @GetMapping("/bySensor/{sensorId}/latest")
    public EntityModel<Airquality> bySensorLatest(@PathVariable Long sensorId){
        //todo implement
        return one((long) 1);
    }

    @Operation(summary = "Get the average air quality of a specific sensor within a given timeframe")
    @GetMapping("bySensor/{sensorId}/timeframe/average")
    ResponseEntity<PagedModel<Airquality>> averageBySensor(
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime,
            @PathVariable Long sensorId,
            @Parameter(hidden = true) Pageable pageable)
    {
        //todo
        return all(pageable);
    }
}
