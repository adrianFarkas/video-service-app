package com.codecool.videoservice.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.model.user.VideoAppUser;
import com.codecool.videoservice.repository.VideoRepository;
import com.codecool.videoservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStore {

    @Autowired
    private  AmazonS3 s3;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private AuthService authService;

    @Value("${cloudfront.link}")
    private String cloudFront;

    public void save(String bucket, MultipartFile video,
                     MultipartFile thumbnail, String title, String description) {

        VideoAppUser appUser = authService.getAuthenticatedUser();

        try {
            s3.putObject(bucket, createS3FilePath(appUser, video),
                    video.getInputStream(), getFileMetadata(video));

            s3.putObject(bucket, createS3FilePath(appUser, thumbnail),
                    thumbnail.getInputStream(), getFileMetadata(thumbnail));

            storeInDatabase(appUser, title, description, video, thumbnail);

        } catch (AmazonServiceException | IOException e) {
            throw new IllegalArgumentException("Failed to store file to s3", e);
        }
    }

    private void storeInDatabase(VideoAppUser user, String title, String description,
                                 MultipartFile video, MultipartFile thumbnail) {

        Video newVideo = Video.builder()
                .videoAppUser(user)
                .title(title)
                .description(description)
                .videoLink(createUserFilePath(user, video))
                .thumbNailLink(createUserFilePath(user, thumbnail))
                .build();

        videoRepository.save(newVideo);
    }

    private ObjectMetadata getFileMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        return metadata;
    }

    private String createS3FilePath(VideoAppUser user, MultipartFile file) {
        return String.format("%s/%s", user.getId(), file.getOriginalFilename());
    }

    private String createUserFilePath(VideoAppUser appUser, MultipartFile file) {
        return String.format("%s/%s/%s", cloudFront, appUser.getId(), file.getOriginalFilename());
    }

}
