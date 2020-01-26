package com.codecool.videoservice.filestore;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.codecool.videoservice.model.user.VideoAppUser;
import static com.codecool.videoservice.util.FileType.JPG;
import static com.codecool.videoservice.util.FileType.MP4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service
public class FileStore {

    @Autowired
    private  AmazonS3 s3;

    @Value("${S3bucket.name}")
    private String s3bucket;

    @Value("${cloudfront.link}")
    private String cloudFront;

    public HashMap<String, Object> getUploadData(String title, VideoAppUser user) {
        String videoName = generateFileName(title, MP4.getFormat());
        String imageName = generateFileName(title, JPG.getFormat());

        HashMap<String, Object> res = generateUploadUrls(videoName, imageName, user);
        res.put("videoFileName", videoName);
        res.put("imageFileName", imageName);
        return res;
    }

    private String createS3FilePath(VideoAppUser user, String filename) {
        return String.format("%s/%s", user.getId(), filename);
    }

    public String createUserFilePath(VideoAppUser appUser, String filename) {
        return String.format("%s/%s/%s", cloudFront, appUser.getId(), filename);
    }

    private String generateFileName(String fromString, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");

        fromString = Normalizer.normalize(fromString, Normalizer.Form.NFD);
        fromString = fromString.replaceAll("[^\\p{ASCII}]", "");
        fromString = String.join("_", fromString.split("[^a-zA-Z0-9]+"));

        return String.format("%s_%s.%s", fromString, dateFormat.format(new Date()), type);
    }

    private URL getPreSignedUrl(HttpMethod method, String filename, VideoAppUser user) {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + 1000 * 10);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(s3bucket, createS3FilePath(user, filename))
                .withMethod(method)
                .withExpiration(expiration);

        return s3.generatePresignedUrl(generatePresignedUrlRequest);
    }

    private HashMap<String, Object> generateUploadUrls(String videoFilename, String imageFilename, VideoAppUser user) {
        HashMap<String, Object> urls = new HashMap<>();
        urls.put("videoUploadUrl",getPreSignedUrl(HttpMethod.PUT, videoFilename, user));
        urls.put("imageUploadUrl",getPreSignedUrl(HttpMethod.PUT, imageFilename, user));
        return urls;
    }
}
