package com.codecool.videoservice.util;

public enum FileType {

    MP4("video/mp4", "mp4"),
    JPG("image/jpeg", "jpg"),
    PNG("image/png", "png");

    private final String mimeType;
    private final String format;

    FileType(String mimeType, String format) {
        this.mimeType = mimeType;
        this.format = format;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFormat() {
        return format;
    }
}
