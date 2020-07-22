package htw.smartcity.aggregator.sensor;

import htw.smartcity.aggregator.temperature.TemperatureNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SensorNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(SensorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String sensorNotFoundHandler(SensorNotFoundException ex){
        return ex.getMessage();
    }
}
