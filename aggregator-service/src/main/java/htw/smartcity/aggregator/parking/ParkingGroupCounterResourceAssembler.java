package htw.smartcity.aggregator.parking;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Parking group counter resource assembler.
 */
@Component
public class ParkingGroupCounterResourceAssembler implements RepresentationModelAssembler<ParkingGroupCounter, EntityModel<ParkingGroupCounter>> {
    @Override
    public EntityModel<ParkingGroupCounter> toModel(ParkingGroupCounter parkingGroupCounter){
        EntityModel<ParkingGroupCounter> entityModel = EntityModel.of(parkingGroupCounter,
                linkTo(methodOn(ParkingGroupController.class).groupMeasures(Pageable.unpaged(), parkingGroupCounter.getParkingGroup().getId())).withRel("groupMeasures"));
        return entityModel;
    }
}
