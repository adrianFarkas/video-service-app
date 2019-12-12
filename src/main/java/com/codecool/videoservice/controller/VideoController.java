package com.codecool.videoservice.controller;

import com.codecool.videoservice.dao.VideoDao;
import com.codecool.videoservice.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoDao videoDaoJpa;

    @GetMapping
    public List<Video> getVideos() {
        return videoDaoJpa.findAllVideos();
    }

    @PutMapping("/{id}")
    public Video updateVideo(@PathVariable("id") Long id, @RequestBody Video video) {
        return videoDaoJpa.updateById(id, video);
    }
}
