package htw.smartcity.aggregator.airqualityaggregator;

import htw.smartcity.aggregator.airquality.AirQualityRepository;
import htw.smartcity.aggregator.sensor.SensorRepository;
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
 * The type Air quality aggregate controller.
 */
@RestController
@RequestMapping (path = "/airQualities/aggregations")
@Tag (name = "Air Qualities Aggregations", description = "Endpoint to get air quality averages, maximum and minimum")
@SecurityRequirement (name = "basic")
public class AirQualityAggregateController
{
    private AirQualityAggregateRepository        airQualityAggregateRepository;
    private AirQualityAggregateResourceAssembler airQualityAggregateResourceAssembler;

    /**
     * Instantiates a new Air quality aggregate controller.
     *
     * @param airQualityAggregateRepository        the air quality aggregate repository
     * @param airQualityAggregateResourceAssembler the air quality aggregate resource assembler
     */
    public AirQualityAggregateController(AirQualityAggregateRepository        airQualityAggregateRepository,
                                         AirQualityAggregateResourceAssembler airQualityAggregateResourceAssembler) {
        this.airQualityAggregateRepository        = airQualityAggregateRepository;
        this.airQualityAggregateResourceAssembler = airQualityAggregateResourceAssembler;
    }

    /**
     * One entity model.
     *
     * @param airQualityaggregatorId the air qualityaggregator id
     * @return the entity model
     */
    @Operation (summary = "Get a single air quality aggregate measurement")
    @GetMapping ("/{airQualityaggregatorId}")
    public EntityModel<AirQualityAggregate> one(@PathVariable Long airQualityaggregatorId)
    {
        AirQualityAggregate airQualityAggregate = airQualityAggregateRepository.findById(airQualityaggregatorId)
                .orElseThrow(() -> new AirQualityAggregateNotFoundException(airQualityaggregatorId));

        return airQualityAggregateResourceAssembler.toModel(airQualityAggregate);
    }


    /**
     * Gets daily average.
     *
     * @param sensorId the sensor id
     * @param date     the date
     * @param pageable the pageable
     * @return the daily average
     */
    @Operation (summary = "Get daily average of a sensor at a given date")
    @GetMapping ("/airqualityaverage/daily/{sensorId}")
    public EntityModel<AirQualityAverageDaily> getDailyAverage(@PathVariable Long sensorId,
                                                             @RequestParam Instant date,
                                                             @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityAverageDaily airQualityAverageDaily =
                airQualityAggregateRepository.findAirQualityAverageDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(
                        sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MAX),
                        LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MIN));

        if (airQualityAverageDaily == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityAverageDaily);

    }


    /**
     * Gets weekly average.
     *
     * @param sensorId the sensor id
     * @param date     the date
     * @param pageable the pageable
     * @return the weekly average
     */
    @Operation (summary = "Get weekly average of a sensor of a given date in the week")
    @GetMapping ("/airqualityaverage/weekly/{sensorId}")
    public EntityModel<AirQualityAverageWeekly> getWeeklyAverage(@PathVariable Long sensorId,
                                                              @RequestParam Instant date,
                                                              @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityAverageWeekly airQualityAverageWeekly =
                airQualityAggregateRepository.findAirQualityAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (airQualityAverageWeekly == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityAverageWeekly);
    }


    /**
     * Gets monthly average.
     *
     * @param sensorId the sensor id
     * @param date     the date
     * @param pageable the pageable
     * @return the monthly average
     */
    @Operation (summary = "Get monthly average of a sensor of a given date in the month")
    @GetMapping ("/airqualityaverage/monthly/{sensorId}")
    public EntityModel<AirQualityAverageMonthly> getMonthlyAverage(@PathVariable Long sensorId,
                                                               @RequestParam Instant date,
                                                               @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityAverageMonthly airQualityAverageMonthly =
                airQualityAggregateRepository.findAirQualityAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (airQualityAverageMonthly == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityAverageMonthly);
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
    @GetMapping ("/airqualitymaximum/daily/{sensorId}")
    public EntityModel<AirQualityMaximumDaily> getDailyMax(@PathVariable Long sensorId, @RequestParam Instant date,
                                                         @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityMaximumDaily airQualityMaximumDaily =
                airQualityAggregateRepository.findAirQualityMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MAX),
                                                                                                                                LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MIN));

        if (airQualityMaximumDaily == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityMaximumDaily);
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
    @GetMapping ("/airqualitymaximum/weekly/{sensorId}")
    public EntityModel<AirQualityMaximumWeekly> getWeeklyMax(@PathVariable Long sensorId, @RequestParam Instant date,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityMaximumWeekly airQualityMaximumWeekly =
                airQualityAggregateRepository.findAirQualityMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (airQualityMaximumWeekly == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityMaximumWeekly);
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
    @GetMapping ("/airqualitymaximum/monthly/{sensorId}")
    public EntityModel<AirQualityMaximumMonthly> getMonthlyMax(@PathVariable Long sensorId,
                                                           @RequestParam Instant date,
                                                           @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityMaximumMonthly airQualityMaximumMonthly =
                airQualityAggregateRepository.findAirQualityMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (airQualityMaximumMonthly == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityMaximumMonthly);
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
    @GetMapping ("/airqualityminimum/daily/{sensorId}")
    public EntityModel<AirQualityMinimumDaily> getDailyMin(@PathVariable Long sensorId, @RequestParam Instant date,
                                                         @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityMinimumDaily airQualityMinimumDaily =
                airQualityAggregateRepository.findAirQualityMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MAX),
                                                                                                                                LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MIN));

        if (airQualityMinimumDaily == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityMinimumDaily);
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
    @GetMapping ("/airqualityminimum/weekly/{sensorId}")
    public EntityModel<AirQualityMinimumWeekly> getWeeklyMin(@PathVariable Long sensorId, @RequestParam Instant date,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityMinimumWeekly airQualityMinimumWeekly =
                airQualityAggregateRepository.findAirQualityMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (airQualityMinimumWeekly == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityMinimumWeekly);
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
    @GetMapping ("/airqualityminimum/monthly/{sensorId}")
    public EntityModel<AirQualityMinimumMonthly> getMonthlyMin(@PathVariable Long sensorId,
                                                           @RequestParam Instant date,
                                                           @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityMinimumMonthly airQualityMinimumMonthly =
                airQualityAggregateRepository.findAirQualityMinimumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (airQualityMinimumMonthly == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityMinimumMonthly);
    }
}
