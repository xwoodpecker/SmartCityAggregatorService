package htw.smartcity.aggregator.temperature;

import htw.smartcity.aggregator.sensor.SensorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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
    public EntityModel<TemperatureAverage> one(@PathVariable Long temperatureaverageId)
    {
        TemperatureAverage temperatureaverage = temperatureAverageRepository.findById(temperatureaverageId)
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
    public EntityModel<TemperatureAverage> getDaily(@PathVariable Long sensorId, @RequestParam LocalDateTime date,
                                                        @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureAverageDaily temperatureAverageDaily =
                temperatureAverageRepository.findTemperatureAverageDailyBySensorIdAndTime(sensorId, date);

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
    public EntityModel<TemperatureAverage> getWeekly(@PathVariable Long sensorId, @RequestParam LocalDateTime date,
                                                         @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureAverageWeekly temperatureAverageWeekly =
                temperatureAverageRepository.findTemperatureAverageWeeklyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, date, date);
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
    public EntityModel<TemperatureAverage> getMonthly(@PathVariable Long sensorId, @RequestParam LocalDateTime date,
                                                          @Parameter (hidden = true) Pageable pageable)
    {
        TemperatureAverageMonthly temperatureAverageMonthly =
                temperatureAverageRepository.findTemperatureAverageMonthlyBySensorIdAndBeginDateLessThanEqualAndEndDateGreaterThanEqual(sensorId, date, date);
        return one((long) 1);
    }

}
