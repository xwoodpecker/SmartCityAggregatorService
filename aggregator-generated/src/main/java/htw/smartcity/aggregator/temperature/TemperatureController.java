package htw.smartcity.aggregator.temperature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TemperatureController {
    @Autowired
    private TemperatureRepository temperatureRepository;

    @Autowired
    private TemperatureResourceAssembler temperatureResourceAssembler;

    @GetMapping("/temperature")
    CollectionModel<EntityModel<Temperature>> all()
    {
        List<EntityModel<Temperature>> temperatures = temperatureRepository.findAll().stream()
                .map(temperatureResourceAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(temperatures,
                linkTo(methodOn(TemperatureController.class).all()).withSelfRel());

    }

    @GetMapping("/temperature/byDate")
    CollectionModel<EntityModel<Temperature>> between(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime)
    {
        List<EntityModel<Temperature>> temperatures = temperatureRepository.findTemperaturesByTimeBeforeAndTimeAfter(endTime, startTime).stream()
                .map(temperatureResourceAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(temperatures,
                linkTo(methodOn(TemperatureController.class).all()).withSelfRel());
    }

    //todo remove (not used)
    @PostMapping("/temperature")
    Temperature newTemperature(@RequestBody Temperature newTemperature)
    {
        return temperatureRepository.save(newTemperature);
    }

    @GetMapping("/temperature/{id}")
    EntityModel<Temperature> one(@PathVariable Long id)
    {
        Temperature temperature = temperatureRepository.findById(id)
                .orElseThrow(() -> new TemperatureNotFoundException(id));

        return temperatureResourceAssembler.toModel(temperature);
    }

    //todo remove (not used)
    @PutMapping("temperature/{id}")
    Temperature replaceTemperature(@RequestBody Temperature newTemperature, @PathVariable Long id)
    {
        return temperatureRepository.findById(id)
                .map(temperature -> {
                    temperature.setTime(newTemperature.getTime());
                    temperature.setValue(newTemperature.getValue());
                    return temperatureRepository.save(temperature);
                })
                .orElseGet(() -> {
                    newTemperature.setId(id);
                    return temperatureRepository.save(newTemperature);
                });
    }

    //todo remove? (tbd)
    @DeleteMapping("/temperature/{id}")
    void deleteTemperature(@PathVariable Long id)
    {
        temperatureRepository.deleteById(id);
    }
}
