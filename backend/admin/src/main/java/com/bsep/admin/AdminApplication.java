package com.bsep.admin;

import com.bsep.admin.app.service.implementation.CaCertificateAliasService;

import org.hibernate.annotations.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication

public class AdminApplication {
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        //noinspection NullableProblems
        return new WebMvcConfigurer() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowedOrigins("*");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AdminApplication.class);
        Map<String, Object> props = new HashMap<>();
        props.put("server.ssl.key-store", "src/main/java/files/keystores/bsep.jks");
        props.put("server.ssl.key-store-password", "bsep");
        props.put("server.ssl.key-store-type", "jks");
        props.put("server.ssl.key-alias", "root");
        props.put("server.ssl.key-password", "bsep");
        props.put("server.port", "8443");
        app.setDefaultProperties(props);
        app.run(args);
    }

}
