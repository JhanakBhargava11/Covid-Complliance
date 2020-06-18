package com.xebia.innovationportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/innovation-portal/api/v1/**")
				.allowedOrigins("https://iportal.herokuapp.com", "http://iportal.herokuapp.com")
				.allowedMethods("PUT", "DELETE").allowCredentials(false).maxAge(3600);
	}
}
