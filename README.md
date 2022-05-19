# Moneyware Ltd. Digital Document Indexing & Archiving Service

[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

## Purpose

This service using [Spring Boot](http://projects.spring.io/spring-boot/), [JPA](), [Hibernate](), [Security]() and [PostgreSQL](https://www.postgresql.org/download/) to fulfill below requirements.

1. *Upload of scanned documents and classified as Document Type (Personal, Address, ..). 
   Below details need to be kept as well: [File, File size, Filename, Unique ID, Timestamp, customer ID, Document Type, Status]*
2. *The file details are saved with a status ‘Completed’ and we need to allow reading of the
   document database every day, each one hour based on this status.*
3. *Once the job reads the db record, it should generate the file on a specific folder. That folder
   path can change over time.*
4. *To be able to identify each document, an index file is needed. For each one document
   generation, an index file with a unique name (DS+Timestamp) will be generated equally and
   have below format.*
5. *This file shall contain a generic comment, Timestamp, customer_id, document_type, filename.*
   
   - COMMENT:  ONDEMAND GENERIC INDEX FILE GENERATED
   - GROUP_FIELD_NAME:  Timestamp
   - GROUP_FIELD_VALUE:  19/5/2022
   - GROUP_FIELD_NAME:  CUSTOMER_ID
   - GROUP_FIELD_VALUE:  12345
   - GROUP_FIELD_NAME:  DOCUMENT_TYPE
   - GROUP_FIELD_VALUE:  KYC
   - GROUP_FILE_NAME:  DS1652952757340.png`

## Feature & functionality supported 

1. We have exposed POST API to upload documents which internally saves in database.
2. Scheduler job is configured to run every hour.
3. This will read the data from the DB, Generate the original & index file for documents for completed status.
4. Once job is completed, It will generate files under specified directory.
5. Basic Spring Security auth is used to consume the REST Endpoint.
6. Configured proper logging using SL4J & Logback to trace request. 
7. Complete unit test with `Code coverage > 95%`.


## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [PostgreSQL 42.2](https://www.postgresql.org/download/)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.moneyware.bank.documentservice.DocumentServiceApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Document Service API's

Once application started, Below is the API specification for same.

####  POST - /document-service/documents
#### Headers :
1. `Authorization : Basic dXNlcjoxMjM0NQ==`
2. `All required default POST method parameters`

* Or, Alternatively in postman we can select `Basic Auth with userName=user & password=12345`

#### Body as form-data

1. `customerId :201924` *- Any valid non empty string*
2. `documentType: KYC`  *- Classified valid document type such as - PERSONAL/KYC/PAN/ADDRESS*
3. `file: Multipart file` *- Any valid file*

#### Success Response
```json
{
   "fileName": "Resume_AnkurChauhan_Nagarro.docx",
   "fileId": "8964052f-3a81-401d-a63b-e13fb555f7a4"
}
```

#### Error Response(If any required parameter not passed)
```json
{
   "error": "Provide all required parameters"
}
```

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.