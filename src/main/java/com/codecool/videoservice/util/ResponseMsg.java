package com.codecool.videoservice.util;

public enum ResponseMsg {
    SUCCESS("{\"success\": true}"), UNSUCCESSFUL("{\"success\": false}"),
    CORRECT("{\"correct\": true}"), INCORRECT("{\"correct\": false}"),
    ENABLED("{\"enabled\": true}"), DISABLED("{\"enabled\": false}");

    private final String msg;

    ResponseMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
