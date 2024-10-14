package com.adopet.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

  @Value("${cors.allowed.origin}")
  private String allowedOrigin;

  private static final String[] ALLOWED_METHODS = { "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS" };

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins(allowedOrigin)
        .allowedMethods(ALLOWED_METHODS)
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}
