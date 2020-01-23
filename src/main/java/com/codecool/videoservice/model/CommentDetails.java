package com.codecool.videoservice.model;

import com.codecool.videoservice.model.user.BasicUserInformation;
import com.codecool.videoservice.model.user.VideoAppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDetails {

    public CommentDetails(Comment c) {
        VideoAppUser user = c.getVideoAppUser();
        this.id = c.getId();
        this.comment = c.getComment();
        this.creationDate = c.getCreationDate();
        this.author = new BasicUserInformation(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfileImg()
        );
    }

    private Long id;
    private String comment;
    private LocalDateTime creationDate;
    private BasicUserInformation author;
}
