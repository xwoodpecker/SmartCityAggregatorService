package htw.smartcity.aggregator.temperature;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TemperatureAggregateNotFoundAdvice
{
    @ResponseBody
    @ExceptionHandler (TemperatureNotFoundException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND)
    String temperatureAggregateNotFoundHandler(TemperatureAggregateNotFoundException ex){
        return ex.getMessage();
    }
}
