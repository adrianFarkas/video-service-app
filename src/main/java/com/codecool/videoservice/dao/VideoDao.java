package com.codecool.videoservice.dao;

import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.model.VideoDetails;

import java.util.List;

public interface VideoDao {

    List<VideoDetails> findAllVideos();

    VideoDetails findVideoById(Long id);

    Video updateById(Long id, Video video);

    Video saveVideo(String title, String description,
                    String videoLink, String thumbnailLink);
}
