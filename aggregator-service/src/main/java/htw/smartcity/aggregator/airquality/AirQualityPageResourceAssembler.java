package htw.smartcity.aggregator.airquality;


import htw.smartcity.aggregator.temperature.Temperature;
import htw.smartcity.aggregator.temperature.TemperatureResourceAssembler;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class AirQualityPageResourceAssembler extends
        PagedResourcesAssembler<AirQuality> {

    public AirQualityPageResourceAssembler() {
        super(null, linkTo(AirQualityResourceAssembler.class)
                .toUriComponentsBuilder().build());
    }
}