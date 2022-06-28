package com.bsep.admin;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableMongoRepositories
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
    @Bean
    public KieContainer kieContainer() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("admin.kjar", "admin-kjar", "0.0.1-SNAPSHOT"));
        KieScanner kScanner = ks.newKieScanner(kContainer);
        kScanner.start(10000);
        return kContainer;
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
