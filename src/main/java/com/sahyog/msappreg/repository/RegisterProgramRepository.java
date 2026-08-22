package com.sahyog.msappreg.repository;

import com.sahyog.msappreg.entity.RegisterProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisterProgramRepository extends JpaRepository<RegisterProgram, Integer> {
    List<RegisterProgram> findByApplicationNumOrderByProgramIdAsc(String applicationNum);
    void deleteByApplicationNum(String applicationNum);
}
