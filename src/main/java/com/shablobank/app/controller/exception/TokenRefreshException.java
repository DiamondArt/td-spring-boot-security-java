package com.shablobank.app.controller.exception;

public class TokenRefreshException extends Exception {
    private static final long serialVersionUID = 1L;
    private TokenRefreshException(String message) {
        super(message);
    }

    public static TokenRefreshException expired(String token) {
        return new TokenRefreshException(String.format("Failed for [%s] :: Refresh token was expired. Please make a new signin request", token));
    }

    public static TokenRefreshException notfound(String token) {
        return new TokenRefreshException(String.format("Failed for [%s] :: Refresh token is not in database", token));
    }
}
