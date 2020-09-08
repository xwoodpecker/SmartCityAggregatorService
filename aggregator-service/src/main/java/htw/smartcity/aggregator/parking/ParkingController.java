package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.*;
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
 * The type Parking controller.
 */
@RestController
@RequestMapping(path = "/parking")
@SecurityRequirement(name = "basic")
@Tag(name = "Parking Measures", description = "Endpoint to get parking measures")
public class ParkingController {
    private final ParkingRepository parkingRepository;

    private final ParkingResourceAssembler parkingResourceAssembler;

    private final ParkingPageResourceAssembler parkingPageResourceAssembler;

    /**
     * Instantiates a new Parking controller.
     *
     * @param parkingRepository            the parking repository
     * @param sensorRepository             the sensor repository
     * @param parkingResourceAssembler     the parking resource assembler
     * @param sensorResourceAssembler      the sensor resource assembler
     * @param parkingPageResourceAssembler the parking page resource assembler
     * @param sensorPageResourceAssembler  the sensor page resource assembler
     */
    public ParkingController(ParkingRepository parkingRepository, SensorRepository sensorRepository, ParkingResourceAssembler parkingResourceAssembler, SensorResourceAssembler sensorResourceAssembler, ParkingPageResourceAssembler parkingPageResourceAssembler, SensorPageResourceAssembler sensorPageResourceAssembler) {
        this.parkingRepository = parkingRepository;
        this.parkingResourceAssembler = parkingResourceAssembler;
        this.parkingPageResourceAssembler = parkingPageResourceAssembler;
    }


    /**
     * By sensor response entity.
     *
     * @param pageable the pageable
     * @param sensorId the sensor id
     * @return the response entity
     */
    @Operation(summary = "Get all parking measurements of a specific sensor")
    @PageableAsQueryParam
    @GetMapping("/bySensor/{sensorId}")
    ResponseEntity<PagedModel<Parking>> bySensor(@Parameter(hidden = true) Pageable pageable, @PathVariable Long sensorId)
    {
        Page p = parkingRepository.findParkingsBySensorId(sensorId, pageable);
        return new ResponseEntity<PagedModel<Parking>>(parkingPageResourceAssembler.toModel(p, parkingResourceAssembler), HttpStatus.OK);
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
    @Operation(summary = "Get all measurements of a specific sensor within a given timeframe")
    @GetMapping("/bySensor/{sensorId}/timeframe")
    public ResponseEntity<PagedModel<Parking>> bySensorInTimeframe(@RequestParam Instant startTime, @RequestParam Instant endTime, @PathVariable Long sensorId, @Parameter(hidden = true) Pageable pageable)
    {
        Page p = parkingRepository.findParkingsByTimeAfterAndTimeBeforeAndSensorId(LocalDateTime.ofInstant(startTime, ZoneOffset.UTC), LocalDateTime.ofInstant(endTime, ZoneOffset.UTC), sensorId, pageable);
        return new ResponseEntity<PagedModel<Parking>>(parkingPageResourceAssembler.toModel(p, parkingResourceAssembler), HttpStatus.OK);
    }

    /**
     * By sensor latest entity model.
     *
     * @param sensorId the sensor id
     * @return the entity model
     */
    @Operation(summary = "Get the latest measurement of a specific sensor")
    @GetMapping("/bySensor/{sensorId}/latest")
    public EntityModel<Parking> bySensorLatest(@PathVariable Long sensorId){
        Parking parking = parkingRepository.findFirstBySensorIdOrderByTimeDesc(sensorId);
        return parkingResourceAssembler.toModel(parking);
    }

}
