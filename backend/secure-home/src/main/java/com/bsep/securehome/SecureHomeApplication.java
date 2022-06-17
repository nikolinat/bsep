package com.bsep.securehome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class SecureHomeApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SecureHomeApplication.class);
        Map<String, Object> props = new HashMap<>();
        // props.put("server.ssl.key-store", "src/main/java/files/keystores/bsep.jks");
        // props.put("server.ssl.key-store-password", "bsep");
        // props.put("server.ssl.key-store-type", "jks");
        // props.put("server.ssl.key-alias", "root");
        // props.put("server.ssl.key-password", "bsep");
         props.put("server.port", "8444");
        app.setDefaultProperties(props);
        app.run(args);
    }

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
                        .allowedOrigins("http://localhost:8080");
            }
        };
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mailtrap.io");
        mailSender.setPort(2525);

        mailSender.setUsername("df2937cc739ea9");
        mailSender.setPassword("b3cb1c2c60096a");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "true");
        props.put("management.health.mail.enabled", "false");
        return mailSender;
    }

}


