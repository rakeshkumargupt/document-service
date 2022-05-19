package com.moneyware.bank.documentservice.service;

import com.moneyware.bank.documentservice.domain.DocumentUploadRequest;
import com.moneyware.bank.documentservice.domain.DocumentUploadResponse;
import com.moneyware.bank.documentservice.exception.DocumentException;

import java.io.IOException;

public interface DocumentService {
    DocumentUploadResponse saveDocument(DocumentUploadRequest documentUploadRequest) throws DocumentException, IOException;
}
