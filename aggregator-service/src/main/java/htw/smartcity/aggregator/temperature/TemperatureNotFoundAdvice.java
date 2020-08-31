package htw.smartcity.aggregator.temperature;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Temperature not found advice.
 */
@ControllerAdvice
public class TemperatureNotFoundAdvice {
    /**
     * Temperature not found handler string.
     *
     * @param ex the ex
     * @return the string
     */
    @ResponseBody
    @ExceptionHandler(TemperatureNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String temperatureNotFoundHandler(TemperatureNotFoundException ex){
        return ex.getMessage();
    }
}
