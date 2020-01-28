package com.codecool.videoservice.model;

import com.codecool.videoservice.model.user.VideoAppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    public ConfirmationToken(VideoAppUser user) {
        this.videoAppUser = user;
        this.createdDate = new Date();
        this.confirmationToken = UUID.randomUUID().toString();
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne
    private VideoAppUser videoAppUser;
}