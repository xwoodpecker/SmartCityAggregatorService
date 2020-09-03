package htw.smartcity.aggregator.security;

import htw.smartcity.aggregator.airquality.AirQuality;
import htw.smartcity.aggregator.airquality.AirQualityController;
import htw.smartcity.aggregator.sensor.SensorController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserResourceAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User user){
        return EntityModel.of(user);
    }
}
