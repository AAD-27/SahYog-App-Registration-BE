package com.sahyog.msappreg.repository;

import com.sahyog.msappreg.entity.ApplicationSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationSequenceRepository extends JpaRepository<ApplicationSequence, String> {
}
