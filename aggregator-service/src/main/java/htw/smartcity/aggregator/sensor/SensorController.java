package htw.smartcity.aggregator.sensor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/sensors")
@SecurityRequirement(name = "basic")
@Tag(name = "Sensor Management", description = "Endpoint to manage sensors")
public class SensorController {
    private SensorRepository sensorRepository;
    private SensorResourceAssembler sensorResourceAssembler;
    private SensorPageResourceAssembler sensorPageResourceAssembler;

    public SensorController(SensorRepository sensorRepository, SensorResourceAssembler sensorResourceAssembler, SensorPageResourceAssembler sensorPageResourceAssembler) {
        this.sensorRepository = sensorRepository;
        this.sensorResourceAssembler = sensorResourceAssembler;
        this.sensorPageResourceAssembler = sensorPageResourceAssembler;
    }

    @Operation(summary = "Get all sensors")
    @PageableAsQueryParam
    @GetMapping("/")
    public ResponseEntity<PagedModel<Sensor>> all(@Parameter(hidden = true) Pageable pageable)
    {
        Page p = sensorRepository.findAll(pageable);
        return new ResponseEntity<PagedModel<Sensor>>(sensorPageResourceAssembler.toModel(p, sensorResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get all sensors of given type")
    @PageableAsQueryParam
    @GetMapping("/byType/{sensorType}")
    public ResponseEntity<PagedModel<Sensor>> byType(@PathVariable Sensor.SensorType sensorType, @Parameter(hidden = true) Pageable pageable)
    {
        //todo implement
        Page p = sensorRepository.findAll(pageable);
        return new ResponseEntity<PagedModel<Sensor>>(sensorPageResourceAssembler.toModel(p, sensorResourceAssembler), HttpStatus.OK);
    }

    @Operation(summary = "Get a specific sensor")
    @GetMapping("/{id}")
    public EntityModel<Sensor> one(@PathVariable Long id)
    {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new SensorNotFoundException(id));

        return sensorResourceAssembler.toModel(sensor);
    }

    @Operation(summary = "Update a specific sensor. Admin Role required.")
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    Sensor replaceSensor(@RequestBody Sensor newSensor, @PathVariable Long id)
    {
        return sensorRepository.findById(id)
                .map(sensor -> {
                    sensor.setName(newSensor.getName());
                    sensor.setInformation(newSensor.getInformation());
                    sensor.setX(newSensor.getX());
                    sensor.setY(newSensor.getY());
                    sensor.setSensorType(newSensor.getSensorType());
                    return sensorRepository.save(sensor);
                })
                .orElseThrow(() -> new SensorNotFoundException(id));
    }
}
