package htw.smartcity.aggregator.airqualityaggregator;

import htw.smartcity.aggregator.sensor.SensorController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AirQualityAggregateResourceAssembler implements RepresentationModelAssembler<AirQualityAggregate,
        EntityModel<AirQualityAggregate>>
{
    @Override
    public EntityModel<AirQualityAggregate> toModel(AirQualityAggregate entity)
    {
        EntityModel<AirQualityAggregate> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }
}
