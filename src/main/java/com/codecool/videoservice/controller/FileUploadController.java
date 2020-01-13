package com.codecool.videoservice.controller;

import com.codecool.videoservice.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static com.codecool.videoservice.util.FileType.MP4;

@RestController
@CrossOrigin
@RequestMapping
public class FileUploadController {

    @Value("${S3bucket.name}")
    private String s3bucket;

    @Autowired
    private FileStore fileStore;

    @PostMapping(value = "/upload/video",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String fileUpload(@RequestPart MultipartFile video, @RequestPart MultipartFile thumbnail,
            @RequestPart String title, @RequestPart String description) {

        if (video.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        if (!Objects.equals(video.getContentType(), MP4.getType())) {
            throw new IllegalStateException("Invalid type of file");
        }

        fileStore.save(s3bucket, video, thumbnail, title, description);
        return video.getOriginalFilename();
    }
}
