package htw.smartcity.aggregator.sensor;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * The type Sensor page resource assembler.
 */
@Component
public class SensorPageResourceAssembler extends
        PagedResourcesAssembler<Sensor> {
    /**
     * Instantiates a new Sensor page resource assembler.
     */
    public SensorPageResourceAssembler() {
        super(null, linkTo(SensorResourceAssembler.class)
                .toUriComponentsBuilder().build());
    }
}
