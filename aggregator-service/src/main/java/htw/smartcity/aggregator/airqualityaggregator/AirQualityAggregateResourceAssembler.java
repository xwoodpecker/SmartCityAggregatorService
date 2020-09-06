package htw.smartcity.aggregator.airqualityaggregator;

import htw.smartcity.aggregator.sensor.SensorController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Air quality aggregate resource assembler.
 */
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

    public EntityModel<AirQualityAverageDaily> toModel(AirQualityAverageDaily entity)
    {
        EntityModel<AirQualityAverageDaily> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<AirQualityAverageWeekly> toModel(AirQualityAverageWeekly entity)
    {
        EntityModel<AirQualityAverageWeekly> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<AirQualityAverageMonthly> toModel(AirQualityAverageMonthly entity)
    {
        EntityModel<AirQualityAverageMonthly> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<AirQualityMaximumDaily> toModel(AirQualityMaximumDaily entity)
    {
        EntityModel<AirQualityMaximumDaily> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<AirQualityMaximumWeekly> toModel(AirQualityMaximumWeekly entity)
    {
        EntityModel<AirQualityMaximumWeekly> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<AirQualityMaximumMonthly> toModel(AirQualityMaximumMonthly entity)
    {
        EntityModel<AirQualityMaximumMonthly> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<AirQualityMinimumDaily> toModel(AirQualityMinimumDaily entity)
    {
        EntityModel<AirQualityMinimumDaily> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<AirQualityMinimumWeekly> toModel(AirQualityMinimumWeekly entity)
    {
        EntityModel<AirQualityMinimumWeekly> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<AirQualityMinimumMonthly> toModel(AirQualityMinimumMonthly entity)
    {
        EntityModel<AirQualityMinimumMonthly> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }
}
