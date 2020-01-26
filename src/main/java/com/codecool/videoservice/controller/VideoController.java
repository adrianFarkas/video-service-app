package com.codecool.videoservice.controller;

import com.codecool.videoservice.dao.VideoDao;
import com.codecool.videoservice.filestore.FileStore;
import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.model.VideoDetails;
import com.codecool.videoservice.model.user.VideoAppUser;
import com.codecool.videoservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoDao videoDaoJpa;

    @Autowired
    private FileStore fileStore;

    @Autowired
    private AuthService authService;

    @GetMapping
    public List<VideoDetails> getVideos() {
        return videoDaoJpa.findAllVideos();
    }

    @GetMapping("/{id}")
    public VideoDetails getVideoById(@PathVariable("id") Long id) {
        return videoDaoJpa.findVideoById(id);
    }

    @PostMapping
    public Long saveVideo(@RequestBody HashMap<String, String> body) {
        VideoAppUser user = authService.getAuthenticatedUser();
        String videoLink = fileStore.createUserFilePath(user, body.get("videoFileName"));
        String thumbnailLink = fileStore.createUserFilePath(user, body.get("imageFileName"));
        Video video = videoDaoJpa.saveVideo(body.get("title"), body.get("description"), videoLink, thumbnailLink);
        return video.getId();
    }

    @PutMapping("/{id}")
    public Video updateVideo(@PathVariable("id") Long id, @RequestBody Video video) {
        return videoDaoJpa.updateById(id, video);
    }
}
