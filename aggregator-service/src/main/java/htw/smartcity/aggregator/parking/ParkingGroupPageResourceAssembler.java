package htw.smartcity.aggregator.parking;


import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * The type Parking group page resource assembler.
 */
@Component
public class ParkingGroupPageResourceAssembler extends
        PagedResourcesAssembler<ParkingGroup> {

    /**
     * Instantiates a new Parking group page resource assembler.
     */
    public ParkingGroupPageResourceAssembler() {
        super(null, linkTo(ParkingGroupResourceAssembler.class)
                .toUriComponentsBuilder().build());
    }
}