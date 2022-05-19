package com.moneyware.bank.documentservice.repository;

import com.moneyware.bank.documentservice.domain.DocumentType;
import com.moneyware.bank.documentservice.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, String> {

    List<DocumentEntity> findByStatus(String status);

    @Query("SELECT count(de) > 0 FROM DocumentEntity de WHERE de.customerId = :customerId AND de.type =:type AND de.name = :name")
    boolean isDuplicateDocumentBy(@Param("customerId") String customerId, @Param("type") DocumentType type, @Param("name") String name);
}
