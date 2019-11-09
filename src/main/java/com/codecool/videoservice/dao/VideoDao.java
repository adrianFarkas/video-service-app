package com.codecool.videoservice.dao;

import com.codecool.videoservice.model.Video;

import java.util.List;

public interface VideoDao {

    List<Video> findAllVideos();

}
