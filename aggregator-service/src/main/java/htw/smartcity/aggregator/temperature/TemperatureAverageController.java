package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.sensor.SensorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * The type Temperature average controller.
 */
@RestController
@RequestMapping (path = "/temperatures/averages")
@Tag (name = "Temperature Averages", description = "Endpoint to get temperature averages")
public class TemperatureAverageController
{
    private TemperatureAverageRepository temperatureAverageRepository;
    private TemperatureAverageResourceAssembler temperatureAverageResourceAssembler;
    private TemperatureRepository temperatureRepository;
    private SensorRepository sr;

    /**
     * Instantiates a new Temperature average controller.
     *
     * @param temperatureAverageRepository        the temperature average repository
     * @param temperatureAverageResourceAssembler the temperature average resource assembler
     * @param temperatureRepository               the temperature repository
     */
    public TemperatureAverageController(TemperatureAverageRepository temperatureAverageRepository,
                                        TemperatureAverageResourceAssembler temperatureAverageResourceAssembler,
                                        TemperatureRepository temperatureRepository, SensorRepository sr) {
        this.temperatureAverageRepository = temperatureAverageRepository;
        this.temperatureAverageResourceAssembler = temperatureAverageResourceAssembler;
        this.temperatureRepository = temperatureRepository;
        this.sr = sr;
    }

    /**
     * One entity model.
     *
     * @param temperatureaverageId the temperatureaverage id
     * @return the entity model
     */
    @Operation (summary = "Get a single temperature average measurement")
    @GetMapping ("/{temperatureaverageId}")
    public EntityModel<TemperatureAggregate> one(@PathVariable Long temperatureaverageId)
    {
        TemperatureAggregate temperatureaverage = temperatureAverageRepository.findById(temperatureaverageId)
                .orElseThrow(() -> new TemperatureNotFoundException(temperatureaverageId));

        return temperatureAverageResourceAssembler.toModel(temperatureaverage);
    }

    /**
     * Compute daily entity model.
     *
     * @param sensorId the sensor id
     * @param pageable the pageable
     * @return the entity model
     */
    @Operation (summary = "Compute daily average of a sensor")
    @GetMapping ("/temperatureaverage/daily/{sensorId}")
    public EntityModel<TemperatureAggregate> computeDaily(@PathVariable Long sensorId,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        LocalDateTime startTime = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.now().with(LocalTime.MAX);


        Page p = temperatureRepository.findTemperaturesBySensorIdAndTimeBetween(sensorId, startTime, endTime,
                                                                                pageable);

        List   l   = p.getContent();
        double sum = 0, count = 0;
        for (var ele: l)
        {
            sum += Double.parseDouble(ele.toString());
            count++;
        }

        TemperatureAverageDaily temperatureAverageDaily = new TemperatureAverageDaily();
        temperatureAverageDaily.setDate(LocalDateTime.now());
        temperatureAverageDaily.setSensor(sr.getOne(sensorId));
        temperatureAverageDaily.setValue(sum/count);

        temperatureAverageRepository.save(temperatureAverageDaily);

        return one((long) 1);

    }

    /**
     * Compute weekly entity model.
     *
     * @param sensorId the sensor id
     * @param pageable the pageable
     * @return the entity model
     */
    @Operation (summary = "Compute daily average of a sensor")
    @GetMapping ("/temperatureaverage/weekly")
    public EntityModel<TemperatureAggregate> computeWeekly(@PathVariable Long sensorId, @Parameter (hidden = true) Pageable pageable)
    {
        return one((long) 1);
    }

    /**
     * Compute monthly entity model.
     *
     * @param sensorId the sensor id
     * @param pageable the pageable
     * @return the entity model
     */
    @Operation (summary = "Compute daily average of a sensor")
    @GetMapping ("/temperatureaverage/monthly")
    public EntityModel<TemperatureAggregate> computeMonthly(@PathVariable Long sensorId, @Parameter (hidden = true) Pageable pageable)
    {
        return one((long) 1);
    }

}
