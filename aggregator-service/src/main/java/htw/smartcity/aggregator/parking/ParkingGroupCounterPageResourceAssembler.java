package htw.smartcity.aggregator.parking;


import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * The type Parking group counter page resource assembler.
 */
@Component
public class ParkingGroupCounterPageResourceAssembler extends
        PagedResourcesAssembler<ParkingGroupCounter> {

    /**
     * Instantiates a new Parking group counter page resource assembler.
     */
    public ParkingGroupCounterPageResourceAssembler() {
        super(null, linkTo(ParkingGroupCounterResourceAssembler.class)
                .toUriComponentsBuilder().build());
    }
}