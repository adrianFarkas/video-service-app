package com.codecool.videoservice.dao;

import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoDaoJpa implements VideoDao {

    @Autowired
    private VideoRepository videoRepository;

    public List<Video> findAllVideos() {
        return videoRepository.findAll();
    }

    public Video updateById(Long id, Video video) {
        videoRepository.updateById(id, video);
        return videoRepository.findById(id).orElse(null);
    }
}
