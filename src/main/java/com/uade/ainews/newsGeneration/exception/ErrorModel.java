package com.uade.ainews.newsGeneration.exception;

import java.util.Date;

public class ErrorModel {

    private Date timestamp;
    private String message;

    public ErrorModel(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

