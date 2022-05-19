package com.moneyware.bank.documentservice.service.impl;

import com.moneyware.bank.documentservice.domain.DocumentType;
import com.moneyware.bank.documentservice.domain.DocumentUploadRequest;
import com.moneyware.bank.documentservice.domain.DocumentUploadResponse;
import com.moneyware.bank.documentservice.exception.DocumentException;
import com.moneyware.bank.documentservice.repository.DocumentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class DocumentServiceImplTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentServiceImpl documentService;

    @Test
    public void testSaveDocument() throws IOException {
        DocumentUploadRequest documentUploadRequest = getMockDocumentUploadRequest();
        Mockito.when(documentRepository.isDuplicateDocumentBy(Mockito.anyString(), Mockito.any(), Mockito.anyString())).thenReturn(false);
        Mockito.when(documentRepository.save(Mockito.any())).thenReturn(null);

        DocumentUploadResponse documentUploadResponse = documentService.saveDocument(documentUploadRequest);

        Assert.assertEquals(documentUploadResponse.getFileName(), "Address.pdf");
        Mockito.verify(documentRepository).save(Mockito.any());
        Mockito.verify(documentRepository).isDuplicateDocumentBy(Mockito.anyString(), Mockito.any(), Mockito.anyString());
    }

    @Test(expected = DocumentException.class)
    public void testSaveDocumentWithDuplicateDocument() throws IOException {
        DocumentUploadRequest documentUploadRequest = getMockDocumentUploadRequest();
        Mockito.when(documentRepository.isDuplicateDocumentBy(Mockito.anyString(), Mockito.any(), Mockito.anyString())).thenReturn(true);

        documentService.saveDocument(documentUploadRequest);

        Mockito.verify(documentRepository).isDuplicateDocumentBy(Mockito.anyString(), Mockito.any(), Mockito.anyString());
    }

    private DocumentUploadRequest getMockDocumentUploadRequest() {
        DocumentUploadRequest documentUploadRequest = Mockito.mock(DocumentUploadRequest.class);
        Mockito.when(documentUploadRequest.getCustomerId()).thenReturn("12345");
        Mockito.when(documentUploadRequest.getDocumentType()).thenReturn(DocumentType.ADDRESS);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("Address", "Address.pdf", "pdf", "India".getBytes());
        Mockito.when(documentUploadRequest.getFile()).thenReturn(mockMultipartFile);
        return documentUploadRequest;
    }
}
