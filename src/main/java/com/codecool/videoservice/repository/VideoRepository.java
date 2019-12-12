package com.codecool.videoservice.repository;

import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.model.VideoDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Modifying
    @Query("UPDATE Video SET name = :#{#video.name}, url = :#{#video.url} WHERE id = :id")
    void updateById(Long id, Video video);

    @Query("SELECT NEW com.codecool.videoservice.model.VideoDetails(v.id, v.name, v.url, v.creationDate)" +
            "FROM Video v")
    List<VideoDetails> getAllVideo();

}
