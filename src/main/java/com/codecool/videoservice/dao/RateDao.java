package com.codecool.videoservice.dao;

import com.codecool.videoservice.model.RateType;
import com.codecool.videoservice.model.VideoRate;

import java.util.List;
import java.util.Map;

public interface RateDao {

    List<VideoRate> getAllRateByVideo(Long videoId);

    void saveVideoRate(Long videoId, RateType type);

    VideoRate getUserRateByVideo(Long id);

    Map<RateType, Long> getCountOfRateByVideo(Long id);

    void deleteVideoRate(Long id);
}
