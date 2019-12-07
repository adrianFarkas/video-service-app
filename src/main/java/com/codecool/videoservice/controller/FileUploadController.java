package com.codecool.videoservice.controller;

import com.codecool.videoservice.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping
public class FileUploadController {

    @Value("${S3bucket.name}")
    private String s3bucket;

    @Autowired
    private FileStore fileStore;

    @PostMapping(value = "/upload/video",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String fileUpload(@RequestPart("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        fileStore.save(
                s3bucket,
                file.getOriginalFilename(),
                Optional.of(metadata),
                file.getInputStream()
                );



        return file.getOriginalFilename();
    }
}
