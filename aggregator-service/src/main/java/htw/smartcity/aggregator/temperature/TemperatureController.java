package htw.smartcity.aggregator.temperature;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * The type Temperature controller.
 */
@RestController
@RequestMapping(path = "/temperatures")
@SecurityRequirement(name = "basic")
@Tag(name = "Temperature Measures", description = "Endpoint to get temperature measures")
public class TemperatureController {
    private TemperatureRepository temperatureRepository;
    private TemperatureResourceAssembler temperatureResourceAssembler;
    private TemperaturePageResourceAssembler temperaturePageResourceAssembler;

    /**
     * Instantiates a new Temperature controller.
     *
     * @param temperatureRepository            the temperature repository
     * @param temperatureResourceAssembler     the temperature resource assembler
     * @param temperaturePageResourceAssembler the temperature page resource assembler
     */
    public TemperatureController(TemperatureRepository temperatureRepository, TemperatureResourceAssembler temperatureResourceAssembler, TemperaturePageResourceAssembler temperaturePageResourceAssembler) {
        this.temperatureRepository = temperatureRepository;
        this.temperatureResourceAssembler = temperatureResourceAssembler;
        this.temperaturePageResourceAssembler = temperaturePageResourceAssembler;
    }

    /**
     * All response entity.
     *
     * @param pageable the pageable
     * @return the response entity
     */
    @Operation(summary = "Get all temperature measurements")
    @PageableAsQueryParam
    @GetMapping("/")
    ResponseEntity<PagedModel<Temperature>> all(@Parameter(hidden = true) Pageable pageable)
    {
        Page p = temperatureRepository.findAll(pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }

    /**
     * Latest response entity.
     *
     * @param pageable the pageable
     * @return the response entity
     */
    @Operation(summary = "Get the latest measurements of all temperature sensors")
    @PageableAsQueryParam
    @GetMapping("/latest")
    ResponseEntity<PagedModel<Temperature>> latest(@Parameter(hidden = true) Pageable pageable)
    {
        Page p = temperatureRepository.findLatest(pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }

    /**
     * Between response entity.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @param pageable  the pageable
     * @return the response entity
     */
    @Operation(summary = "Get all temperature measurements of all sensors in a given timeframe")
    @PageableAsQueryParam
    @GetMapping("/timeframe")
    ResponseEntity<PagedModel<Temperature>> between(@RequestParam Instant startTime, @RequestParam Instant endTime, @Parameter(hidden = true) Pageable pageable)
    {
        Page p = temperatureRepository.findTemperaturesByTimeAfterAndTimeBefore(LocalDateTime.ofInstant(startTime, ZoneOffset.UTC), LocalDateTime.ofInstant(endTime, ZoneOffset.UTC), pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }


    /**
     * One entity model.
     *
     * @param temperatureId the temperature id
     * @return the entity model
     */
    @Operation(summary = "Get a single temperature measurement")
    @GetMapping("/{temperatureId}")
    EntityModel<Temperature> one(@PathVariable Long temperatureId)
    {
        Temperature temperature = temperatureRepository.findById(temperatureId)
                .orElseThrow(() -> new TemperatureNotFoundException(temperatureId));

        return temperatureResourceAssembler.toModel(temperature);
    }

    /**
     * By sensor response entity.
     *
     * @param sensorId the sensor id
     * @param pageable the pageable
     * @return the response entity
     */
    @Operation(summary = "Get all temperature measurements of a specific sensor")
    @GetMapping("/bySensor/{sensorId}")
    public ResponseEntity<PagedModel<Temperature>> bySensor(@PathVariable Long sensorId, @Parameter(hidden = true) Pageable pageable) {
        Page p = temperatureRepository.findTemperaturesBySensorId(sensorId, pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }

    /**
     * By sensor in timeframe response entity.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @param sensorId  the sensor id
     * @param pageable  the pageable
     * @return the response entity
     */
    @Operation(summary = "Get all temperatures measures of a specific sensor within a given timeframe")
    @GetMapping("/bySensor/{sensorId}/timeframe")
    public ResponseEntity<PagedModel<Temperature>> bySensorInTimeframe(@RequestParam Instant startTime, @RequestParam Instant endTime, @PathVariable Long sensorId, @Parameter(hidden = true) Pageable pageable)
    {
        Page p = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(sensorId, LocalDateTime.ofInstant(startTime, ZoneOffset.UTC), LocalDateTime.ofInstant(endTime, ZoneOffset.UTC),
                                                                                  pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p,
        temperatureResourceAssembler), HttpStatus.OK);
    }

    /**
     * By sensor latest entity model.
     *
     * @param sensorId the sensor id
     * @return the entity model
     */
    @Operation(summary = "Get the latest measurement of a specific sensor")
    @GetMapping("/bySensor/{sensorId}/latest")
    public EntityModel<Temperature> bySensorLatest(@PathVariable Long sensorId){
        Temperature temperature = temperatureRepository.findFirstBySensorIdOrderByTimeDesc(sensorId);
        return temperatureResourceAssembler.toModel(temperature);
    }
}
