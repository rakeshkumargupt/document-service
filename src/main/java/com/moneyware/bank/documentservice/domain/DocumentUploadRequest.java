package com.moneyware.bank.documentservice.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class DocumentUploadRequest implements Serializable {

    public static final long serialVersionUID = 4541105350136400533L;

    private MultipartFile file;
    private String customerId;
    private DocumentType documentType;
}
