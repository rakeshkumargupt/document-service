package com.moneyware.bank.documentservice.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void testHandleDocumentException() {
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleDocumentException(new DocumentException("Error while processing document"));
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertNotNull(responseEntity);
    }

    @Test
    public void testHandleIOException() {
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleIOException(new IOException("IO Exception while processing document"));
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        Assert.assertNotNull(responseEntity);
    }
}
