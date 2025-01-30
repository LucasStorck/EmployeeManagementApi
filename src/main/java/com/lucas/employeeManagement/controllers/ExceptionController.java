package com.lucas.employeeManagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
    return new ResponseEntity<>("Só usuários 'SUPERUSER' podem acessar esse método", HttpStatus.FORBIDDEN);
  }
}
