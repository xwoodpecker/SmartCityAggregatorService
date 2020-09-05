package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.sensor.SensorController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Temperature average resource assembler.
 */
@Component
public class TemperatureAggregateResourceAssembler implements RepresentationModelAssembler<TemperatureAggregate,
        EntityModel<TemperatureAggregate>>
{
    @Override
    public EntityModel<TemperatureAggregate> toModel(TemperatureAggregate entity)
    {
        EntityModel<TemperatureAggregate> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }
}
