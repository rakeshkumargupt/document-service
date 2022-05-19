package com.moneyware.bank.documentservice.exception;

import org.junit.Assert;
import org.junit.Test;

public class DocumentExceptionTest {

    @Test
    public void testDocumentException() {
        DocumentException documentException = new DocumentException("Document processing error");
        Assert.assertEquals(documentException.getMessage(), "Document processing error");

        DocumentException documentExceptionWithParameter = new DocumentException("Document processing error", new Throwable("Error"));
        Assert.assertEquals(documentExceptionWithParameter.getMessage(), "Document processing error");
    }

}
