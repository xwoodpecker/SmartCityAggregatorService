package htw.smartcity.aggregator.airquality;

import htw.smartcity.aggregator.sensor.SensorController;
import htw.smartcity.aggregator.temperature.Temperature;
import htw.smartcity.aggregator.temperature.TemperatureController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AirqualityResourceAssembler implements RepresentationModelAssembler<Airquality, EntityModel<Airquality>> {
    @Override
    public EntityModel<Airquality> toModel(Airquality airquality){
        EntityModel<Airquality> entityModel = EntityModel.of(airquality,
                linkTo(methodOn(AirqualityController.class).one(airquality.getId())).withSelfRel(),
                linkTo(methodOn(AirqualityController.class).all(Pageable.unpaged())).withRel("airqualities"));
        if(airquality.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(airquality.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }
}
