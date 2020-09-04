package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.sensor.SensorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * The type Temperature average controller.
 */
@RestController
@RequestMapping (path = "/temperatures/averages")
@Tag (name = "Temperature Averages", description = "Endpoint to get temperature averages")
public class TemperatureAverageController
{
    private TemperatureAggregateRepository temperatureAggregateRepository;
    private TemperatureAverageResourceAssembler temperatureAverageResourceAssembler;
    private TemperatureRepository temperatureRepository;
    private SensorRepository sr;

    /**
     * Instantiates a new Temperature average controller.
     *
     * @param temperatureAggregateRepository        the temperature aggregate repository
     * @param temperatureAverageResourceAssembler the temperature average resource assembler
     * @param temperatureRepository               the temperature repository
     */
    public TemperatureAverageController(TemperatureAggregateRepository temperatureAggregateRepository,
                                        TemperatureAverageResourceAssembler temperatureAverageResourceAssembler,
                                        TemperatureRepository temperatureRepository, SensorRepository sr) {
        this.temperatureAggregateRepository = temperatureAggregateRepository;
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
        TemperatureAggregate temperatureaverage = temperatureAggregateRepository.findById(temperatureaverageId)
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
    @Operation (summary = "Get daily average of a sensor at a given date")
    @GetMapping ("/temperatureaverage/daily/{sensorId}")
    public EntityModel<TemperatureAggregate> getDaily(@PathVariable Long sensorId, @RequestParam LocalDateTime date,
                                                        @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureAverageDaily temperatureAverageDaily =
                temperatureAggregateRepository.findTemperatureAverageDailyBySensorIdAndTime(sensorId, date);

        return one((long) 1);

    }

    /**
     * Compute weekly entity model.
     *
     * @param sensorId the sensor id
     * @param pageable the pageable
     * @return the entity model
     */
    @Operation (summary = "Get weekly average of a sensor of a given date in the week")
    @GetMapping ("/temperatureaverage/weekly/{sensorId}")
    public EntityModel<TemperatureAggregate> getWeekly(@PathVariable Long sensorId, @RequestParam LocalDateTime date,
                                                         @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureAverageWeekly temperatureAverageWeekly =
                temperatureAggregateRepository.findTemperatureAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, date, date);
        return one((long) 1);
    }

    /**
     * Compute monthly entity model.
     *
     * @param sensorId the sensor id
     * @param pageable the pageable
     * @return the entity model
     */
    @Operation (summary = "Get monthly average of a sensor of a given date in the month")
    @GetMapping ("/temperatureaverage/monthly")
    public EntityModel<TemperatureAggregate> getMonthly(@PathVariable Long sensorId, @RequestParam LocalDateTime date,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureAverageMonthly temperatureAverageMonthly =
                temperatureAggregateRepository.findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, date, date);
        return one((long) 1);
    }

}
