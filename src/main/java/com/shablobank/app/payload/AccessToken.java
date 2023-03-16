package com.shablobank.app.payload;

import javax.persistence.Embeddable;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

@Embeddable
public class AccessToken {
    private HashMap device = new HashMap<>();

    private String id;

    private String type = "Bearer";

    private Date expiresAt;

    private Date issuedAt;

    public AccessToken() {
    }

    public AccessToken(String id, Date expiresAt, Date issuedAt, HttpServletRequest request) {
        HashMap map = new HashMap<>();
        map.put("userAgent", request.getHeader("user-agent"));
        map.put("remoteAddr", request.getRemoteAddr());
        this.device = map;

        this.id = id;
        this.expiresAt = expiresAt;
        this.issuedAt = issuedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public HashMap getDevice() {
        return device;
    }

    public void setDevice(HashMap device) {
        this.device = device;
    }
}