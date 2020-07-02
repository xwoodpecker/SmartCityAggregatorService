package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.sensor.SensorController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ParkingGroupResourceAssembler implements RepresentationModelAssembler<ParkingGroup, EntityModel<ParkingGroup>> {
    @Override
    public EntityModel<ParkingGroup> toModel(ParkingGroup parkingGroup){
        EntityModel<ParkingGroup> entityModel = EntityModel.of(parkingGroup);
                //todo
        return entityModel;
    }
}