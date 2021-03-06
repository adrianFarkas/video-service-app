package com.codecool.videoservice.model;

import com.codecool.videoservice.model.user.BasicUserInformation;
import com.codecool.videoservice.model.user.VideoAppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class VideoDetails {

    public VideoDetails(Video v) {
        VideoAppUser user = v.getVideoAppUser();
        this.id = v.getId();
        this.title = v.getTitle();
        this.description = v.getDescription();
        this.videoLink = v.getVideoLink();
        this.thumbnailLink = v.getThumbNailLink();
        this.creationDate = v.getCreationDate();
        this.author = new BasicUserInformation(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfileImg()
        );
    }

    private Long id;
    private String title;
    private String description;
    private String videoLink;
    private String thumbnailLink;
    private LocalDateTime creationDate;
    private BasicUserInformation author;
}
