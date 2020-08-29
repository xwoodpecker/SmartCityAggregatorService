package htw.smartcity.aggregator.sensor;

import htw.smartcity.aggregator.temperature.TemperatureController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SensorResourceAssembler implements RepresentationModelAssembler<Sensor, EntityModel<Sensor>> {
    @Override
    public EntityModel<Sensor> toModel(Sensor sensor){
        EntityModel entityModel = EntityModel.of(sensor,
                linkTo(methodOn(SensorController.class).one(sensor.getId())).withSelfRel(),
                linkTo(methodOn(SensorController.class).all(Pageable.unpaged())).withRel("sensors")
        );

        switch(sensor.getSensorType()){
            case PARKING:
                break;
            case AIR_QUALITY:
                break;
            case TEMPERATURE:
                entityModel.add(linkTo(methodOn(TemperatureController.class).bySensor(sensor.getId(), Pageable.unpaged())).withRel("data"));
                break;
        }

        return entityModel;

    }
}
