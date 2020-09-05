package htw.smartcity.aggregator.airqualityaggregator;

import htw.smartcity.aggregator.airquality.AirQuality;
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

@RestController
@RequestMapping (path = "/airQualities/aggregations")
@Tag (name = "Air Qualities Aggregations", description = "Endpoint to get air quality averages, maximum and minimum")
@SecurityRequirement (name = "basic")
public class AirQualityAggregateController
{
    private AirQualityAggregateRepository        airQualityAggregateRepository;
    private AirQualityAggregateResourceAssembler airQualityAggregateResourceAssembler;
    private AirQualityRepository                 airQualityRepository;
    private SensorRepository                     sr;


    public AirQualityAggregateController(AirQualityAggregateRepository        airQualityAggregateRepository,
                                         AirQualityAggregateResourceAssembler airQualityAggregateResourceAssembler,
                                         AirQualityRepository                 airQualityRepository,
                                         SensorRepository                     sr) {
        this.airQualityAggregateRepository        = airQualityAggregateRepository;
        this.airQualityAggregateResourceAssembler = airQualityAggregateResourceAssembler;
        this.airQualityRepository                 = airQualityRepository;
        this.sr                                    = sr;
    }

    @Operation (summary = "Get a single air quality aggregate measurement")
    @GetMapping ("/{airQualityaggregatorId}")
    public EntityModel<AirQualityAggregate> one(@PathVariable Long airQualityaggregatorId)
    {
        AirQualityAggregate airQualityAggregate = airQualityAggregateRepository.findById(airQualityaggregatorId)
                .orElseThrow(() -> new AirQualityAggregateNotFoundException(airQualityaggregatorId));

        return airQualityAggregateResourceAssembler.toModel(airQualityAggregate);
    }


    @Operation (summary = "Get daily average of a sensor at a given date")
    @GetMapping ("/airqualityaverage/daily/{sensorId}")
    public EntityModel<AirQualityAggregate> getDailyAverage(@PathVariable Long sensorId,
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


    @Operation (summary = "Get weekly average of a sensor of a given date in the week")
    @GetMapping ("/airqualityaverage/weekly/{sensorId}")
    public EntityModel<AirQualityAggregate> getWeeklyAverage(@PathVariable Long sensorId,
                                                              @RequestParam Instant date,
                                                              @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityAverageWeekly airQualityAverageWeekly =
                airQualityAggregateRepository.findAirQualityAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (airQualityAverageWeekly == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityAverageWeekly);
    }


    @Operation (summary = "Get monthly average of a sensor of a given date in the month")
    @GetMapping ("/airqualityaverage/monthly/{sensorId}")
    public EntityModel<AirQualityAggregate> getMonthlyAverage(@PathVariable Long sensorId,
                                                               @RequestParam Instant date,
                                                               @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityAverageMonthly airQualityAverageMonthly =
                airQualityAggregateRepository.findAirQualityAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (airQualityAverageMonthly == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityAverageMonthly);
    }

    @Operation (summary = "Get daily maximum of a sensor at a given date")
    @GetMapping ("/airqualitymaximum/daily/{sensorId}")
    public EntityModel<AirQualityAggregate> getDailyMax(@PathVariable Long sensorId, @RequestParam Instant date,
                                                         @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityMaximumDaily airQualityMaximumDaily =
                airQualityAggregateRepository.findAirQualityMaximumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MAX),
                                                                                                                                LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MIN));

        if (airQualityMaximumDaily == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityMaximumDaily);
    }

    @Operation (summary = "Get weekly maximum of a sensor of a given date in the week")
    @GetMapping ("/airqualitymaximum/weekly/{sensorId}")
    public EntityModel<AirQualityAggregate> getWeeklyMax(@PathVariable Long sensorId, @RequestParam Instant date,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityMaximumWeekly airQualityMaximumWeekly =
                airQualityAggregateRepository.findAirQualityMaximumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (airQualityMaximumWeekly == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityMaximumWeekly);
    }

    @Operation (summary = "Get monthly maximum of a sensor of a given date in the week")
    @GetMapping ("/airqualitymaximum/monthly/{sensorId}")
    public EntityModel<AirQualityAggregate> getMonthlyMax(@PathVariable Long sensorId,
                                                           @RequestParam Instant date,
                                                           @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityMaximumMonthly airQualityMaximumMonthly =
                airQualityAggregateRepository.findAirQualityMaximumMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (airQualityMaximumMonthly == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityMaximumMonthly);
    }

    @Operation (summary = "Get daily minimum of a sensor at a given date")
    @GetMapping ("/airqualityminimum/daily/{sensorId}")
    public EntityModel<AirQualityAggregate> getDailyMin(@PathVariable Long sensorId, @RequestParam Instant date,
                                                         @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityMinimumDaily airQualityMinimumDaily =
                airQualityAggregateRepository.findAirQualityMinimumDailyBySensorIdAndTimeLessThanEqualAndTimeGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MAX),
                                                                                                                                LocalDateTime.ofInstant(date, ZoneOffset.UTC).with(LocalTime.MIN));

        if (airQualityMinimumDaily == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityMinimumDaily);
    }

    @Operation (summary = "Get weekly minimum of a sensor of a given date in the week")
    @GetMapping ("/airqualityminimum/weekly/{sensorId}")
    public EntityModel<AirQualityAggregate> getWeeklyMin(@PathVariable Long sensorId, @RequestParam Instant date,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        AirQualityMinimumWeekly airQualityMinimumWeekly =
                airQualityAggregateRepository.findAirQualityMinimumWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, LocalDateTime.ofInstant(date, ZoneOffset.UTC), LocalDateTime.ofInstant(date, ZoneOffset.UTC));

        if (airQualityMinimumWeekly == null)
            throw new AirQualityAggregateNotFoundException(sensorId);

        return airQualityAggregateResourceAssembler.toModel(airQualityMinimumWeekly);
    }

    @Operation (summary = "Get monthly minimum of a sensor of a given date in the week")
    @GetMapping ("/airqualityminimum/monthly/{sensorId}")
    public EntityModel<AirQualityAggregate> getMonthlyMin(@PathVariable Long sensorId,
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
