package com.codecool.videoservice.controller;

import com.codecool.videoservice.filestore.FileStore;
import com.codecool.videoservice.model.user.VideoAppUser;
import com.codecool.videoservice.repository.UserRepository;
import com.codecool.videoservice.service.AuthService;
import com.codecool.videoservice.service.ThumbnailGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RestController
@CrossOrigin(allowCredentials = "true")
@RequestMapping
public class FileUploadController {

    @Autowired
    private FileStore fileStore;

    @Autowired
    private AuthService authService;

    @Autowired
    private ThumbnailGenerator thumbnailGenerator;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/upload/data")
    public HashMap<String, Object> getUploadData(@RequestParam String title) {
        VideoAppUser user = authService.getAuthenticatedUser();
        return fileStore.getUploadData(title, user);
    }

    @PostMapping("/create/thumbnails")
    public List<String> getImages(@RequestPart MultipartFile video) throws IOException {
        return thumbnailGenerator.createRandomThumbnails(4, video.getInputStream());
    }

    @PutMapping("/change/picture")
    public String saveProfilePicture(@RequestPart MultipartFile file) {
        VideoAppUser user = authService.getAuthenticatedUser();
        String link = fileStore.saveProfilePicture(file, user);
        userRepository.updateProfileImgById(user.getId(), link);
        return link;
    }
}
