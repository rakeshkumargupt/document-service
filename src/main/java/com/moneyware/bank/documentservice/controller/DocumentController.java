package com.moneyware.bank.documentservice.controller;

import com.moneyware.bank.documentservice.domain.DocumentUploadRequest;
import com.moneyware.bank.documentservice.exception.DocumentException;
import com.moneyware.bank.documentservice.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/documents")
    public ResponseEntity<Object> uploadDocument(@ModelAttribute DocumentUploadRequest documentUploadRequest) throws IOException {

        log.info("Started document upload for customer id: {}, document type: {}", documentUploadRequest.getCustomerId(), documentUploadRequest.getDocumentType());
        if (isInValidDocumentUploadRequest(documentUploadRequest)) {
            log.debug("Invalid parameters passed: {}", documentUploadRequest);
            throw new DocumentException("Provide all required parameters");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.saveDocument(documentUploadRequest));
    }

    private boolean isInValidDocumentUploadRequest(DocumentUploadRequest documentUploadRequest) {
        return Objects.isNull(documentUploadRequest) || Objects.isNull(documentUploadRequest.getFile()) || documentUploadRequest.getFile().isEmpty()
                || !StringUtils.hasText(documentUploadRequest.getCustomerId()) || Objects.isNull(documentUploadRequest.getDocumentType());
    }
}
