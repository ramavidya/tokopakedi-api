package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.model.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e){
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.valueOf(e.getStatusCode().value()).getReasonPhrase())
                .message(e.getReason())
                .build();
        return ResponseEntity
                .status(e.getStatusCode())
                .body(response);
    }
}
