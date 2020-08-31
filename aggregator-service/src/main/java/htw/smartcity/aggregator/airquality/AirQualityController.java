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

/**
 * The type Air quality controller.
 */
@RestController
@RequestMapping(path = "/airQualities")
@Tag(name = "Air Quality Measures", description = "Endpoint to get air quality measures")
public class AirQualityController {
    private AirQualityRepository airQualityRepository;
    private AirQualityResourceAssembler airQualityResourceAssembler;
    private AirQualityPageResourceAssembler airQualityPageResourceAssembler;

    /**
     * Instantiates a new Air quality controller.
     *
     * @param airQualityRepository            the air quality repository
     * @param airQualityResourceAssembler     the air quality resource assembler
     * @param airQualityPageResourceAssembler the air quality page resource assembler
     */
    public AirQualityController(AirQualityRepository airQualityRepository, AirQualityResourceAssembler airQualityResourceAssembler, AirQualityPageResourceAssembler airQualityPageResourceAssembler) {
        this.airQualityRepository = airQualityRepository;
        this.airQualityResourceAssembler = airQualityResourceAssembler;
        this.airQualityPageResourceAssembler = airQualityPageResourceAssembler;
    }

    /**
     * All response entity.
     *
     * @param pageable the pageable
     * @return the response entity
     */
    @Operation(summary = "Get all air quality measurements")
    @PageableAsQueryParam
    @GetMapping("/")
    ResponseEntity<PagedModel<AirQuality>> all(@Parameter(hidden = true) Pageable pageable)
    {
        Page p = airQualityRepository.findAll(pageable);
        return new ResponseEntity<PagedModel<AirQuality>>(airQualityPageResourceAssembler.toModel(p, airQualityResourceAssembler), HttpStatus.OK);
    }

    /**
     * Latest response entity.
     *
     * @param pageable the pageable
     * @return the response entity
     */
    @Operation(summary = "Get the latest measurements of all air quality sensors")
    @PageableAsQueryParam
    @GetMapping("/latest")
    ResponseEntity<PagedModel<AirQuality>> latest(@Parameter(hidden = true) Pageable pageable)
    {
        //todo
        return all(pageable);
    }

    /**
     * Latest average entity model.
     *
     * @return the entity model
     */
    @Operation(summary = "Get the average air quality of the latest measurements of all sensors")
    @GetMapping("/latest/average")
    EntityModel<AirQuality> latestAverage()
    {
        //todo
        //todo average entity?
        return one((long) 1);
    }

    /**
     * Between response entity.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @param pageable  the pageable
     * @return the response entity
     */
    @Operation(summary = "Get all air quality measurements of all sensors in a given timeframe")
    @PageableAsQueryParam
    @GetMapping("/timeframe")
    ResponseEntity<PagedModel<AirQuality>> between(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime, @Parameter(hidden = true) Pageable pageable)
    {
        Page p = airQualityRepository.findAirQualitiesByTimeAfterAndTimeBefore(startTime, endTime, pageable);
        return new ResponseEntity<PagedModel<AirQuality>>(airQualityPageResourceAssembler.toModel(p, airQualityResourceAssembler), HttpStatus.OK);
    }

    /**
     * Between average entity model.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the entity model
     */
    @Operation(summary = "Get the average air quality of all sensors for the given timeframe")
    @GetMapping("/timeframe/average")
    EntityModel<AirQuality> betweenAverage(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime)
    {
        //todo
        //todo avg entity
        return one((long) 1);
    }

    /**
     * One entity model.
     *
     * @param airQualityId the air quality id
     * @return the entity model
     */
    @Operation(summary = "Get a single air quality measurement")
    @GetMapping("/{airQualityId}")
    EntityModel<AirQuality> one(@PathVariable Long airQualityId)
    {
        AirQuality airQuality = airQualityRepository.findById(airQualityId)
                .orElseThrow(() -> new AirQualityNotFoundException(airQualityId));

        return airQualityResourceAssembler.toModel(airQuality);
    }

    /**
     * By sensor response entity.
     *
     * @param sensorId the sensor id
     * @param pageable the pageable
     * @return the response entity
     */
    @Operation(summary = "Get all air quality measurements of a specific sensor")
    @GetMapping("/bySensor/{sensorId}")
    public ResponseEntity<PagedModel<AirQuality>> bySensor(@PathVariable Long sensorId, @Parameter(hidden = true) Pageable pageable) {
        Page p = airQualityRepository.findAirQualitiesBySensorId(sensorId, pageable);
        return new ResponseEntity<PagedModel<AirQuality>>(airQualityPageResourceAssembler.toModel(p, airQualityResourceAssembler), HttpStatus.OK);
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
    @Operation(summary = "Get all air quality measures of a specific sensor within a given timeframe")
    @GetMapping("/bySensor/{sensorId}/timeframe")
    public ResponseEntity<PagedModel<AirQuality>> bySensorInTimeframe(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime, @PathVariable Long sensorId, @Parameter(hidden = true) Pageable pageable)
    {
        //todo
        return all(pageable);
    }

    /**
     * By sensor latest entity model.
     *
     * @param sensorId the sensor id
     * @return the entity model
     */
    @Operation(summary = "Get the latest air quality measurement of a specific sensor")
    @GetMapping("/bySensor/{sensorId}/latest")
    public EntityModel<AirQuality> bySensorLatest(@PathVariable Long sensorId){
        //todo implement
        return one((long) 1);
    }

    /**
     * Average by sensor response entity.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @param sensorId  the sensor id
     * @param pageable  the pageable
     * @return the response entity
     */
    @Operation(summary = "Get the average air quality of a specific sensor within a given timeframe")
    @GetMapping("bySensor/{sensorId}/timeframe/average")
    ResponseEntity<PagedModel<AirQuality>> averageBySensor(
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime,
            @PathVariable Long sensorId,
            @Parameter(hidden = true) Pageable pageable)
    {
        //todo
        return all(pageable);
    }
}
