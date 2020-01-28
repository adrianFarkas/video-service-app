package com.codecool.videoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateId implements Serializable {

    private Long video;
    private String videoAppUser;
}
