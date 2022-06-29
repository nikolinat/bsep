package com.bsep.admin;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bsep.admin.app.sockets.SocketsModule;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableMongoRepositories
public class AdminApplication {
    @Value("${rt-server.host}")
    private String host;

    @Value("${rt-server.port}")
    private Integer port;

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        // noinspection NullableProblems
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

    @Bean
    public KieContainer kieContainer() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("admin.kjar", "admin-kjar", "0.0.1-SNAPSHOT"));
        KieScanner kScanner = ks.newKieScanner(kContainer);
        kScanner.start(10000);
        return kContainer;
    }

    @Bean
    public SocketIOServer socketIOServer() throws FileNotFoundException {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setKeyStore(new FileInputStream(new File("src/main/java/files/keystores/bsep.jks")));
        config.setKeyStorePassword("bsep");
        config.setPort(port);
        return new SocketIOServer(config);
    }

    @Bean
    CommandLineRunner runner(SocketIOServer socketIOServer, SocketsModule socketsModule) {
        return args -> {
            socketIOServer.start();

        };
    }

}
