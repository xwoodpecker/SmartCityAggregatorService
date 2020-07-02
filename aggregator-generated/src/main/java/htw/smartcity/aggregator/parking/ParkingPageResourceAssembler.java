package htw.smartcity.aggregator.parking;


import htw.smartcity.aggregator.temperature.Temperature;
import htw.smartcity.aggregator.temperature.TemperatureResourceAssembler;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ParkingPageResourceAssembler extends
        PagedResourcesAssembler<Parking> {

    public ParkingPageResourceAssembler() {
        super(null, linkTo(ParkingResourceAssembler.class)
                .toUriComponentsBuilder().build());
    }
}