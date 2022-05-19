package com.moneyware.bank.documentservice.service.impl;

import com.moneyware.bank.documentservice.common.AppConstants;
import com.moneyware.bank.documentservice.domain.DocumentUploadRequest;
import com.moneyware.bank.documentservice.domain.DocumentUploadResponse;
import com.moneyware.bank.documentservice.entity.DocumentEntity;
import com.moneyware.bank.documentservice.exception.DocumentException;
import com.moneyware.bank.documentservice.repository.DocumentRepository;
import com.moneyware.bank.documentservice.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public DocumentUploadResponse saveDocument(DocumentUploadRequest documentUploadRequest) throws DocumentException, IOException {

        String fileName = StringUtils.cleanPath(documentUploadRequest.getFile().getOriginalFilename());
        if (documentRepository.isDuplicateDocumentBy(documentUploadRequest.getCustomerId(), documentUploadRequest.getDocumentType(), fileName)) {
            log.debug("Can't upload duplicate document for customer id: {}, document type: {}", documentUploadRequest.getCustomerId(), documentUploadRequest.getDocumentType());
            throw new DocumentException("Can't upload duplicate document for same customer");
        }

        DocumentEntity documentEntity = prepareDocumentEntity(documentUploadRequest);
        documentRepository.save(documentEntity);

        log.info("Completed document upload for customer id: {}, unique document id: {}", documentUploadRequest.getCustomerId(), documentEntity.getDocumentId());
        return new DocumentUploadResponse(documentEntity.getName(), documentEntity.getDocumentId());
    }

    private DocumentEntity prepareDocumentEntity(DocumentUploadRequest documentUploadRequest) throws IOException {
        DocumentEntity documentEntity = new DocumentEntity();

        MultipartFile documentFile = documentUploadRequest.getFile();
        documentEntity.setName(StringUtils.cleanPath(documentFile.getOriginalFilename()));
        documentEntity.setContentType(FilenameUtils.getExtension(documentFile.getOriginalFilename()));
        documentEntity.setData(documentFile.getBytes());
        documentEntity.setSize(documentFile.getSize());
        documentEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        documentEntity.setCustomerId(documentUploadRequest.getCustomerId());
        documentEntity.setType(documentUploadRequest.getDocumentType());
        documentEntity.setStatus(AppConstants.COMPLETED);

        return documentEntity;
    }
}
