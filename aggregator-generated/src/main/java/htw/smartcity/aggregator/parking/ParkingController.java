package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.*;
import htw.smartcity.aggregator.temperature.*;
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

import java.util.Date;

@RestController
@RequestMapping(path = "/parking")
@Tag(name = "Parking Measures", description = "Endpoint to get parking measures")
public class ParkingController {
    private ParkingRepository parkingRepository;
    private ParkingGroupRepository parkingGroupRepository;
    private SensorRepository sensorRepository;

    private ParkingResourceAssembler parkingResourceAssembler;
    private ParkingGroupResourceAssembler parkingGroupResourceAssembler;
    private SensorResourceAssembler sensorResourceAssembler;

    private ParkingPageResourceAssembler parkingPageResourceAssembler;
    private ParkingGroupPageResourceAssembler parkingGroupPageResourceAssembler;
    private SensorPageResourceAssembler sensorPageResourceAssembler;

    public ParkingController(ParkingRepository parkingRepository, ParkingGroupRepository parkingGroupRepository, SensorRepository sensorRepository, ParkingResourceAssembler parkingResourceAssembler, ParkingGroupResourceAssembler parkingGroupResourceAssembler, SensorResourceAssembler sensorResourceAssembler, ParkingPageResourceAssembler parkingPageResourceAssembler, ParkingGroupPageResourceAssembler parkingGroupPageResourceAssembler, SensorPageResourceAssembler sensorPageResourceAssembler) {
        this.parkingRepository = parkingRepository;
        this.parkingGroupRepository = parkingGroupRepository;
        this.sensorRepository = sensorRepository;
        this.parkingResourceAssembler = parkingResourceAssembler;
        this.parkingGroupResourceAssembler = parkingGroupResourceAssembler;
        this.sensorResourceAssembler = sensorResourceAssembler;
        this.parkingPageResourceAssembler = parkingPageResourceAssembler;
        this.parkingGroupPageResourceAssembler = parkingGroupPageResourceAssembler;
        this.sensorPageResourceAssembler = sensorPageResourceAssembler;
    }

    @Operation(summary = "Get all parking groups")
    @PageableAsQueryParam
    @GetMapping("/")
    ResponseEntity<PagedModel<ParkingGroup>> allGroups(@Parameter(hidden = true) Pageable pageable)
    {
        Page p = parkingGroupRepository.findAll(pageable);
        return new ResponseEntity<PagedModel<ParkingGroup>>(parkingGroupPageResourceAssembler.toModel(p, parkingGroupResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get all sensors in a specific parking group")
    @PageableAsQueryParam
    @GetMapping("/{groupId}/sensors")
    ResponseEntity<PagedModel<Sensor>> sensorsInGroup(@Parameter(hidden = true) Pageable pageable, @PathVariable Long groupId)
    {
        Page p = parkingGroupRepository.findParkingSensorsByGroupId(groupId, pageable);
        return new ResponseEntity<PagedModel<Sensor>>(sensorPageResourceAssembler.toModel(p, sensorResourceAssembler), HttpStatus.OK);
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
    public ResponseEntity<PagedModel<Parking>> bySensorInTimeframe(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime, @PathVariable Long sensorId, @Parameter(hidden = true) Pageable pageable)
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
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime,
            @PathVariable Long sensorId,
            @Parameter(hidden = true) Pageable pageable)
    {
        //todo
        return bySensor(pageable, sensorId);
    }

}
