package com.sahyog.msappreg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "AR_APPLICATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @Column(name = "APPLICATION_NO", nullable = false, length = 8)
    private String applicationNumber;

    @Column(name = "PAGE_ID")
    private String pageId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "EMAIL")
    private String emailAddress;

    @Column(name = "APPLICATION_DATE")
    private String applicationDate;

    @Column(name = "APPLICATION_STATUS")
    private String applicationStatus;

    @Column(name = "PROGRAM_NAME")
    private String programName;

    @Column(name = "APPLICANT_NAME")
    private String applicantName;

    @Column(name = "APPLICANT_EMAIL")
    private String applicantEmail;

    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
