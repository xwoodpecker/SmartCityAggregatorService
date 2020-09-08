package htw.smartcity.aggregator;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.SpringDocUtils;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The type Aggregator application.
 */
@SpringBootApplication
@EnableScheduling
public class AggregatorApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(AggregatorApplication.class, args);
	}

	/**
	 * Custom open api open api.
	 *
	 * @return the open api
	 */
	@Bean
	public OpenAPI customOpenAPI() {
		SpringDocUtils.getConfig().replaceWithClass(org.springframework.data.domain.Pageable.class, Pageable.class);

		return new OpenAPI()
				.components(new Components().addSecuritySchemes("basic", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic").in(SecurityScheme.In.HEADER).name("Authorization")))
						.info(new Info().title("SmartCity Aggregator Service").version("v0"));

	}
}