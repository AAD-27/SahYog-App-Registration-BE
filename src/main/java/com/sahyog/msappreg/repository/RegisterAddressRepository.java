package com.sahyog.msappreg.repository;

import com.sahyog.msappreg.entity.RegisterAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterAddressRepository extends JpaRepository<RegisterAddress, Integer> {
    Optional<RegisterAddress> findFirstByApplicationNumAndAddressTypeOrderByAddressIdAsc(
            String applicationNum, String addressType);
}
