package com.moneyware.bank.documentservice.controller;


import com.moneyware.bank.documentservice.domain.DocumentType;
import com.moneyware.bank.documentservice.domain.DocumentUploadRequest;
import com.moneyware.bank.documentservice.domain.DocumentUploadResponse;
import com.moneyware.bank.documentservice.exception.DocumentException;
import com.moneyware.bank.documentservice.service.DocumentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    @Test
    public void testUploadDocument() throws IOException {
        DocumentUploadRequest documentUploadRequest = getMockDocumentUploadRequest();

        Mockito.when(documentService.saveDocument(Mockito.any())).thenReturn(new DocumentUploadResponse("KYC.pdf", "AJWS_SJKJKS_SKS"));
        ResponseEntity<Object> responseEntity = documentController.uploadDocument(documentUploadRequest);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        Assert.assertNotNull(responseEntity.getBody());

        Mockito.verify(documentService).saveDocument(Mockito.any());
    }

    @Test(expected = DocumentException.class)
    public void testUploadDocumentWithInvalidRequestData() throws IOException {
        DocumentUploadRequest documentUploadRequest = Mockito.mock(DocumentUploadRequest.class);
        documentController.uploadDocument(documentUploadRequest);
    }

    private DocumentUploadRequest getMockDocumentUploadRequest() {
        DocumentUploadRequest documentUploadRequest = Mockito.mock(DocumentUploadRequest.class);
        Mockito.when(documentUploadRequest.getCustomerId()).thenReturn("12345");
        Mockito.when(documentUploadRequest.getDocumentType()).thenReturn(DocumentType.ADDRESS);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("Address.pdf", "India".getBytes());
        Mockito.when(documentUploadRequest.getFile()).thenReturn(mockMultipartFile);
        return documentUploadRequest;
    }

}
