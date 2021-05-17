package com.projects.travelblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.projects")
public class TravelBlogApplication {

    public static void main(String[] args) {

        SpringApplication.run(TravelBlogApplication.class, args);

    }
}
