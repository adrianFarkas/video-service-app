package com.codecool.videoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDetails {

    private Long id;
    private String title;
    private String description;
    private String videoLink;
    private String thumbnailLink;
    private LocalDateTime creationDate;
    private String userId;
}
