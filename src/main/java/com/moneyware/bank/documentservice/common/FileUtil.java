package com.moneyware.bank.documentservice.common;

import com.moneyware.bank.documentservice.entity.DocumentEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;

@Component
public class FileUtil {

    @Value("${file.export.dir}")
    private String fileDirectory;

    public void createOriginalFile(DocumentEntity documentEntity) throws IOException {
        Path path = Paths.get(fileDirectory + AppConstants.FORWARD_SLASH + documentEntity.getName());
        Files.write(path, documentEntity.getData());
    }

    public void createIndexFile(DocumentEntity documentEntity) throws IOException {
        String fileName = AppConstants.DOCUMENT_PREFIX + documentEntity.getCreatedDate().getTime();
        Path path = Paths.get(fileDirectory + AppConstants.FORWARD_SLASH + fileName + AppConstants.INDEX_EXT);
        String content = getIndexFileContent(documentEntity);
        Files.write(path, content.getBytes());
    }

    private String getIndexFileContent(DocumentEntity documentEntity) {
        StringBuilder content = new StringBuilder();
        prepareKey(content, AppConstants.COMMENT_TEXT, AppConstants.COMMENT_VALUE);

        prepareKey(content, AppConstants.GROUP_FIELD_NAME, AppConstants.TIMESTAMP);
        prepareKey(content, AppConstants.GROUP_FIELD_VALUE, prepareTimestamp(documentEntity.getCreatedDate()));

        prepareKey(content, AppConstants.GROUP_FIELD_NAME, AppConstants.CUSTOMER_ID);
        prepareKey(content, AppConstants.GROUP_FIELD_VALUE, documentEntity.getCustomerId());

        prepareKey(content, AppConstants.GROUP_FIELD_NAME, AppConstants.DOCUMENT_TYPE);
        prepareKey(content, AppConstants.GROUP_FIELD_VALUE, documentEntity.getType().name());

        prepareKey(content, AppConstants.GROUP_FILE_NAME, AppConstants.DOCUMENT_PREFIX + documentEntity.getCreatedDate().getTime() + AppConstants.DOT + documentEntity.getContentType());

        return content.toString();
    }

    private String prepareTimestamp(Timestamp createdDate) {
        LocalDate currDate = createdDate.toLocalDateTime().toLocalDate();
        return currDate.getDayOfMonth() + AppConstants.FORWARD_SLASH + currDate.getMonthValue() + AppConstants.FORWARD_SLASH + currDate.getYear();
    }

    private void prepareKey(StringBuilder content, String key, String value) {
        content.append(key);
        content.append(AppConstants.COLON);
        content.append(AppConstants.SPACE);
        content.append(value);
        content.append(AppConstants.NEW_LINE);
    }
}
