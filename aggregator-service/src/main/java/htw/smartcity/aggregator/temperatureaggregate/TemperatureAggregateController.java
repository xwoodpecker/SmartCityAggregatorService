package htw.smartcity.aggregator.temperatureaggregate;

import htw.smartcity.aggregator.sensor.SensorRepository;
import htw.smartcity.aggregator.temperature.TemperatureNotFoundException;
import htw.smartcity.aggregator.temperature.TemperatureRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

/**
 * The type Temperature average controller.
 */
@RestController
@RequestMapping (path = "/temperatures/aggregations")
@Tag (name = "Temperature Aggregations", description = "Endpoint to get temperature averages, maximum and minimum")
@SecurityRequirement (name = "basic")
public class TemperatureAggregateController
{
    private TemperatureAggregateRepository        temperatureAggregateRepository;
    private TemperatureAggregateResourceAssembler temperatureAggregateResourceAssembler;
    private TemperatureRepository                 temperatureRepository;
    private SensorRepository                      sr;

    /**
     * Instantiates a new Temperature average controller.
     *
     * @param temperatureAggregateRepository        the temperature aggregate repository
     * @param temperatureAggregateResourceAssembler the temperature average resource assembler
     * @param temperatureRepository                 the temperature repository
     * @param sr                                    the sr
     */
    public TemperatureAggregateController(TemperatureAggregateRepository temperatureAggregateRepository,
                                          TemperatureAggregateResourceAssembler temperatureAggregateResourceAssembler,
                                          TemperatureRepository temperatureRepository, SensorRepository sr) {
        this.temperatureAggregateRepository        = temperatureAggregateRepository;
        this.temperatureAggregateResourceAssembler = temperatureAggregateResourceAssembler;
        this.temperatureRepository                 = temperatureRepository;
        this.sr                                    = sr;
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

        return temperatureAggregateResourceAssembler.toModel(temperatureaverage);
    }

    /**
     * Compute daily entity model.
     *
     * @param sensorId the sensor id
     * @param date     the date
     * @param pageable the pageable
     * @return the entity model
     */
    @Operation (summary = "Get daily average of a sensor at a given date")
    @GetMapping ("/temperatureaverage/daily/{sensorId}")
    public EntityModel<TemperatureAggregate> getDailyAverage(@PathVariable Long sensorId,
                                                           @RequestParam Instant date,
                                                        @Parameter (hidden = true) Pageable pageable)
    {
            TemperatureAverageDaily temperatureAverageDaily =
                    temperatureAggregateRepository.findTemperatureAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(
                            sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MAX),
                            LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MIN));

            if (temperatureAverageDaily == null)
                throw new TemperatureAggregateNotFoundException(sensorId);

        return temperatureAggregateResourceAssembler.toModel(temperatureAverageDaily);

    }

    /**
     * Compute weekly entity model.
     *
     * @param sensorId the sensor id
     * @param date     the date
     * @param pageable the pageable
     * @return the entity model
     */
    @Operation (summary = "Get weekly average of a sensor of a given date in the week")
    @GetMapping ("/temperatureaverage/weekly/{sensorId}")
    public EntityModel<TemperatureAggregate> getWeeklyAverage(@PathVariable Long sensorId,
                                                           @RequestParam Instant date,
                                                         @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureAverageWeekly temperatureAverageWeekly =
                temperatureAggregateRepository.findTemperatureAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (temperatureAverageWeekly == null)
            throw new TemperatureAggregateNotFoundException(sensorId);

        return temperatureAggregateResourceAssembler.toModel(temperatureAverageWeekly);
    }

    /**
     * Compute monthly entity model.
     *
     * @param sensorId the sensor id
     * @param date     the date
     * @param pageable the pageable
     * @return the entity model
     */
    @Operation (summary = "Get monthly average of a sensor of a given date in the month")
    @GetMapping ("/temperatureaverage/monthly/{sensorId}")
    public EntityModel<TemperatureAggregate> getMonthlyAverage(@PathVariable Long sensorId,
                                                           @RequestParam Instant date,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureAverageMonthly temperatureAverageMonthly =
                temperatureAggregateRepository.findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (temperatureAverageMonthly == null)
            throw new TemperatureAggregateNotFoundException(sensorId);

        return temperatureAggregateResourceAssembler.toModel(temperatureAverageMonthly);
    }

    /**
     * Gets daily max.
     *
     * @param sensorId the sensor id
     * @param date     the date
     * @param pageable the pageable
     * @return the daily max
     */
    @Operation (summary = "Get daily maximum of a sensor at a given date")
    @GetMapping ("/temperaturemaximum/daily/{sensorId}")
    public EntityModel<TemperatureAggregate> getDailyMax(@PathVariable Long sensorId, @RequestParam Instant date,
                                                      @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureMaximumDaily temperatureMaximumDaily =
                temperatureAggregateRepository.findTemperatureMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MAX),
                                                                                                                                 LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MIN));

        if (temperatureMaximumDaily == null)
            throw new TemperatureAggregateNotFoundException(sensorId);

        return temperatureAggregateResourceAssembler.toModel(temperatureMaximumDaily);
    }

    /**
     * Gets weekly max.
     *
     * @param sensorId the sensor id
     * @param date     the date
     * @param pageable the pageable
     * @return the weekly max
     */
    @Operation (summary = "Get weekly maximum of a sensor of a given date in the week")
    @GetMapping ("/temperaturemaximum/weekly/{sensorId}")
    public EntityModel<TemperatureAggregate> getWeeklyMax(@PathVariable Long sensorId, @RequestParam Instant date,
                                                         @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureMaximumWeekly temperatureMaximumWeekly =
                temperatureAggregateRepository.findTemperatureMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (temperatureMaximumWeekly == null)
            throw new TemperatureAggregateNotFoundException(sensorId);

        return temperatureAggregateResourceAssembler.toModel(temperatureMaximumWeekly);
    }

    /**
     * Gets monthly max.
     *
     * @param sensorId the sensor id
     * @param date     the date
     * @param pageable the pageable
     * @return the monthly max
     */
    @Operation (summary = "Get monthly maximum of a sensor of a given date in the week")
    @GetMapping ("/temperaturemaximum/monthly/{sensorId}")
    public EntityModel<TemperatureAggregate> getMonthlyMax(@PathVariable Long sensorId,
                                                           @RequestParam Instant date,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureMaximumMonthly temperatureMaximumMonthly =
                temperatureAggregateRepository.findTemperatureMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (temperatureMaximumMonthly == null)
            throw new TemperatureAggregateNotFoundException(sensorId);

        return temperatureAggregateResourceAssembler.toModel(temperatureMaximumMonthly);
    }

    /**
     * Gets daily min.
     *
     * @param sensorId the sensor id
     * @param date     the date
     * @param pageable the pageable
     * @return the daily min
     */
    @Operation (summary = "Get daily minimum of a sensor at a given date")
    @GetMapping ("/temperatureminimum/daily/{sensorId}")
    public EntityModel<TemperatureAggregate> getDailyMin(@PathVariable Long sensorId, @RequestParam Instant date,
                                                         @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureMinimumDaily temperatureMinimumDaily =
                temperatureAggregateRepository.findTemperatureMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MAX),
                                                                                                                                LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MIN));

        if (temperatureMinimumDaily == null)
            throw new TemperatureAggregateNotFoundException(sensorId);

        return temperatureAggregateResourceAssembler.toModel(temperatureMinimumDaily);
    }

    /**
     * Gets weekly min.
     *
     * @param sensorId the sensor id
     * @param date     the date
     * @param pageable the pageable
     * @return the weekly min
     */
    @Operation (summary = "Get weekly minimum of a sensor of a given date in the week")
    @GetMapping ("/temperatureminimum/weekly/{sensorId}")
    public EntityModel<TemperatureAggregate> getWeeklyMin(@PathVariable Long sensorId, @RequestParam Instant date,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureMinimumWeekly temperatureMinimumWeekly =
                temperatureAggregateRepository.findTemperatureMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (temperatureMinimumWeekly == null)
            throw new TemperatureAggregateNotFoundException(sensorId);

        return temperatureAggregateResourceAssembler.toModel(temperatureMinimumWeekly);
    }

    /**
     * Gets monthly min.
     *
     * @param sensorId the sensor id
     * @param date     the date
     * @param pageable the pageable
     * @return the monthly min
     */
    @Operation (summary = "Get monthly minimum of a sensor of a given date in the week")
    @GetMapping ("/temperatureminimum/monthly/{sensorId}")
    public EntityModel<TemperatureAggregate> getMonthlyMin(@PathVariable Long sensorId,
                                                           @RequestParam Instant date,
                                                           @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureMinimumMonthly temperatureMinimumMonthly =
                temperatureAggregateRepository.findTemperatureMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (temperatureMinimumMonthly == null)
            throw new TemperatureAggregateNotFoundException(sensorId);

        return temperatureAggregateResourceAssembler.toModel(temperatureMinimumMonthly);
    }

}
