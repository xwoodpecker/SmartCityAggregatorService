package htw.smartcity.aggregator.temperature;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping (path = "/temperatures/averages")
@Tag (name = "Temperature Averages", description = "Endpoint to get temperature averages")
public class TemperatureAverageController
{
    private TemperatureAverageRepository temperatureAverageRepository;
    private TemperatureAverageResourceAssembler temperatureAverageResourceAssembler;
    private TemperatureRepository temperatureRepository;


    public TemperatureAverageController(TemperatureAverageRepository temperatureAverageRepository,
                                        TemperatureAverageResourceAssembler temperatureAverageResourceAssembler, TemperatureRepository temperatureRepository) {
        this.temperatureAverageRepository = temperatureAverageRepository;
        this.temperatureAverageResourceAssembler = temperatureAverageResourceAssembler;
        this.temperatureRepository = temperatureRepository;
    }

    @Operation (summary = "Get a single temperature average measurement")
    @GetMapping ("/{temperatureaverageId}")
    public EntityModel<TemperatureAverage> one(@PathVariable Long temperatureaverageId)
    {
        TemperatureAverage temperatureaverage = temperatureAverageRepository.findById(temperatureaverageId)
                .orElseThrow(() -> new TemperatureNotFoundException(temperatureaverageId));

        return temperatureAverageResourceAssembler.toModel(temperatureaverage);
    }

    @Operation (summary = "Compute daily average of a sensor")
    @GetMapping ("/temperatureaverage/daily")
    public EntityModel<TemperatureAverage> computeDaily(@PathVariable Long sensorId,
                                                        @Parameter (hidden = true) Pageable pageable)
    {
        /*
        Date startTime = new Date();
        startTime.setHours(0);
        startTime.setMinutes(0);
        startTime.setSeconds(0);
        Date endTime = new Date();
        endTime.setHours(23);
        endTime.setMinutes(59);
        endTime.setSeconds(59);

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
        temperatureAverageDaily.setDate(new Date());
        temperatureAverageDaily.setSensor(sensorId);
        temperatureAverageDaily.setValue(sum/count);

        temperatureAverageRepository.save(temperatureAverageDaily);

        return one((long) 1);

         */
        return null;
    }

    @Operation (summary = "Compute daily average of a sensor")
    @GetMapping ("/temperatureaverage/weekly")
    public EntityModel<TemperatureAverage> computeWeekly(@PathVariable Long sensorId, @Parameter (hidden = true) Pageable pageable)
    {
        return one((long) 1);
    }

    @Operation (summary = "Compute daily average of a sensor")
    @GetMapping ("/temperatureaverage/monthly")
    public EntityModel<TemperatureAverage> computeMonthly(@PathVariable Long sensorId, @Parameter (hidden = true) Pageable pageable)
    {
        return one((long) 1);
    }

}
