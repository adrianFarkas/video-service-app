package com.codecool.videoservice.model;

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

    @JoinColumn(name = "video_id")
    @ManyToOne
    @JsonIgnore
    private Video video;
}
