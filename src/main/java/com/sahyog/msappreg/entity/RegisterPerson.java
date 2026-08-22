package com.sahyog.msappreg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "AR_PERSON", uniqueConstraints = @UniqueConstraint(
        name = "UK_AR_PERSON_APPLICATION_TYPE", columnNames = {"APPLICATION_NO", "PERSON_TYPE"}))
@Data
public class RegisterPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSON_ID")
    private Integer personId;

    @Column(name = "APPLICATION_NO", nullable = false, length = 8)
    private String applicationNum;

    @Column(name = "PERSON_TYPE", nullable = false, length = 30)
    private String personType;

    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName;

    @Column(name = "MIDDLE_NAME", length = 50)
    private String middleName;

    @Column(name = "LAST_NAME", nullable = false, length = 50)
    private String lastName;

    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private LocalDate dob;

    @Column(name = "AGE", nullable = false)
    private Integer age;

    @Column(name = "GENDER", nullable = false, length = 20)
    private String gender;

    @Column(name = "CASTE", length = 50)
    private String casteRace;

    @Column(name = "RELIGION", length = 50)
    private String religion;

    @Column(name = "MARITAL_STATUS", length = 30)
    private String maritalStatus;

    @Column(name = "AADHAR_NUMBER", nullable = false, length = 12)
    private String aadharNumber;

    @Column(name = "PAN_NUMBER", length = 10)
    private String panNumber;

    @Column(name = "PASSPORT_NUMBER", length = 20)
    private String passportNumber;
}
