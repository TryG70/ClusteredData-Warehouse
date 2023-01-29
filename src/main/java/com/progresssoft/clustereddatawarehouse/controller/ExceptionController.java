package com.progresssoft.clustereddatawarehouse.controller;

import com.progresssoft.clustereddatawarehouse.exception.DealAlreadyExistsException;
import com.progresssoft.clustereddatawarehouse.exception.FxDealNotFoundException;
import com.progresssoft.clustereddatawarehouse.response.ExceptionResponse;
import com.progresssoft.clustereddatawarehouse.exception.SameISOCodeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DealAlreadyExistsException.class)
    public ResponseEntity<Object> dealAlreadyExists(DealAlreadyExistsException exception) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), FORBIDDEN);
        return new ResponseEntity<>(exceptionResponse,FORBIDDEN);
    }

    @ExceptionHandler(SameISOCodeException.class)
    public ResponseEntity<Object> sameISOCodeException(SameISOCodeException exception) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), BAD_REQUEST);
        return new ResponseEntity<>(exceptionResponse,BAD_REQUEST);
    }

    @ExceptionHandler(FxDealNotFoundException.class)
    public ResponseEntity<Object> fxDealNotFoundException(FxDealNotFoundException exception) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(exceptionResponse,NOT_FOUND);
    }
}
