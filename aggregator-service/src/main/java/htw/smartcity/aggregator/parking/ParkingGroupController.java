package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorPageResourceAssembler;
import htw.smartcity.aggregator.sensor.SensorRepository;
import htw.smartcity.aggregator.sensor.SensorResourceAssembler;
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
@RequestMapping(path = "/parking/group")
@Tag(name = "Parking Measures", description = "Endpoint to get parking measures")
public class ParkingGroupController {

    private ParkingRepository parkingRepository;
    private ParkingGroupRepository parkingGroupRepository;
    private SensorRepository sensorRepository;
    private ParkingGroupCounterRepository parkingGroupCounterRepository;

    private ParkingResourceAssembler parkingResourceAssembler;
    private ParkingGroupResourceAssembler parkingGroupResourceAssembler;
    private SensorResourceAssembler sensorResourceAssembler;
    private ParkingGroupCounterResourceAssembler parkingGroupCounterResourceAssembler;

    private ParkingPageResourceAssembler parkingPageResourceAssembler;
    private ParkingGroupPageResourceAssembler parkingGroupPageResourceAssembler;
    private SensorPageResourceAssembler sensorPageResourceAssembler;
    private ParkingGroupCounterPageResourceAssembler parkingGroupCounterPageResourceAssembler;

    public ParkingGroupController(ParkingRepository parkingRepository, ParkingGroupRepository parkingGroupRepository, SensorRepository sensorRepository, ParkingGroupCounterRepository parkingGroupCounterRepository, ParkingResourceAssembler parkingResourceAssembler, ParkingGroupResourceAssembler parkingGroupResourceAssembler, SensorResourceAssembler sensorResourceAssembler, ParkingGroupCounterResourceAssembler parkingGroupCounterResourceAssembler, ParkingPageResourceAssembler parkingPageResourceAssembler, ParkingGroupPageResourceAssembler parkingGroupPageResourceAssembler, SensorPageResourceAssembler sensorPageResourceAssembler, ParkingGroupCounterPageResourceAssembler parkingGroupCounterPageResourceAssembler) {
        this.parkingRepository = parkingRepository;
        this.parkingGroupRepository = parkingGroupRepository;
        this.sensorRepository = sensorRepository;
        this.parkingGroupCounterRepository = parkingGroupCounterRepository;
        this.parkingResourceAssembler = parkingResourceAssembler;
        this.parkingGroupResourceAssembler = parkingGroupResourceAssembler;
        this.sensorResourceAssembler = sensorResourceAssembler;
        this.parkingGroupCounterResourceAssembler = parkingGroupCounterResourceAssembler;
        this.parkingPageResourceAssembler = parkingPageResourceAssembler;
        this.parkingGroupPageResourceAssembler = parkingGroupPageResourceAssembler;
        this.sensorPageResourceAssembler = sensorPageResourceAssembler;
        this.parkingGroupCounterPageResourceAssembler = parkingGroupCounterPageResourceAssembler;
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

    @Operation(summary = "Get all aggregated group counters of all groups")
    @PageableAsQueryParam
    @GetMapping("/overview")
    ResponseEntity<List<PagedModel<ParkingGroupCounter>>> groupOverview(@Parameter(hidden = true) Pageable pageable)
    {
        List<PagedModel<ParkingGroupCounter>> parkingGroupCounterList = new ArrayList<>();
        parkingGroupRepository.findAll().forEach(parkingGroup -> {
            Page p = parkingGroupCounterRepository.findAllByParkingGroupId(pageable, parkingGroup.getId());
            PagedModel<ParkingGroupCounter> pagedModel = parkingGroupCounterPageResourceAssembler.toModel(p, parkingGroupCounterResourceAssembler);
            parkingGroupCounterList.add(pagedModel);
        });
        return new ResponseEntity<>(parkingGroupCounterList, HttpStatus.OK);
    }

    @Operation(summary = "Get the latest aggregated group counter of all groups")
    @GetMapping("/overview/latest")
    ResponseEntity<List<EntityModel<ParkingGroupCounter>>> groupOverviewLatest()
    {
        List<EntityModel<ParkingGroupCounter>> parkingGroupCounterList = new ArrayList<>();
        parkingGroupRepository.findAll().forEach(parkingGroup -> {
            ParkingGroupCounter parkingGroupCounter = parkingGroupCounterRepository.findFirstByParkingGroupOrderByTimeDesc(parkingGroup);
            EntityModel<ParkingGroupCounter> pagedModel = parkingGroupCounterResourceAssembler.toModel(parkingGroupCounter);
            parkingGroupCounterList.add(pagedModel);
        });
        return new ResponseEntity<>(parkingGroupCounterList, HttpStatus.OK);
    }

    @Operation(summary = "Get all aggregated group counters of a specific groups")
    @PageableAsQueryParam
    @GetMapping("/{groupId}/overview")
    ResponseEntity<PagedModel<ParkingGroupCounter>> oneGroupOverview(@Parameter(hidden = true) Pageable pageable, @PathVariable Long groupId)
    {
        Page p = parkingGroupCounterRepository.findAllByParkingGroupId(pageable, groupId);
        return new ResponseEntity<>(parkingGroupCounterPageResourceAssembler.toModel(p, parkingGroupCounterResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get the latest aggregated group counter of a specific groups")
    @GetMapping("/{groupId}/overview/latest")
    EntityModel<ParkingGroupCounter> oneGroupOverviewLatest(@PathVariable Long groupId)
    {
        ParkingGroupCounter parkingGroupCounter = parkingGroupCounterRepository.findFirstByParkingGroupIdOrderByTimeDesc(groupId);
        return parkingGroupCounterResourceAssembler.toModel(parkingGroupCounter);
    }

    @Operation(summary = "Get all measures of a specific sensor")
    @PageableAsQueryParam
    @GetMapping("/{groupId}/{sensorId}")
    ResponseEntity<PagedModel<Parking>> byGroupAndSensor(@Parameter(hidden = true) Pageable pageable, @PathVariable Long groupId, @PathVariable Long sensorId)
    {
        return bySensor(pageable, sensorId);
    }

    @Operation(summary = "Get the latest measure of a specific sensor")
    @GetMapping("/{groupId}/{sensorId}/latest")
    EntityModel<Parking> byGroupAndSensorLatest(@PathVariable Long groupId, @PathVariable Long sensorId)
    {
        return bySensorLatest(sensorId);
    }

    @Operation(summary = "Get all measurements of a specific group")
    @PageableAsQueryParam
    @GetMapping("/{groupId}")
    ResponseEntity<List<PagedModel<Parking>>> groupMeasures(@Parameter(hidden = true) Pageable pageable, @PathVariable Long groupId)
    {
        List<PagedModel<Parking>> parkingList = new ArrayList<>();
        ParkingGroup parkingGroup = parkingGroupRepository.findById(groupId)
                .orElseThrow(() -> new ParkingGroupNotFoundException(groupId));
        parkingGroup.getSensors().forEach(sensor -> {
            Page p = parkingRepository.findParkingsBySensorId(sensor.getId(), pageable);
            PagedModel<Parking> pagedModel = parkingPageResourceAssembler.toModel(p, parkingResourceAssembler);
            parkingList.add(pagedModel);
        });

        return new ResponseEntity<>(parkingList, HttpStatus.OK);
    }

    @Operation(summary = "Get the latest measurements of a specific group")
    @PageableAsQueryParam
    @GetMapping("/{groupId}/latest")
    ResponseEntity<List<EntityModel<Parking>>> groupMeasuresLatest(@Parameter(hidden = true) Pageable pageable, @PathVariable Long groupId) {
        List<EntityModel<Parking>> parkingList = new ArrayList<>();
        ParkingGroup parkingGroup = parkingGroupRepository.findById(groupId)
                .orElseThrow(() -> new ParkingGroupNotFoundException(groupId));
        parkingGroup.getSensors().forEach(sensor -> {
            Parking parking = parkingRepository.findFirstBySensorIdOrderByTimeDesc(sensor.getId());
            EntityModel<Parking> entityModel = parkingResourceAssembler.toModel(parking);
            parkingList.add(entityModel);
        });

        return new ResponseEntity<>(parkingList, HttpStatus.OK);
    }

    public EntityModel<Parking> bySensorLatest(@PathVariable Long sensorId){
        Parking parking = parkingRepository.findFirstBySensorIdOrderByTimeDesc(sensorId);
        return parkingResourceAssembler.toModel(parking);
    }

    ResponseEntity<PagedModel<Parking>> bySensor(@Parameter(hidden = true) Pageable pageable, @PathVariable Long sensorId)
    {
        Page p = parkingRepository.findParkingsBySensorId(sensorId, pageable);
        return new ResponseEntity<PagedModel<Parking>>(parkingPageResourceAssembler.toModel(p, parkingResourceAssembler), HttpStatus.OK);
    }

}
