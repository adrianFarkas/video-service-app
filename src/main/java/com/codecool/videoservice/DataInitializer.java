package com.codecool.videoservice;

import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public void run(String... args) {
        Video video1 = Video.builder()
                .name("Codecool-Microservices with Spring Cloud #1")
                .url("https://www.youtube.com/watch?v=tsC4bIP0Jl4")
                .build();

        Video video2 = Video.builder()
                .name("React Context & Hooks Tutorial #1 - Introduction")
                .url("https://www.youtube.com/watch?v=6RhOzQciVwI&list=PL4cUxeGkcC9hNokByJilPg5g9m2APUePI")
                .build();

        Video video3 = Video.builder()
                .name("Programming / Coding / Hacking music")
                .url("https://www.youtube.com/watch?v=l9nh1l8ZIJQ&t=2319s")
                .build();

        Video video4 = Video.builder()
                .name("Töltött sajtos párizsi")
                .url("https://www.youtube.com/watch?v=HFxg7_b2WJo")
                .build();

        videoRepository.saveAll(Arrays.asList(video1, video2, video3, video4));

        log.debug("printing all videos...");
        videoRepository.findAll().forEach(v -> log.debug(" Video :" + v.toString()));
    }
}
