package htw.smartcity.aggregator.sensor;

import htw.smartcity.aggregator.temperature.Temperature;
import htw.smartcity.aggregator.temperature.TemperatureResourceAssembler;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class SensorPageResourceAssembler extends
        PagedResourcesAssembler<Sensor> {
    public SensorPageResourceAssembler() {
        super(null, linkTo(SensorResourceAssembler.class)
                .toUriComponentsBuilder().build());
    }
}
