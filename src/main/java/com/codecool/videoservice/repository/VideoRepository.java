package com.codecool.videoservice.repository;

import com.codecool.videoservice.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Modifying
    @Query("UPDATE Video SET name = :#{#video.name}, url = :#{#video.url} WHERE id = :id")
    void updateById(Long id, Video video);

}
