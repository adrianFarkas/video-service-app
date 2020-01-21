package com.codecool.videoservice.dao;

import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.model.VideoDetails;
import com.codecool.videoservice.model.user.VideoAppUser;

import java.util.List;

public interface VideoDao {

    List<VideoDetails> findAllVideos();

    VideoDetails findVideoById(Long id);

    Video updateById(Long id, Video video);

    Video addNewVideo(VideoAppUser user, String title, String description,
                      String videoLink, String thumbnailLink);
}
