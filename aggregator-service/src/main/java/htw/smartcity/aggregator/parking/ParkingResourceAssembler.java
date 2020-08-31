package htw.smartcity.aggregator.parking;

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
 * The type Parking resource assembler.
 */
@Component
public class ParkingResourceAssembler implements RepresentationModelAssembler<Parking, EntityModel<Parking>> {
    @Override
    public EntityModel<Parking> toModel(Parking parking){
        EntityModel<Parking> entityModel = EntityModel.of(parking);
                //linkTo(methodOn(ParkingController.class).one(parking.getId())).withSelfRel(),
                //linkTo(methodOn(ParkingController.class).all(Pageable.unpaged())).withRel("parking"));
            //todo
        if(parking.getSensor() != null)
        {
            entityModel.add(linkTo(methodOn(SensorController.class).one(parking.getSensor().getId())).withRel("sensor"));
        }
        return entityModel;
    }
}
