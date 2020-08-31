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

/**
 * The type Air quality resource assembler.
 */
@Component
public class AirQualityResourceAssembler implements RepresentationModelAssembler<AirQuality, EntityModel<AirQuality>> {
    @Override
    public EntityModel<AirQuality> toModel(AirQuality airQuality){
        EntityModel<AirQuality> entityModel = EntityModel.of(airQuality,
                linkTo(methodOn(AirQualityController.class).one(airQuality.getId())).withSelfRel(),
                linkTo(methodOn(AirQualityController.class).all(Pageable.unpaged())).withRel("airQualities"));
        if(airQuality.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(airQuality.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }
}
