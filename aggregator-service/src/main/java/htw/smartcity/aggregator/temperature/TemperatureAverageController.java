package htw.smartcity.aggregator.temperature;

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


    /**
     * Instantiates a new Temperature average controller.
     *
     * @param temperatureAverageRepository        the temperature average repository
     * @param temperatureAverageResourceAssembler the temperature average resource assembler
     * @param temperatureRepository               the temperature repository
     */
    public TemperatureAverageController(TemperatureAverageRepository temperatureAverageRepository,
                                        TemperatureAverageResourceAssembler temperatureAverageResourceAssembler, TemperatureRepository temperatureRepository) {
        this.temperatureAverageRepository = temperatureAverageRepository;
        this.temperatureAverageResourceAssembler = temperatureAverageResourceAssembler;
        this.temperatureRepository = temperatureRepository;
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
    @Operation (summary = "Compute daily average of a sensor")
    @GetMapping ("/temperatureaverage/daily/{sensorId}")
    public EntityModel<TemperatureAverage> computeDaily(@PathVariable Long sensorId,
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
        //temperatureAverageDaily.setSensor(sensorId);
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
    public EntityModel<TemperatureAverage> computeWeekly(@PathVariable Long sensorId, @Parameter (hidden = true) Pageable pageable)
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
    public EntityModel<TemperatureAverage> computeMonthly(@PathVariable Long sensorId, @Parameter (hidden = true) Pageable pageable)
    {
        return one((long) 1);
    }

}
