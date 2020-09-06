package htw.smartcity.aggregator.temperatureaggregate;

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

    public EntityModel<TemperatureAverageDaily> toModel(TemperatureAverageDaily entity)
    {
        EntityModel<TemperatureAverageDaily> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<TemperatureAverageWeekly> toModel(TemperatureAverageWeekly entity)
    {
        EntityModel<TemperatureAverageWeekly> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<TemperatureAverageMonthly> toModel(TemperatureAverageMonthly entity)
    {
        EntityModel<TemperatureAverageMonthly> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<TemperatureMaximumDaily> toModel(TemperatureMaximumDaily entity)
    {
        EntityModel<TemperatureMaximumDaily> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<TemperatureMaximumWeekly> toModel(TemperatureMaximumWeekly entity)
    {
        EntityModel<TemperatureMaximumWeekly> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<TemperatureMaximumMonthly> toModel(TemperatureMaximumMonthly entity)
    {
        EntityModel<TemperatureMaximumMonthly> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<TemperatureMinimumDaily> toModel(TemperatureMinimumDaily entity)
    {
        EntityModel<TemperatureMinimumDaily> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<TemperatureMinimumWeekly> toModel(TemperatureMinimumWeekly entity)
    {
        EntityModel<TemperatureMinimumWeekly> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }

    public EntityModel<TemperatureMinimumMonthly> toModel(TemperatureMinimumMonthly entity)
    {
        EntityModel<TemperatureMinimumMonthly> entityModel = EntityModel.of(entity);
        if(entity.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(entity.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }
}
