package htw.smartcity.aggregator.parking;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Parking not found advice.
 */
@ControllerAdvice
public class ParkingNotFoundAdvice {
    /**
     * Parking not found handler string.
     *
     * @param ex the ex
     * @return the string
     */
    @ResponseBody
    @ExceptionHandler(ParkingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String parkingNotFoundHandler(ParkingNotFoundException ex){
        return ex.getMessage();
    }
}
