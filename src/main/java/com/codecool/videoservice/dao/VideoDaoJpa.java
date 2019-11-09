package com.codecool.videoservice.dao;

import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.repository.RecommendationRepository;
import com.codecool.videoservice.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoDaoJpa implements VideoDao {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    public List<Video> findAllVideos() {
        return videoRepository.findAll();
    }

    public Video getVideoWithRecommendation(Long id) {
        Video video = videoRepository.findById(id).orElse(null);
        if (video != null) video.setRecommendations(recommendationRepository.findAllByVideoId(id));
        return video;
    }
}
