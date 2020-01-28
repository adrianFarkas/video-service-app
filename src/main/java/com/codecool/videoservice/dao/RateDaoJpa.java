package com.codecool.videoservice.dao;

import com.codecool.videoservice.model.RateType;
import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.model.VideoRate;
import com.codecool.videoservice.model.user.VideoAppUser;
import com.codecool.videoservice.repository.VideoRateRepository;
import com.codecool.videoservice.repository.VideoRepository;
import com.codecool.videoservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RateDaoJpa implements RateDao {

    @Autowired
    private VideoRateRepository videoRateRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private AuthService authService;

    @Override
    public List<VideoRate> getAllRateByVideo(Long videoId) {
        return videoRateRepository.findAllByVideoId(videoId);
    }

    @Override
    public void saveVideoRate(Long videoId, RateType type) {
        VideoAppUser user = authService.getAuthenticatedUser();
        Video video = videoRepository.findById(videoId).orElse(null);
        VideoRate videoRate = VideoRate.builder()
                .video(video)
                .rate(type)
                .videoAppUser(user)
                .build();
        videoRateRepository.save(videoRate);
    }

    @Override
    public VideoRate getUserRateByVideo(Long videoId) {
        VideoAppUser user = authService.getAuthenticatedUser();
        return videoRateRepository.findByVideoIdAndVideoAppUser(videoId,user);
    }

    @Override
    public Map<RateType, Long> getCountOfRateByVideo(Long videoId) {
        List<VideoRate> rates = videoRateRepository.findAllByVideoId(videoId);
        Map<RateType, Long> result = rates
                .stream()
                .collect(Collectors.groupingBy(VideoRate::getRate, Collectors.counting()));
        EnumSet.allOf(RateType.class)
                .forEach(type -> result.computeIfAbsent(type, v -> 0L));
        return result;
    }

    @Override
    public void deleteVideoRate(Long videoId) {
        VideoAppUser user = authService.getAuthenticatedUser();
        videoRateRepository.deleteByVideoIdAndVideoAppUser(videoId, user);
    }
}
