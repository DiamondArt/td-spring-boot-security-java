package com.shablobank.app.controller.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class ApiException {
    private Date timestamp = new Date();
    private Integer status;
    private String error;
    private String path;
    private List<String> reasons = new ArrayList<String>();

    public ApiException(HttpStatus status, String path, List<String> reasons) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.path = path;
        this.reasons = reasons;
    }

    public ApiException(Integer statusCode, String path, String reasons) {
        this.status = statusCode;
        this.error = HttpStatus.valueOf(status).getReasonPhrase();
        this.path = path;
        this.reasons = Collections.singletonList(reasons);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }
}
