package htw.smartcity.aggregator.airquality;


import htw.smartcity.aggregator.temperature.Temperature;
import htw.smartcity.aggregator.temperature.TemperatureResourceAssembler;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class AirqualityPageResourceAssembler extends
        PagedResourcesAssembler<Airquality> {

    public AirqualityPageResourceAssembler() {
        super(null, linkTo(AirqualityResourceAssembler.class)
                .toUriComponentsBuilder().build());
    }
}