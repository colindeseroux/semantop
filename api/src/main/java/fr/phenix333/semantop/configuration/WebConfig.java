package fr.phenix333.semantop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${cors.allowed.origins}")
	private String allowedOrigins;

	/**
	 * Enable CORS for all endpoints
	 * 
	 * @param registry -> CorsRegistry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(this.allowedOrigins).allowedMethods("*").allowedHeaders("*");
	}

}
