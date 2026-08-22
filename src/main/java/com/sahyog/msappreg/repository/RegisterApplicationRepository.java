package com.sahyog.msappreg.repository;

import com.sahyog.msappreg.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterApplicationRepository extends JpaRepository<Application, String> {
    Optional<Application> findByApplicationNumber(String applicationNumber);
}
