package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.Sensor;
import htw.smartcity.aggregator.sensor.SensorPageResourceAssembler;
import htw.smartcity.aggregator.sensor.SensorRepository;
import htw.smartcity.aggregator.sensor.SensorResourceAssembler;
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

import java.util.ArrayList;
import java.util.List;

/**
 * The type Parking group controller.
 */
@RestController
@RequestMapping(path = "/parking/group")
@SecurityRequirement(name = "basic")
@Tag(name = "Parking Measures", description = "Endpoint to get parking measures")
public class ParkingGroupController {

    private final ParkingRepository parkingRepository;
    private final ParkingGroupRepository parkingGroupRepository;
    private final ParkingGroupCounterRepository parkingGroupCounterRepository;

    private final ParkingResourceAssembler parkingResourceAssembler;
    private final ParkingGroupResourceAssembler parkingGroupResourceAssembler;
    private final SensorResourceAssembler sensorResourceAssembler;
    private final ParkingGroupCounterResourceAssembler parkingGroupCounterResourceAssembler;

    private final ParkingPageResourceAssembler parkingPageResourceAssembler;
    private final ParkingGroupPageResourceAssembler parkingGroupPageResourceAssembler;
    private final SensorPageResourceAssembler sensorPageResourceAssembler;
    private final ParkingGroupCounterPageResourceAssembler parkingGroupCounterPageResourceAssembler;

    private final ParkingController parkingController;

    /**
     * Instantiates a new Parking group controller.
     *
     * @param parkingRepository                        the parking repository
     * @param parkingGroupRepository                   the parking group repository
     * @param sensorRepository                         the sensor repository
     * @param parkingGroupCounterRepository            the parking group counter repository
     * @param parkingResourceAssembler                 the parking resource assembler
     * @param parkingGroupResourceAssembler            the parking group resource assembler
     * @param sensorResourceAssembler                  the sensor resource assembler
     * @param parkingGroupCounterResourceAssembler     the parking group counter resource assembler
     * @param parkingPageResourceAssembler             the parking page resource assembler
     * @param parkingGroupPageResourceAssembler        the parking group page resource assembler
     * @param sensorPageResourceAssembler              the sensor page resource assembler
     * @param parkingGroupCounterPageResourceAssembler the parking group counter page resource assembler
     * @param parkingController                        the parking controller
     */
    public ParkingGroupController(ParkingRepository parkingRepository, ParkingGroupRepository parkingGroupRepository, SensorRepository sensorRepository, ParkingGroupCounterRepository parkingGroupCounterRepository, ParkingResourceAssembler parkingResourceAssembler, ParkingGroupResourceAssembler parkingGroupResourceAssembler, SensorResourceAssembler sensorResourceAssembler, ParkingGroupCounterResourceAssembler parkingGroupCounterResourceAssembler, ParkingPageResourceAssembler parkingPageResourceAssembler, ParkingGroupPageResourceAssembler parkingGroupPageResourceAssembler, SensorPageResourceAssembler sensorPageResourceAssembler, ParkingGroupCounterPageResourceAssembler parkingGroupCounterPageResourceAssembler, ParkingController parkingController) {
        this.parkingRepository = parkingRepository;
        this.parkingGroupRepository = parkingGroupRepository;
        this.parkingGroupCounterRepository = parkingGroupCounterRepository;
        this.parkingResourceAssembler = parkingResourceAssembler;
        this.parkingGroupResourceAssembler = parkingGroupResourceAssembler;
        this.sensorResourceAssembler = sensorResourceAssembler;
        this.parkingGroupCounterResourceAssembler = parkingGroupCounterResourceAssembler;
        this.parkingPageResourceAssembler = parkingPageResourceAssembler;
        this.parkingGroupPageResourceAssembler = parkingGroupPageResourceAssembler;
        this.sensorPageResourceAssembler = sensorPageResourceAssembler;
        this.parkingGroupCounterPageResourceAssembler = parkingGroupCounterPageResourceAssembler;
        this.parkingController = parkingController;
    }


    /**
     * All groups response entity.
     *
     * @param pageable the pageable
     * @return the response entity
     */
    @Operation(summary = "Get all parking groups")
    @PageableAsQueryParam
    @GetMapping("/")
    ResponseEntity<PagedModel<ParkingGroup>> allGroups(@Parameter(hidden = true) Pageable pageable)
    {
        Page p = parkingGroupRepository.findAll(pageable);
        return new ResponseEntity<PagedModel<ParkingGroup>>(parkingGroupPageResourceAssembler.toModel(p, parkingGroupResourceAssembler), HttpStatus.OK);
    }

    /**
     * Sensors in group response entity.
     *
     * @param pageable the pageable
     * @param groupId  the group id
     * @return the response entity
     */
    @Operation(summary = "Get all sensors in a specific parking group")
    @PageableAsQueryParam
    @GetMapping("/{groupId}/sensors")
    ResponseEntity<PagedModel<Sensor>> sensorsInGroup(@Parameter(hidden = true) Pageable pageable, @PathVariable Long groupId)
    {
        Page p = parkingGroupRepository.findParkingSensorsByGroupId(groupId, pageable);
        return new ResponseEntity<PagedModel<Sensor>>(sensorPageResourceAssembler.toModel(p, sensorResourceAssembler), HttpStatus.OK);
    }

    /**
     * Group overview response entity.
     *
     * @param pageable the pageable
     * @return the response entity
     */
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

    /**
     * Group overview latest response entity.
     *
     * @return the response entity
     */
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

    /**
     * One group overview response entity.
     *
     * @param pageable the pageable
     * @param groupId  the group id
     * @return the response entity
     */
    @Operation(summary = "Get all aggregated group counters of a specific groups")
    @PageableAsQueryParam
    @GetMapping("/{groupId}/overview")
    ResponseEntity<PagedModel<ParkingGroupCounter>> oneGroupOverview(@Parameter(hidden = true) Pageable pageable, @PathVariable Long groupId)
    {
        Page p = parkingGroupCounterRepository.findAllByParkingGroupId(pageable, groupId);
        return new ResponseEntity<>(parkingGroupCounterPageResourceAssembler.toModel(p, parkingGroupCounterResourceAssembler), HttpStatus.OK);
    }

    /**
     * One group overview latest entity model.
     *
     * @param groupId the group id
     * @return the entity model
     */
    @Operation(summary = "Get the latest aggregated group counter of a specific groups")
    @GetMapping("/{groupId}/overview/latest")
    EntityModel<ParkingGroupCounter> oneGroupOverviewLatest(@PathVariable Long groupId)
    {
        ParkingGroupCounter parkingGroupCounter = parkingGroupCounterRepository.findFirstByParkingGroupIdOrderByTimeDesc(groupId);
        return parkingGroupCounterResourceAssembler.toModel(parkingGroupCounter);
    }

    /**
     * By group and sensor response entity.
     *
     * @param pageable the pageable
     * @param groupId  the group id
     * @param sensorId the sensor id
     * @return the response entity
     */
    @Operation(summary = "Get all measures of a specific sensor")
    @PageableAsQueryParam
    @GetMapping("/{groupId}/{sensorId}")
    ResponseEntity<PagedModel<Parking>> byGroupAndSensor(@Parameter(hidden = true) Pageable pageable, @PathVariable Long groupId, @PathVariable Long sensorId)
    {
        return parkingController.bySensor(pageable, sensorId);
    }

    /**
     * By group and sensor latest entity model.
     *
     * @param groupId  the group id
     * @param sensorId the sensor id
     * @return the entity model
     */
    @Operation(summary = "Get the latest measure of a specific sensor")
    @GetMapping("/{groupId}/{sensorId}/latest")
    EntityModel<Parking> byGroupAndSensorLatest(@PathVariable Long groupId, @PathVariable Long sensorId)
    {
        return parkingController.bySensorLatest(sensorId);
    }

    /**
     * Group measures response entity.
     *
     * @param pageable the pageable
     * @param groupId  the group id
     * @return the response entity
     */
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

    /**
     * Group measures latest response entity.
     *
     * @param pageable the pageable
     * @param groupId  the group id
     * @return the response entity
     */
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



}
