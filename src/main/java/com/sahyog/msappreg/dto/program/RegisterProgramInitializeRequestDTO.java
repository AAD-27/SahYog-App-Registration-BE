package com.sahyog.msappreg.dto.program;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class RegisterProgramInitializeRequestDTO {
    @JsonAlias("appOrCaseNum")
    private String applicationNum;
}
