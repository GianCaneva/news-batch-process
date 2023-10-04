package com.uade.ainews.newsGeneration.exception;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ErrorAppHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        printErrorMessage(ex);
        String errorMessageDescription = ex.getLocalizedMessage() == null ? ex.toString() : ex.getLocalizedMessage();
        ErrorModel errorMessageModel = new ErrorModel(new Date(), errorMessageDescription);
        return new ResponseEntity<>(errorMessageModel, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {
        printErrorMessage(ex);
        String errorMessageDescription = ex.getLocalizedMessage() == null ? ex.toString() : ex.getLocalizedMessage();
        ErrorModel errorMessage = new ErrorModel(new Date(), errorMessageDescription);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static void printErrorMessage(Exception ex) {
        System.out.println("===Error=== " + ex.getClass()
                + " Message: " + ex.getMessage()
                + " Cause: " + ex.getCause()
                + " Stacktrace:" + ex.getStackTrace());
    }
}