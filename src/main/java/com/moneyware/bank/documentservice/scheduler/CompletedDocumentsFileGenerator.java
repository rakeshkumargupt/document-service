package com.moneyware.bank.documentservice.scheduler;

import com.moneyware.bank.documentservice.common.AppConstants;
import com.moneyware.bank.documentservice.common.FileUtil;
import com.moneyware.bank.documentservice.entity.DocumentEntity;
import com.moneyware.bank.documentservice.exception.DocumentException;
import com.moneyware.bank.documentservice.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompletedDocumentsFileGenerator {

    private final DocumentRepository documentRepository;
    private final FileUtil fileUtil;

    @Scheduled(cron = "*/59 * * * * *")
    @Transactional
    public void process() {
        log.info("Scheduler started: {}", this.getClass().getSimpleName());
        List<DocumentEntity> documentEntities = documentRepository.findByStatus(AppConstants.COMPLETED);
        documentEntities.forEach(this::createFiles);
        log.info("Scheduler completed: {}, Total processed records: {}", this.getClass().getSimpleName(), documentEntities.size());
    }

    private void createFiles(DocumentEntity documentEntity) {
        try {
            fileUtil.createOriginalFile(documentEntity);
            fileUtil.createIndexFile(documentEntity);
            log.info("Successfully generated files for document: {}", documentEntity.getName());
        } catch (IOException e) {
            log.error("Error occurred while creating files: {}", e);
            throw new DocumentException("Error occurred in scheduler while creating files");
        }
    }
}
