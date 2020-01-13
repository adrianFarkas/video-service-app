package com.codecool.videoservice.model;

import com.codecool.videoservice.model.user.VideoAppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Video {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private String videoLink;

    private String thumbNailLink;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "video")
    @ToString.Exclude
    private List<Comment> comments;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private VideoAppUser videoAppUser;
}
