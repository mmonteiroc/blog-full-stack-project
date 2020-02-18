package com.esliceu.proyectoglobal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Code created by: mmonteirocl
 * Email: miguelmonteiroclaveri@gmail.com
 * Date: 18/02/2020
 * Package: com.esliceu.proyectoglobal.config
 * Project: backend
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@PropertySource("classpath:application.properties")
public class CorsConfiguration implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins(environment.getProperty("cors.allowed"))
                .allowedMethods("PUT", "POST", "GET", "DELETE", "OPTIONS");

    }

}