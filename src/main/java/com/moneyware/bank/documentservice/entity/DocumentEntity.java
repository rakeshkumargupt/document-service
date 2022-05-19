package com.moneyware.bank.documentservice.entity;

import com.moneyware.bank.documentservice.domain.DocumentType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "documents")
public class DocumentEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String documentId;

    private String customerId;
    private DocumentType type;
    private String status;

    private Timestamp createdDate;
    private String name;
    private String contentType;
    private Long size;

    @Lob
    private byte[] data;
}
