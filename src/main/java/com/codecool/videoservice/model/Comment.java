package com.codecool.videoservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {

    @Id
    @Getter
    @GeneratedValue
    private Long id;

    @Getter
    @Column(columnDefinition = "text")
    private String comment;

    @Getter
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @JoinColumn(name = "video_id")
    @ManyToOne
    @JsonIgnore
    private Video video;

    @Getter
    @Column(name = "video_id", insertable = false, updatable = false)
    private Long videoId;
}
