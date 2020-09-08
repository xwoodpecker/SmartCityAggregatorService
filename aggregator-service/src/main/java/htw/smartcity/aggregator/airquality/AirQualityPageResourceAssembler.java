package htw.smartcity.aggregator.airquality;


import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * The type Air quality page resource assembler.
 */
@Component
public class AirQualityPageResourceAssembler extends
        PagedResourcesAssembler<AirQuality> {

    /**
     * Instantiates a new Air quality page resource assembler.
     */
    public AirQualityPageResourceAssembler() {
        super(null, linkTo(AirQualityResourceAssembler.class)
                .toUriComponentsBuilder().build());
    }
}