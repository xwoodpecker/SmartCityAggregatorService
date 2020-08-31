package htw.smartcity.aggregator.parking;

import htw.smartcity.aggregator.temperature.TemperatureNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Parking group not found advice.
 */
@ControllerAdvice
public class ParkingGroupNotFoundAdvice {
    /**
     * Parking group not found handler string.
     *
     * @param ex the ex
     * @return the string
     */
    @ResponseBody
    @ExceptionHandler(ParkingGroupNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String parkingGroupNotFoundHandler(ParkingGroupNotFoundException ex){
        return ex.getMessage();
    }
}
