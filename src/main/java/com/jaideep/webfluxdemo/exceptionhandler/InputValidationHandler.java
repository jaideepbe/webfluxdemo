package com.jaideep.webfluxdemo.exceptionhandler;

import com.jaideep.webfluxdemo.dto.InputValidationFailedResponse;
import com.jaideep.webfluxdemo.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputValidationFailedResponse> handleInputValidationException(InputValidationException ex) {
        InputValidationFailedResponse response = new InputValidationFailedResponse();
        response.setInput(ex.getInput());
        response.setErrorCode(ex.getErrorCode());
        response.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
