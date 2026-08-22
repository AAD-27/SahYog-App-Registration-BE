package com.sahyog.msappreg.repository;

import com.sahyog.msappreg.entity.RegisterPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterPersonRepository extends JpaRepository<RegisterPerson, Integer> {
    Optional<RegisterPerson> findFirstByApplicationNumAndPersonTypeOrderByPersonIdAsc(
            String applicationNum, String personType);
}
