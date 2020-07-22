package htw.smartcity.aggregator.parking;


import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ParkingGroupCounterPageResourceAssembler extends
        PagedResourcesAssembler<ParkingGroupCounter> {

    public ParkingGroupCounterPageResourceAssembler() {
        super(null, linkTo(ParkingGroupCounterResourceAssembler.class)
                .toUriComponentsBuilder().build());
    }
}