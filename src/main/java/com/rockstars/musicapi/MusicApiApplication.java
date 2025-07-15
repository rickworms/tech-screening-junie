package com.rockstars.musicapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for the Music API.
 * 
 * This application provides RESTful endpoints for managing bands and songs,
 * with support for CRUD operations and filtering capabilities.
 */
@SpringBootApplication
public class MusicApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicApiApplication.class, args);
    }
}