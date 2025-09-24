package com.example.My.website.backend.enums;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvironmentConfig {

    @Bean
    public String mongodbUri() {
        return System.getenv("MONGODB_URI") != null ?
                System.getenv("MONGODB_URI") :
                "mongodb+srv://jairam:jairam@cluster0.skyqx.mongodb.net/website?retryWrites=true&w=majority&appName=Cluster0";
    }

    @Bean
    public String jwtSecret() {
        return System.getenv("JWT_SECRET") != null ?
                System.getenv("JWT_SECRET") :
                "mysupersecretkeymysupersecretkeymysupersecretkey";
    }
}
