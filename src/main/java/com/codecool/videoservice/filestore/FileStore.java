package com.codecool.videoservice.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.codecool.videoservice.model.Video;
import com.codecool.videoservice.model.user.VideoAppUser;
import com.codecool.videoservice.repository.VideoRepository;
import com.codecool.videoservice.service.AuthService;
import static com.codecool.videoservice.util.FileType.JPG;
import static com.codecool.videoservice.util.FileType.MP4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        String videoName = generateFileName(title, MP4.getFormat());
        String thumbName = generateFileName(title, JPG.getFormat());

        try {
            s3.putObject(
                    bucket,
                    createS3FilePath(appUser, videoName),
                    video.getInputStream(),
                    getFileMetadata(video)
            );

            s3.putObject(
                    bucket,
                    createS3FilePath(appUser, thumbName),
                    thumbnail.getInputStream(),
                    getFileMetadata(thumbnail)
            );

            storeInDatabase(appUser, title, description, videoName, thumbName);

        } catch (AmazonServiceException | IOException e) {
            throw new IllegalArgumentException("Failed to store file to s3", e);
        }
    }

    private void storeInDatabase(VideoAppUser user, String title, String description,
                                 String videoName, String thumbnailName) {

        Video newVideo = Video.builder()
                .videoAppUser(user)
                .title(title)
                .description(description)
                .videoLink(createUserFilePath(user, videoName))
                .thumbNailLink(createUserFilePath(user, thumbnailName))
                .build();

        videoRepository.save(newVideo);
    }

    private ObjectMetadata getFileMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        return metadata;
    }

    private String createS3FilePath(VideoAppUser user, String filename) {
        return String.format("%s/%s", user.getId(), filename);
    }

    private String createUserFilePath(VideoAppUser appUser, String filename) {
        return String.format("%s/%s/%s", cloudFront, appUser.getId(), filename);
    }

    private String generateFileName(String fromString, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");

        fromString = Normalizer.normalize(fromString, Normalizer.Form.NFD);
        fromString = fromString.replaceAll("[^\\p{ASCII}]", "");
        fromString = String.join("_", fromString.split("[^a-zA-Z0-9]+"));

        return String.format("%s_%s.%s", fromString, dateFormat.format(new Date()), type);
    }
}
