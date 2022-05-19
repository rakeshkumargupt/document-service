package com.moneyware.bank.documentservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DocumentException.class)
    public ResponseEntity<Object> handleDocumentException(DocumentException exc) {
        log.error("Error occurred while processing document: {}", exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(prepareResponseError(exc.getMessage()));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException(IOException exc) {
        log.error("IO error occurred while processing document: {}", exc.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(prepareResponseError(String.format("Could not upload the file: %s", exc.getMessage())));
    }

    private Map<String, String> prepareResponseError(String exc) {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("error", exc);
        return responseMap;
    }
}
