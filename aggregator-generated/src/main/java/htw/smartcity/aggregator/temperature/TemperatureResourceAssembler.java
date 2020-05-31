package htw.smartcity.aggregator.temperature;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TemperatureResourceAssembler implements RepresentationModelAssembler<Temperature, EntityModel<Temperature>> {
    @Override
    public EntityModel<Temperature> toModel(Temperature temperature){
        return EntityModel.of(temperature,
                linkTo(methodOn(TemperatureController.class).one(temperature.getId())).withSelfRel(),
                linkTo(methodOn(TemperatureController.class).all(Pageable.unpaged())).withRel("temperatures"));
    }
}
