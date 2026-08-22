package com.sahyog.msappreg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "AR_ADDRESS", uniqueConstraints = @UniqueConstraint(
        name = "UK_AR_ADDRESS_APPLICATION_TYPE",
        columnNames = {"APPLICATION_NO", "ADDRESS_TYPE"}))
@Data
public class RegisterAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Integer addressId;

    @Column(name = "APPLICATION_NO", nullable = false, length = 8)
    private String applicationNum;

    @Column(name = "ADDRESS_TYPE", nullable = false, length = 20)
    private String addressType;

    @Column(name = "ADDRESS_LINE1", nullable = false, length = 150)
    private String line1;

    @Column(name = "ADDRESS_LINE2", length = 150)
    private String line2;

    @Column(name = "CITY", nullable = false, length = 50)
    private String city;

    @Column(name = "STATE", nullable = false, length = 50)
    private String state;

    @Column(name = "COUNTRY", nullable = false, length = 50)
    private String country;

    @Column(name = "PINCODE", nullable = false, length = 10)
    private String pincode;
}
