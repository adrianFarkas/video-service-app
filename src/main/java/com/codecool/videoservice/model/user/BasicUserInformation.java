package com.codecool.videoservice.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicUserInformation {

    private String id;
    private String firstName;
    private String lastName;
    private String profileImg;
}
