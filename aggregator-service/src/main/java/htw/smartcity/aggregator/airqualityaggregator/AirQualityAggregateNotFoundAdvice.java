package htw.smartcity.aggregator.airqualityaggregator;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AirQualityAggregateNotFoundAdvice
{
    @ResponseBody
    @ExceptionHandler (AirQualityAggregateNotFoundException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND)
    String temperatureAggregateNotFoundHandler(AirQualityAggregateNotFoundException ex){
        return ex.getMessage();
    }
}
