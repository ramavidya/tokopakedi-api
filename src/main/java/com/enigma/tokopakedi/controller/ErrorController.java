package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.model.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
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
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e){
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException e){
        HttpStatus httpStatus = null;
        String status = null;
        String message = e.getMostSpecificCause().getMessage();
        String reason = null;
        if (message.contains("foreign key constraint")){
            httpStatus = HttpStatus.BAD_REQUEST;
            status = HttpStatus.BAD_REQUEST.getReasonPhrase();
            reason = "cannot remove data because there is a reference from other table";
        } else if (message.contains("unique constraint") || message.contains("Duplicate entry")) {
            httpStatus = HttpStatus.CONFLICT;
            status = HttpStatus.CONFLICT.getReasonPhrase();
            reason = "duplicate data";
        }else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            reason = " Internal server error";
        }

        WebResponse<String> response = WebResponse.<String>builder()
                .status(status)
                .message(reason)
                .build();
        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }
}
