package com.codecool.videoservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Recommendation {

    @Id
    @GeneratedValue
    private Long id;

    private Integer rating;

    @Column(columnDefinition = "text")
    private String comment;

    @JsonIgnore
    private Long videoId;
}
