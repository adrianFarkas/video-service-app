package com.codecool.videoservice.repository;

import com.codecool.videoservice.model.user.BasicUserInformation;
import com.codecool.videoservice.model.user.VideoAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<VideoAppUser,String> {

    Optional<VideoAppUser> findByEmail(String email);

    @Query("SELECT NEW com.codecool.videoservice.model.user.BasicUserInformation(" +
            "v.id, v.firstName, v.lastName, v.profileImg)" +
            "FROM VideoAppUser v WHERE v.email = :email")
    BasicUserInformation getBasicInformationByEmail(String email);

    @Query("SELECT NEW com.codecool.videoservice.model.user.BasicUserInformation(" +
            "v.id, v.firstName, v.lastName, v.profileImg)" +
            "FROM VideoAppUser v WHERE v.id = :id")
    BasicUserInformation getBasicInformationById(String id);

    @Modifying
    @Query("UPDATE VideoAppUser " +
            "SET profileImg = :imageLink " +
            "WHERE id = :id")
    void updateProfileImgById(String id, String imageLink);
}
