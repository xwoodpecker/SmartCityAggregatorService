package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.sensor.SensorController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TemperatureAverageResourceAssembler implements RepresentationModelAssembler<TemperatureAverage,
        EntityModel<TemperatureAverage>>
{
    @Override
    public EntityModel<TemperatureAverage> toModel(TemperatureAverage entity)
    {
        EntityModel<TemperatureAverage> entityModel = EntityModel.of(entity,
                                  linkTo(methodOn(TemperatureAverageController.class).one(entity.getId())).withSelfRel());
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }
}
