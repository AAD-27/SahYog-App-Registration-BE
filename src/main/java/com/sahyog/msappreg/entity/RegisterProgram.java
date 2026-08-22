package com.sahyog.msappreg.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "AR_PROGRAM", uniqueConstraints = @UniqueConstraint(
        name = "UK_AR_PROGRAM_APPLICATION_CODE", columnNames = {"APPLICATION_NO", "PROGRAM_CODE"}))
@Data
public class RegisterProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGRAM_ID")
    private Integer programId;

    @Column(name = "APPLICATION_NO", nullable = false, length = 8)
    private String applicationNum;

    @Column(name = "PROGRAM_CODE", nullable = false, length = 20)
    private String programCode;

    @Column(name = "PROGRAM_NAME", nullable = false, length = 100)
    private String programName;
}
