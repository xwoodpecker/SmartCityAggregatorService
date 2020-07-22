package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.SensorController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ParkingGroupCounterResourceAssembler implements RepresentationModelAssembler<ParkingGroupCounter, EntityModel<ParkingGroupCounter>> {
    @Override
    public EntityModel<ParkingGroupCounter> toModel(ParkingGroupCounter parkingGroupCounter){
        EntityModel<ParkingGroupCounter> entityModel = EntityModel.of(parkingGroupCounter);
                //linkTo(methodOn(ParkingController.class).one(parking.getId())).withSelfRel(),
                //linkTo(methodOn(ParkingController.class).all(Pageable.unpaged())).withRel("parking"));
            //todo
        return entityModel;
    }
}
