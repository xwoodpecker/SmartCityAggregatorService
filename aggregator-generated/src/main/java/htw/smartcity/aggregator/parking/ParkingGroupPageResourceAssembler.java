package htw.smartcity.aggregator.parking;


import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ParkingGroupPageResourceAssembler extends
        PagedResourcesAssembler<ParkingGroup> {

    public ParkingGroupPageResourceAssembler() {
        super(null, linkTo(ParkingGroupResourceAssembler.class)
                .toUriComponentsBuilder().build());
    }
}