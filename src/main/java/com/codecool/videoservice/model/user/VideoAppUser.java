package com.codecool.videoservice.model.user;

import com.codecool.videoservice.model.Comment;
import com.codecool.videoservice.model.ConfirmationToken;
import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.model.VideoRate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class VideoAppUser {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "com.codecool.videoservice.util.IdGenerator")
    private String id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String password;

    private String profileImg;

    @NotNull
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "videoAppUser", cascade = CascadeType.PERSIST)
    private Set<Video> videos;

    @JsonIgnore
    @OneToMany(mappedBy = "videoAppUser", cascade = CascadeType.PERSIST)
    private Set<VideoRate> rates;

    @JsonIgnore
    @OneToMany(mappedBy = "videoAppUser", cascade = CascadeType.PERSIST)
    private Set<Comment> comments;

    @Column(columnDefinition = "boolean default false")
    private boolean enabled;

    @OneToOne(mappedBy = "videoAppUser")
    private ConfirmationToken confirmationToken;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
}
