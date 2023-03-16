package com.shablobank.app.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/*
* Cette classe sert à la gestion des exceptions
* qui se produiront lors de l'appel de l'une des methodes de notre API
* En fonction du type d'erreur rencontrée, on ecrira une methode destiné
* à afficher les messages d'erreur correspondants.
* Les messages d'erreurs sont personnalisée lors de la definition du model
* avec les decorateurs @<Contrainte>(message = "message erreur")
*  EX: @NotNull(message = "Ce champ est obligatoire")
* */

@RestControllerAdvice
public class ApplicationExceptionHandler {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationArgument(
    MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }
}
