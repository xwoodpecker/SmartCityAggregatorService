package htw.smartcity.aggregator.airquality;

import htw.smartcity.aggregator.temperature.TemperatureNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AirqualityNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(AirqualityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String airqualityeNotFoundHandler(AirqualityNotFoundException ex){
        return ex.getMessage();
    }
}
