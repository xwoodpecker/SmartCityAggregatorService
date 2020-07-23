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

import javax.persistence.Convert;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/temperatures")
@Tag(name = "Temperature Measures", description = "Endpoint to get temperature measures")
public class TemperatureController {
    private TemperatureRepository temperatureRepository;
    private TemperatureResourceAssembler temperatureResourceAssembler;
    private TemperaturePageResourceAssembler temperaturePageResourceAssembler;

    public TemperatureController(TemperatureRepository temperatureRepository, TemperatureResourceAssembler temperatureResourceAssembler, TemperaturePageResourceAssembler temperaturePageResourceAssembler) {
        this.temperatureRepository = temperatureRepository;
        this.temperatureResourceAssembler = temperatureResourceAssembler;
        this.temperaturePageResourceAssembler = temperaturePageResourceAssembler;
    }

    @Operation(summary = "Get all temperature measurements")
    @PageableAsQueryParam
    @GetMapping("/")
    ResponseEntity<PagedModel<Temperature>> all(@Parameter(hidden = true) Pageable pageable)
    {
        Page p = temperatureRepository.findAll(pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get the latest measurements of all temperature sensors")
    @PageableAsQueryParam
    @GetMapping("/latest")
    ResponseEntity<PagedModel<Temperature>> latest(@Parameter(hidden = true) Pageable pageable)
    {
        //todo
        return all(pageable);
    }

    @Operation(summary = "Get the average temperature of the latest measurements of all temperature sensors")
    @GetMapping("/latest/average")
    ResponseEntity<PagedModel<Average>> latestAverage(@Parameter(hidden = true) Pageable pageable)
    {
        //todo
        //todo average entity?
        return null;
    }

    @Operation(summary = "Get all temperature measurements of all sensors in a given timeframe")
    @PageableAsQueryParam
    @GetMapping("/timeframe")
    ResponseEntity<PagedModel<Temperature>> between(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime, @Parameter(hidden = true) Pageable pageable)
    {
        Page p = temperatureRepository.findTemperaturesByTimeBeforeAndTimeAfter(endTime, startTime, pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get the average temperature of all sensors for the given timeframe")
    @GetMapping("/timeframe/average")
    ResponseEntity<PagedModel<Average>> betweenAverage(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
                                                       @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime, @Parameter(hidden = true) Pageable pageable)
    {
        Page p = temperatureRepository.findTemperaturesByTimeBeforeAndTimeAfter(endTime, startTime, pageable);
        List l = p.getContent();
        double sum = 0, count = 0;
        for (var ele: l)
        {
            sum += Double.parseDouble(ele.toString());
            count++;
        }
        Average avg = new Average(Average.SensorType.TEMPERATURE, startTime, endTime, sum/count);

        // todo return
        return new ResponseEntity<PagedModel<Average>>(temperaturePageResourceAssembler.toModel(p,
                                                                                          temperatureResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get a single temperature measurement")
    @GetMapping("/{temperatureId}")
    EntityModel<Temperature> one(@PathVariable Long temperatureId)
    {
        Temperature temperature = temperatureRepository.findById(temperatureId)
                .orElseThrow(() -> new TemperatureNotFoundException(temperatureId));

        return temperatureResourceAssembler.toModel(temperature);
    }

    @Operation(summary = "Get all temperature measurements of a specific sensor")
    @GetMapping("/bySensor/{sensorId}")
    public ResponseEntity<PagedModel<Temperature>> bySensor(@PathVariable Long sensorId, @Parameter(hidden = true) Pageable pageable) {
        Page p = temperatureRepository.findTemperaturesBySensorId(sensorId, pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p, temperatureResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get all temperatures measures of a specific sensor within a given timeframe")
    @GetMapping("/bySensor/{sensorId}/timeframe")
    public ResponseEntity<PagedModel<Temperature>> bySensorInTimeframe(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime, @PathVariable Long sensorId, @Parameter(hidden = true) Pageable pageable)
    {
        //todo
        Page p = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(sensorId, startTime, endTime,
                                                                                  pageable);
        return new ResponseEntity<PagedModel<Temperature>>(temperaturePageResourceAssembler.toModel(p,
        temperatureResourceAssembler), HttpStatus.OK);
         //return all(pageable);
    }

    @Operation(summary = "Get the latest measurement of a specific sensor")
    @GetMapping("/bySensor/{sensorId}/latest")
    public EntityModel<Temperature> bySensorLatest(@PathVariable Long sensorId){
        //todo implement
        return one((long) 1);
    }

    @Operation(summary = "Get the average temperature of a specific sensor within a given timeframe")
    @GetMapping("bySensor/{sensorId}/timeframe/average")
    ResponseEntity<PagedModel<Temperature>> averageBySensor(
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime,
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
