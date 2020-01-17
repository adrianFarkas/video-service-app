package com.codecool.videoservice.model;

import com.codecool.videoservice.model.user.VideoAppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "text")
    private String comment;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @ManyToOne
    @JsonIgnore
    private Video video;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private VideoAppUser videoAppUser;

    @Column(name = "video_app_user_id", insertable = false, updatable = false)
    private String userId;
}
