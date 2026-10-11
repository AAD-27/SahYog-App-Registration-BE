package com.sahyog.msappreg.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitializeRequestDTO {
    @JsonAlias("appOrCaseNum")
    private String applicationNum;
}
