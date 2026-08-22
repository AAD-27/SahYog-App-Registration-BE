# Application Registration System - Implementation Summary

## Overview
End-to-end implementation of a two-API registration workflow with serial application number generation.

## APIs

### 1. Initialize API
**Endpoint:** `POST /api/register-application/initialize`

**Request:**
```json
{
  "applicationNum": "A9000000"
}
```

**Response (if found):**
```json
{
  "applicationNum": "A9000000",
  "pageId": "AR001",
  "firstName": "Ameya",
  "middleName": "ww",
  "lastName": "Dikshit",
  "mobileNumber": "1234567876",
  "emailAddress": "amydikshit@gmail.com",
  "applicationDate": "2026-07-25",
  "found": true
}
```

**Response (if not found):**
```json
{
  "applicationNum": null,
  "found": false
}
```

### 2. Next API
**Endpoint:** `POST /api/register-application/next`

**Request:**
```json
{
  "applicationNum": "",
  "pageId": "AR001",
  "firstName": "Ameya",
  "middleName": "ww",
  "lastName": "Dikshit",
  "mobileNumber": "1234567876",
  "emailAddress": "amydikshit@gmail.com",
  "applicationDate": "2026-07-25"
}
```

**Response:**
```json
{
  "applicationNum": "A9000000",
  "status": "Registered"
}
```

## Database Schema

### AR_APPLICATION Table
- id (BIGINT, PK, AUTO_INCREMENT)
- APPLICATION_NUMBER (VARCHAR(255), UNIQUE, NOT NULL)
- PAGE_ID (VARCHAR(255))
- FIRST_NAME (VARCHAR(255))
- MIDDLE_NAME (VARCHAR(255))
- LAST_NAME (VARCHAR(255))
- MOBILE_NUMBER (VARCHAR(255))
- EMAIL_ADDRESS (VARCHAR(255), NOT NULL)
- APPLICATION_DATE (VARCHAR(255))
- APPLICATION_STATUS (VARCHAR(255))
- APPLICANT_NAME (VARCHAR(255))
- APPLICANT_EMAIL (VARCHAR(255))
- PROGRAM_NAME (VARCHAR(255))

### AR_APPLICATION_SEQUENCE Table
- SEQUENCE_ID (VARCHAR(255), PK)
- NEXT_VALUE (BIGINT, NOT NULL)
- VERSION (BIGINT) - For optimistic locking

## Key Features

1. **Serial Number Generation**
   - Starts at A9000000
   - Increments sequentially: A9000001, A9000002, etc.
   - Thread-safe using optimistic locking (@Version annotation)

2. **Initialize API Logic**
   - Accepts only applicationNum parameter
   - Queries database for existing application
   - Returns found=true with full data if exists
   - Returns found=false if doesn't exist

3. **Next API Logic**
   - If applicationNum is empty, generates new number
   - If applicationNum is provided, uses it
   - Persists all application data to database
   - Returns generated/used applicationNum with status

4. **Persistence**
   - H2 in-memory database
   - JPA with Hibernate ORM
   - Automatic table creation

## File Structure

```
src/main/java/com/sahyog/msappreg/
├── controller/
│   └── RegisterApplicationController.java
├── service/
│   ├── RegisterApplicationService.java (interface)
│   └── impl/
│       └── RegisterApplicationServiceImpl.java
├── repository/
│   ├── RegisterApplicationRepository.java
│   └── ApplicationSequenceRepository.java
├── entity/
│   ├── Application.java
│   └── ApplicationSequence.java
├── dto/
│   ├── InitializeRequestDTO.java
│   ├── InitializeResponseDTO.java
│   ├── NextRequestDTO.java
│   └── NextResponseDTO.java
└── MsAppRegApplication.java
```

## Testing

Run the ApiTest.java to verify:
1. Initialize with non-existent application returns found=false
2. Next API generates A9000000 on first call
3. Initialize retrieves the generated application
4. Next API generates A9000001 on second call (sequential numbering)

## Configuration

**Port:** 8090
**Database:** H2 in-memory (jdbc:h2:mem:testdb)
**Framework:** Spring Boot 3.1.0
**ORM:** Hibernate 6.2.2
