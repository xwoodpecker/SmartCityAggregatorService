package htw.smartcity.aggregator.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SensorController {
    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorResourceAssembler sensorResourceAssembler;

    @Autowired
    private SensorPageResourceAssembler sensorPageResourceAssembler;

    @GetMapping("/sensors")
    public ResponseEntity<PagedModel<Sensor>> all(Pageable pageable)
    {
        Page p = sensorRepository.findAll(pageable);

        return new ResponseEntity<PagedModel<Sensor>>(sensorPageResourceAssembler.toModel(p, sensorResourceAssembler), HttpStatus.OK);
    }

    @GetMapping("sensors/{id}")
    public EntityModel<Sensor> one(@PathVariable Long id)
    {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new SensorNotFoundException(id));

        return sensorResourceAssembler.toModel(sensor);
    }
}
