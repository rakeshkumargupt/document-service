package com.moneyware.bank.documentservice.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class DocumentUploadResponse implements Serializable {

    public static final long serialVersionUID = -1057731763525685005L;

    private String fileName;
    private String fileId;

    public DocumentUploadResponse(String fileName, String fileId) {
        this.fileName = fileName;
        this.fileId = fileId;
    }
}
