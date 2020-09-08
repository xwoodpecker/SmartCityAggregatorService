package htw.smartcity.aggregator.sensor;

import htw.smartcity.aggregator.airquality.AirQualityController;
import htw.smartcity.aggregator.parking.ParkingController;
import htw.smartcity.aggregator.parking.ParkingGroupController;
import htw.smartcity.aggregator.temperature.TemperatureController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Sensor resource assembler.
 */
@Component
public class SensorResourceAssembler implements RepresentationModelAssembler<Sensor, EntityModel<Sensor>> {
    @Override
    public EntityModel<Sensor> toModel(Sensor sensor){
        EntityModel entityModel = EntityModel.of(sensor,
                linkTo(methodOn(SensorController.class).one(sensor.getId())).withSelfRel(),
                linkTo(methodOn(SensorController.class).all(Pageable.unpaged())).withRel("sensors")
        );

        switch(sensor.getSensorType()){
            case AIR_QUALITY:
                entityModel.add(linkTo(methodOn(AirQualityController.class).bySensor(sensor.getId(), Pageable.unpaged())).withRel("data"));
                break;
            case TEMPERATURE:
                entityModel.add(linkTo(methodOn(TemperatureController.class).bySensor(sensor.getId(), Pageable.unpaged())).withRel("data"));
                break;
        }

        return entityModel;

    }
}
