package com.codecool.videoservice.repository;

import com.codecool.videoservice.model.VideoAppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<VideoAppUser,String> {

    Optional<VideoAppUser> findByUserName(String username);

    Optional<VideoAppUser> findByEmail(String email);
}
