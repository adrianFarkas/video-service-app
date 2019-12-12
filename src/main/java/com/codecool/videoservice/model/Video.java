package com.codecool.videoservice.model;

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

    private String name;

    private String url;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "video")
    @ToString.Exclude
    private List<Comment> comments;

}
