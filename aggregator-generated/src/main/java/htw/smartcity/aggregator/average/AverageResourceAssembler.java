package htw.smartcity.aggregator.average;


import htw.smartcity.aggregator.sensor.SensorController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AverageResourceAssembler implements RepresentationModelAssembler<Average, EntityModel<Average>>
{
    @Override
    public EntityModel<Average> toModel(Average avg){
        EntityModel<Average> entityModel = EntityModel.of(avg,
            linkTo(methodOn(AverageController.class).one(avg.getId())).withSelfRel());
            //linkTo(methodOn(AverageController.class).all(Pageable.unpaged())).withRel("averages"));
        if(avg.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(avg.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }
}
