package com.codecool.videoservice.model;

import com.codecool.videoservice.model.user.VideoAppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(RateId.class)
@Builder
@Entity
public class VideoRate {

    @Id
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Video video;

    @Id
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private VideoAppUser videoAppUser;

    @Enumerated(EnumType.STRING)
    private RateType rate;
}
