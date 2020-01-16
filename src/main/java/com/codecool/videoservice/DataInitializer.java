package com.codecool.videoservice;

import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.model.user.VideoAppUser;
import com.codecool.videoservice.repository.VideoRepository;
import com.codecool.videoservice.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private AuthService authService;

    @Value("${cloudfront.link}")
    private String cloudFront;

    @Override
    public void run(String... args) {

        String customId = "feda081c-9cd2-44ea-90eb-1a0c8131b34d";
        String basicLink = String.format("%s/%s", cloudFront, customId);

        VideoAppUser appUser = VideoAppUser.builder()
                .id(customId)
                .email("adrian@example.com")
                .firstName("Farkas")
                .lastName("Adrián")
                .password("Qwerty1")
                .profileImg(String.format("%s/%s", basicLink, "my-img.jpg"))
                .build();

        Video video1 = Video.builder()
                .title("Sample Video 1")
                .description("Est bi-color saga, cesaris. Coordinataes observare!")
                .videoLink(String.format("%s/%s", basicLink, "sample_video_1_2020_01_16_15_47.mp4"))
                .thumbNailLink(String.format("%s/%s", basicLink, "sample_video_1_2020_01_16_15_47.jpg"))
                .videoAppUser(appUser)
                .build();

        Video video2 = Video.builder()
                .title("Sample Video 2")
                .description("Noster adiurator mechanice imperiums cedrium est.\n" +
                        "Solitudo bi-color lanista est.")
                .videoLink(String.format("%s/%s", basicLink, "sample_video_2_2020_01_16_15_51.mp4"))
                .thumbNailLink(String.format("%s/%s", basicLink, "sample_video_2_2020_01_16_15_51.jpg"))
                .videoAppUser(appUser)
                .build();

        Video video3 = Video.builder()
                .title("Sample Video 3")
                .description("Noster adiurator mechanice imperiums cedrium est.\n" +
                        "Solitudo bi-color lanista est.\n\n" +
                        "Brodiums trabem, tanquam varius adelphis.")
                .videoLink(String.format("%s/%s", basicLink, "sample_video_3_2020_01_16_15_53.mp4"))
                .thumbNailLink(String.format("%s/%s", basicLink, "sample_video_3_2020_01_16_15_53.jpg"))
                .videoAppUser(appUser)
                .build();

        Video video4 = Video.builder()
                .title("Sample Video 4")
                .description("Heu, clemens xiphias! Vae, festus orexis!")
                .videoLink(String.format("%s/%s", basicLink, "sample_video_4_2020_01_16_15_54.mp4"))
                .thumbNailLink(String.format("%s/%s", basicLink, "sample_video_4_2020_01_16_15_54.jpg"))
                .videoAppUser(appUser)
                .build();

        authService.saveUser(appUser);
        videoRepository.saveAll(Arrays.asList(video1, video2, video3, video4));

        log.debug("printing all videos...");
        videoRepository.findAll().forEach(v -> log.debug(" Video :" + v.toString()));
    }
}
