package htw.smartcity.aggregator.temperature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TemperatureController {

    @Autowired
    private TemperatureRepository temperatureRepository;

    @RequestMapping("/")
    @ResponseBody
    public void test(){
        Temperature temperature = new Temperature();
        temperature.setTime("24:00:00");
        temperature.setValue(23.5);
        temperatureRepository.save(temperature);
    }
}
