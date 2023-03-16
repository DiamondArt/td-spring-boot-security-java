package com.shablobank.app.controller.exception;

public class LdapClientException extends Exception {
    private String message;

    private LdapClientException(String message) {
        this.message = message;
    }

    public static LdapClientException create(String message) {
        return new LdapClientException(String.format("Ldap Server :: %s", message));
    }

    @Override
    public String getMessage() {
        return message;
    }
}
