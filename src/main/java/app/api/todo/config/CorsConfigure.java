package app.api.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfigure implements WebMvcConfigurer{

	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/todo/**")
			.allowedOrigins("http://127.0.0.1:51387")
			.allowedMethods("*")
			.allowedHeaders("*")
			.allowCredentials(false);
	}
}
