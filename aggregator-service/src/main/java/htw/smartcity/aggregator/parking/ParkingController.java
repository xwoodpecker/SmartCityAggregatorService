package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/parking")
@Tag(name = "Parking Measures", description = "Endpoint to get parking measures")
public class ParkingController {
    private final ParkingRepository parkingRepository;

    private final ParkingResourceAssembler parkingResourceAssembler;

    private final ParkingPageResourceAssembler parkingPageResourceAssembler;

    public ParkingController(ParkingRepository parkingRepository, SensorRepository sensorRepository, ParkingResourceAssembler parkingResourceAssembler, SensorResourceAssembler sensorResourceAssembler, ParkingPageResourceAssembler parkingPageResourceAssembler, SensorPageResourceAssembler sensorPageResourceAssembler) {
        this.parkingRepository = parkingRepository;
        this.parkingResourceAssembler = parkingResourceAssembler;
        this.parkingPageResourceAssembler = parkingPageResourceAssembler;
    }


    @Operation(summary = "Get all parking measurements of a specific sensor")
    @PageableAsQueryParam
    @GetMapping("/bySensor/{sensorId}")
    ResponseEntity<PagedModel<Parking>> bySensor(@Parameter(hidden = true) Pageable pageable, @PathVariable Long sensorId)
    {
        Page p = parkingRepository.findParkingsBySensorId(sensorId, pageable);
        return new ResponseEntity<PagedModel<Parking>>(parkingPageResourceAssembler.toModel(p, parkingResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get all measurements of a specific sensor within a given timeframe")
    @GetMapping("/bySensor/{sensorId}/timeframe")
    public ResponseEntity<PagedModel<Parking>> bySensorInTimeframe(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime, @PathVariable Long sensorId, @Parameter(hidden = true) Pageable pageable)
    {
        Page p = parkingRepository.findParkingsByTimeBeforeAndTimeAfterAndSensorId(endTime, startTime, sensorId, pageable);
        return new ResponseEntity<PagedModel<Parking>>(parkingPageResourceAssembler.toModel(p, parkingResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get the latest measurement of a specific sensor")
    @GetMapping("/bySensor/{sensorId}/latest")
    public EntityModel<Parking> bySensorLatest(@PathVariable Long sensorId){
        Parking parking = parkingRepository.findFirstBySensorIdOrderByTimeDesc(sensorId);
        return parkingResourceAssembler.toModel(parking);
    }

    @Operation(summary = "Get the average occupancy of a specific sensor within a given timeframe")
    @GetMapping("bySensor/{sensorId}/timeframe/average")
    ResponseEntity<PagedModel<Parking>> averageBySensor(
            //todo average entity
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime,
            @PathVariable Long sensorId,
            @Parameter(hidden = true) Pageable pageable)
    {
        //todo
        return bySensor(pageable, sensorId);
    }



}
