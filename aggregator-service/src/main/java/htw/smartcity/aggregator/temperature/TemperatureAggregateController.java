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
@RequestMapping (path = "/temperatures/aggregations")
@Tag (name = "Temperature Aggregations", description = "Endpoint to get temperature averages, maximum and minimum")
public class TemperatureAggregateController
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
    public TemperatureAggregateController(TemperatureAggregateRepository temperatureAggregateRepository,
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
     * @param temperatureaggregatorId the temperatureaverage id
     * @return the entity model
     */
    @Operation (summary = "Get a single temperature aggregate measurement")
    @GetMapping ("/{temperatureaggregatorId}")
    public EntityModel<TemperatureAggregate> one(@PathVariable Long temperatureaggregatorId)
    {
        TemperatureAggregate temperatureaverage = temperatureAggregateRepository.findById(temperatureaggregatorId)
                .orElseThrow(() -> new TemperatureNotFoundException(temperatureaggregatorId));

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
    public EntityModel<TemperatureAggregate> getDailyAverage(@PathVariable Long sensorId,
                                                           @RequestParam LocalDateTime date,
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
    public EntityModel<TemperatureAggregate> getWeeklyAverage(@PathVariable Long sensorId,
                                                           @RequestParam LocalDateTime date,
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
    @GetMapping ("/temperatureaverage/monthly/{sensorId}")
    public EntityModel<TemperatureAggregate> getMonthlyAverage(@PathVariable Long sensorId,
                                                           @RequestParam LocalDateTime date,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureAverageMonthly temperatureAverageMonthly =
                temperatureAggregateRepository.findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, date, date);
        return one((long) 1);
    }

    @Operation (summary = "Get daily maximum of a sensor at a given date")
    @GetMapping ("/temperaturemaximum/daily/{sensorId}")
    public EntityModel<TemperatureAggregate> getDailyMax(@PathVariable Long sensorId, @RequestParam LocalDateTime date,
                                                      @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureMaximumDaily temperatureMaximumDaily =
                temperatureAggregateRepository.findTemperatureMaxiumumDailyBySensorIdAndTime(sensorId, date);

        return one((long) 1);
    }

    @Operation (summary = "Get weekly maximum of a sensor of a given date in the week")
    @GetMapping ("/temperaturemaximum/weekly/{sensorId}")
    public EntityModel<TemperatureAggregate> getWeeklyMax(@PathVariable Long sensorId, @RequestParam LocalDateTime date,
                                                         @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureMaximumWeekly temperatureMaximumWeekly =
                temperatureAggregateRepository.findTemperatureMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, date, date);

        return one((long) 1);
    }

    @Operation (summary = "Get monthly maximum of a sensor of a given date in the week")
    @GetMapping ("/temperaturemaximum/monthly/{sensorId}")
    public EntityModel<TemperatureAggregate> getMonthlyMax(@PathVariable Long sensorId,
                                                           @RequestParam LocalDateTime date,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureMaximumMonthly temperatureMaximumMonthly =
                temperatureAggregateRepository.findTemperatureMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, date, date);

        return one((long) 1);
    }

    @Operation (summary = "Get daily minimum of a sensor at a given date")
    @GetMapping ("/temperatureminimum/daily/{sensorId}")
    public EntityModel<TemperatureAggregate> getDailyMin(@PathVariable Long sensorId, @RequestParam LocalDateTime date,
                                                         @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureMinimumDaily temperatureMinimumDaily =
                temperatureAggregateRepository.findTemperatureMinimumDailyBySensorIdAndTime(sensorId, date);

        return one((long) 1);
    }

    @Operation (summary = "Get weekly minimum of a sensor of a given date in the week")
    @GetMapping ("/temperatureminimum/weekly/{sensorId}")
    public EntityModel<TemperatureAggregate> getWeeklyMin(@PathVariable Long sensorId, @RequestParam LocalDateTime date,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureMinimumWeekly temperatureMinimumWeekly =
                temperatureAggregateRepository.findTemperatureMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, date, date);

        return one((long) 1);
    }

    @Operation (summary = "Get monthly minimum of a sensor of a given date in the week")
    @GetMapping ("/temperatureminimum/monthly/{sensorId}")
    public EntityModel<TemperatureAggregate> getMonthlyMin(@PathVariable Long sensorId,
                                                           @RequestParam LocalDateTime date,
                                                           @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureMinimumMonthly temperatureMinimumMonthly =
                temperatureAggregateRepository.findTemperatureMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, date, date);

        return one((long) 1);
    }

}
