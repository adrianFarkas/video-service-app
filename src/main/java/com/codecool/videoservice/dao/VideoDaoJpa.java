package com.codecool.videoservice.dao;

import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.model.VideoDetails;
import com.codecool.videoservice.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoDaoJpa implements VideoDao {

    @Autowired
    private VideoRepository videoRepository;

    public List<VideoDetails> findAllVideos() {
        return videoRepository.getAllVideo();
    }

    public Video findVideoById(Long id) {
        return videoRepository.findById(id).orElse(null);
    }

    public Video updateById(Long id, Video video) {
        videoRepository.updateById(id, video);
        return videoRepository.findById(id).orElse(null);
    }
}
