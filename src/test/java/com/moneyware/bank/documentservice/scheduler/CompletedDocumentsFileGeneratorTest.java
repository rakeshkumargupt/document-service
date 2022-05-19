package com.moneyware.bank.documentservice.scheduler;

import com.moneyware.bank.documentservice.common.FileUtil;
import com.moneyware.bank.documentservice.entity.DocumentEntity;
import com.moneyware.bank.documentservice.exception.DocumentException;
import com.moneyware.bank.documentservice.repository.DocumentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CompletedDocumentsFileGeneratorTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private FileUtil fileUtil;

    @InjectMocks
    private CompletedDocumentsFileGenerator completedDocumentsFileGenerator;

    @Test
    public void testFileGeneratorScheduler() throws IOException {
        Mockito.when(documentRepository.findByStatus(Mockito.anyString())).thenReturn(getMockDocumentEntities());
        Mockito.doNothing().when(fileUtil).createOriginalFile(Mockito.any());
        Mockito.doNothing().when(fileUtil).createIndexFile(Mockito.any());

        completedDocumentsFileGenerator.process();

        Mockito.verify(documentRepository).findByStatus(Mockito.anyString());
        Mockito.verify(fileUtil).createOriginalFile(Mockito.any());
        Mockito.verify(fileUtil).createIndexFile(Mockito.any());
    }

    @Test(expected = DocumentException.class)
    public void testFileGeneratorSchedulerWithException() throws IOException {
        Mockito.when(documentRepository.findByStatus(Mockito.anyString())).thenReturn(getMockDocumentEntities());
        Mockito.doThrow(new IOException("")).when(fileUtil).createOriginalFile(Mockito.any());

        completedDocumentsFileGenerator.process();

        Mockito.verify(documentRepository).findByStatus(Mockito.anyString());
        Mockito.verify(fileUtil).createOriginalFile(Mockito.any());
    }

    private List<DocumentEntity> getMockDocumentEntities() {
        DocumentEntity documentEntity = Mockito.mock(DocumentEntity.class);
        return Arrays.asList(documentEntity);
    }
}
