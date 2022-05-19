package com.bsep.admin.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/*")
                .allowedMethods("HEAD","GET","POST","PUT","DELETE","PATCH").allowedOrigins("http://192.168.1.3:8080");

    }
}
