package com.hyunki.pointapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PointApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PointApiApplication.class, args);
    }

}
