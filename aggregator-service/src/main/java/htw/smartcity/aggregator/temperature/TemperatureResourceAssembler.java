package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.sensor.SensorController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Temperature resource assembler.
 */
@Component
public class TemperatureResourceAssembler implements RepresentationModelAssembler<Temperature, EntityModel<Temperature>> {
    @Override
    public EntityModel<Temperature> toModel(Temperature temperature){
        EntityModel<Temperature> entityModel = EntityModel.of(temperature,
                linkTo(methodOn(TemperatureController.class).one(temperature.getId())).withSelfRel(),
                linkTo(methodOn(TemperatureController.class).all(Pageable.unpaged())).withRel("temperatures"));
        if(temperature.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(temperature.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }
}
