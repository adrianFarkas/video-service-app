package com.codecool.videoservice.repository;

import com.codecool.videoservice.model.VideoRate;
import com.codecool.videoservice.model.user.VideoAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface VideoRateRepository extends JpaRepository<VideoRate, Long> {

    List<VideoRate> findAllByVideoId(Long id);

    VideoRate findByVideoIdAndVideoAppUser(Long id, VideoAppUser user);

    void deleteByVideoIdAndVideoAppUser(Long id, VideoAppUser user);
}
