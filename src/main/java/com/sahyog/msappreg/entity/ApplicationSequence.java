package com.sahyog.msappreg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AR_APPLICATION_SEQUENCE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSequence {

    @Id
    @Column(name = "SEQUENCE_ID")
    private String sequenceId;

    @Column(name = "NEXT_VALUE", nullable = false)
    private Long nextValue;

    @Version
    @Column(name = "VERSION")
    private Long version;
}
