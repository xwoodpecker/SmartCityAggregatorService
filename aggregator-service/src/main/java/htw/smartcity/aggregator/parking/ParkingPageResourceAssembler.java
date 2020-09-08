package htw.smartcity.aggregator.parking;


import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * The type Parking page resource assembler.
 */
@Component
public class ParkingPageResourceAssembler extends
        PagedResourcesAssembler<Parking> {

    /**
     * Instantiates a new Parking page resource assembler.
     */
    public ParkingPageResourceAssembler() {
        super(null, linkTo(ParkingResourceAssembler.class)
                .toUriComponentsBuilder().build());
    }
}