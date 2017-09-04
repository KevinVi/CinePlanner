package com.example.cinemaplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class CinemaPlannerApplication {
    // extends SpringBootServletInitializer
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(CinemaPlannerApplication.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(CinemaPlannerApplication.class, args);
    }
}
