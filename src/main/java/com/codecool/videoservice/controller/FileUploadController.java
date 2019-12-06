package com.codecool.videoservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping(value = "/upload")
public class FileUploadController {

    @Value("${file.path}")
    private String videoPath;

    @PostMapping(value = "/video",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String fileUpload(@RequestPart("file") MultipartFile file) throws IOException {
        File convertFile = new File(videoPath + file.getOriginalFilename());

        try (FileOutputStream fileOutputStream = new FileOutputStream(convertFile)) {
            fileOutputStream.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "File has uploaded successfully!";
    }
}
