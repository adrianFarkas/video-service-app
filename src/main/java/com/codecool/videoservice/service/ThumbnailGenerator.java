package com.codecool.videoservice.service;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ThumbnailGenerator {

    @Autowired
    private Random random;

    public List<String> createRandomThumbnails(int number, InputStream inputStream) throws IOException {
        List<String> images = new ArrayList<>();
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(inputStream);
        Java2DFrameConverter converter = new Java2DFrameConverter();

        frameGrabber.start();

        for (int i = 0; i < number; i++) {
            Frame frame = grabRandomImage(frameGrabber);
            images.add(bufferedToDataUrl(converter.convert(frame)));
        }

        frameGrabber.stop();
        return images;
    }

    private Frame grabRandomImage(FFmpegFrameGrabber grabber) throws FrameGrabber.Exception {
        int frameLength = grabber.getLengthInFrames();
        grabber.setFrameNumber(random.nextInt(frameLength));
        return grabber.grabImage();
    }

    private String bufferedToDataUrl(BufferedImage image) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", os);
        byte[] byteArray = os.toByteArray();
        String base64 = StringUtils.newStringUtf8(Base64.encodeBase64(byteArray, false));
        return String.format("data:%s;base64,%s", "image/jpeg", base64);
    }
}