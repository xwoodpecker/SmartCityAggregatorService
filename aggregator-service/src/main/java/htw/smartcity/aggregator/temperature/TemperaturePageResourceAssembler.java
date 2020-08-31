package htw.smartcity.aggregator.temperature;


import org.springframework.context.annotation.Scope;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * The type Temperature page resource assembler.
 */
@Component
public class TemperaturePageResourceAssembler extends
        PagedResourcesAssembler<Temperature> {

    /**
     * Instantiates a new Temperature page resource assembler.
     */
    public TemperaturePageResourceAssembler() {
        super(null, linkTo(TemperatureResourceAssembler.class)
                .toUriComponentsBuilder().build());
    }
}