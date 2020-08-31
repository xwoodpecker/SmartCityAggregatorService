package htw.smartcity.aggregator.airquality;

import htw.smartcity.aggregator.temperature.TemperatureNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Air quality not found advice.
 */
@ControllerAdvice
public class AirQualityNotFoundAdvice {
    /**
     * Air qualitye not found handler string.
     *
     * @param ex the ex
     * @return the string
     */
    @ResponseBody
    @ExceptionHandler(AirQualityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String airQualityeNotFoundHandler(AirQualityNotFoundException ex){
        return ex.getMessage();
    }
}
