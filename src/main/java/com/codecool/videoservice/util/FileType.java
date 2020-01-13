package com.codecool.videoservice.util;

public enum FileType {

    MP4("video/mp4"),
    JPG("image/jpeg"),
    PNG("image/png");

    private final String type;

    FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
