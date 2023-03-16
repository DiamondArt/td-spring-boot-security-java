package com.shablobank.app.controller.exception;

import com.shablobank.app.models.User;
import org.springframework.lang.Nullable;


public class NotFoundException extends Exception {
    private String id;
    private Class aClass;

    private NotFoundException(@Nullable String id, Class aClass) {
        this.id = id;
        this.aClass = aClass;
    }

    public static NotFoundException create(@Nullable String id, Class aClass) {
        return new NotFoundException(id, aClass);
    }

    @Override
    public String getMessage() {
        if (aClass.getSimpleName().equalsIgnoreCase(User.class.getSimpleName())) {
            return String.format("%s {username} %s not found.", aClass.getSimpleName(), id);
        }
        if (id == null) {
            return String.format("%s not found.", aClass.getSimpleName());
        }
        return String.format("%s {id} %s not found.", aClass.getSimpleName(), id);
    }
}
