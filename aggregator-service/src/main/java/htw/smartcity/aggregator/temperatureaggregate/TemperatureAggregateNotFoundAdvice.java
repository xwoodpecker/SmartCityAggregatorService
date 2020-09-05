package htw.smartcity.aggregator.temperatureaggregate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Temperature aggregate not found advice.
 */
@ControllerAdvice
public class TemperatureAggregateNotFoundAdvice
{
    /**
     * Temperature aggregate not found handler string.
     *
     * @param ex the ex
     * @return the string
     */
    @ResponseBody
    @ExceptionHandler (TemperatureAggregateNotFoundException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND)
    String temperatureAggregateNotFoundHandler(TemperatureAggregateNotFoundException ex){
        return ex.getMessage();
    }
}
