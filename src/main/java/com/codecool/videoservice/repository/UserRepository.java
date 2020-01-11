package com.codecool.videoservice.repository;

import com.codecool.videoservice.model.user.BasicUserInformation;
import com.codecool.videoservice.model.user.VideoAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<VideoAppUser,String> {

    Optional<VideoAppUser> findByEmail(String email);

    @Query("SELECT NEW com.codecool.videoservice.model.user.BasicUserInformation(" +
            "v.firstName, v.lastName, v.email, v.profileImg)" +
            "FROM VideoAppUser v WHERE v.email = :email")
    BasicUserInformation getBasicInformationByEmail(String email);
}
