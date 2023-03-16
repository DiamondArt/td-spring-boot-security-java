package com.shablobank.app.controller.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;


public class ContentNotAllowedException extends Exception {
    List<ObjectError> errors;

    public static ContentNotAllowedException create(BindingResult bindingResult) {
        return new ContentNotAllowedException(bindingResult.getAllErrors());
    }

    private ContentNotAllowedException(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }
}
