package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.average.Average;
import htw.smartcity.aggregator.sensor.SensorController;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * The type Temperature controller.
 */
@RestController
@RequestMapping(path = "/temperatures")
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
        //todo
        return all(pageable);
    }

    /**
     * Latest average response entity.
     *
     * @param pageable the pageable
     * @return the response entity
     */
    @Operation(summary = "Get the average temperature of the latest measurements of all temperature sensors")
    @GetMapping("/latest/average")
    ResponseEntity<PagedModel<Average>> latestAverage(@Parameter(hidden = true) Pageable pageable)
    {
        //todo
        //todo average entity?
        return null;
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
     * Between average response entity.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @param pageable  the pageable
     * @return the response entity
     */
    @Operation(summary = "Get the average temperature of all sensors for the given timeframe")
    @GetMapping("/timeframe/average")
    ResponseEntity<PagedModel<Average>> betweenAverage(@RequestParam Instant startTime, @RequestParam Instant endTime, @Parameter(hidden = true) Pageable pageable)
    {
        Page p = temperatureRepository.findTemperaturesByTimeAfterAndTimeBefore(LocalDateTime.ofInstant(startTime, ZoneOffset.UTC), LocalDateTime.ofInstant(endTime, ZoneOffset.UTC), pageable);
        List l = p.getContent();
        double sum = 0, count = 0;
        for (var ele: l)
        {
            sum += Double.parseDouble(ele.toString());
            count++;
        }
        Average avg = new Average(Average.SensorType.TEMPERATURE, LocalDateTime.ofInstant(startTime, ZoneOffset.UTC), LocalDateTime.ofInstant(endTime, ZoneOffset.UTC), sum/count);

        // todo return
        return new ResponseEntity<PagedModel<Average>>(temperaturePageResourceAssembler.toModel(p,
                                                                                          temperatureResourceAssembler), HttpStatus.OK);
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
        //todo
        Page p = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(sensorId, LocalDateTime.ofInstant(startTime, ZoneOffset.UTC), LocalDateTime.ofInstant(endTime, ZoneOffset.UTC),
                                                                                  pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p,
        temperatureResourceAssembler), HttpStatus.OK);
         //return all(pageable);
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
    @Operation(summary = "Get the average temperature of a specific sensor within a given timeframe")
    @GetMapping("bySensor/{sensorId}/timeframe/average")
    ResponseEntity<PagedModel<Temperature>> averageBySensor(
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime,
            @PathVariable Long sensorId,
            @Parameter(hidden = true) Pageable pageable)
    {
        //todo
        /*Page p = temperatureRepository.findTemperaturesBySensorIdAndByTimeBetween(sensorId, startTime, endTime,
                                                                                  pageable);
        List l = p.getContent();
        double sum = 0, count = 0;
        for (var ele: l)
        {
            sum += Double.parseDouble(ele.toString());
            count++;
        }
        // todo wie auch immer Sensor obj bekommen aus sensorId
        //Average avg = new Average(sensorId, Average.SensorType.TEMPERATURE, startTime, endTime, sum/count);

        // todo return

         */
        return all(pageable);
    }
}
