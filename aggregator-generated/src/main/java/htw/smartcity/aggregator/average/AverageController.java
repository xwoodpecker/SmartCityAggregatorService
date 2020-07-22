package htw.smartcity.aggregator.average;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (path = "/averages")
@Tag (name = "Average values", description = "Endpoint to get average values")
public class AverageController
{
    private AverageRepository averageRepository;
    private AverageResourceAssembler averageResourceAssembler;

    public AverageController(AverageRepository averageRepository, AverageResourceAssembler averageResourceAssembler)
    {
        this.averageRepository = averageRepository;
        this.averageResourceAssembler = averageResourceAssembler;
    }

    EntityModel<Average> one(@PathVariable Long avgId)
    {
        Average avg = averageRepository.findById(avgId)
                .orElseThrow(() -> new AverageNotFoundException(avgId));

        return averageResourceAssembler.toModel(avg);
    }
}
