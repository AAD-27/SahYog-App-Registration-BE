package com.sahyog.msappreg.dto.person;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPersonInitializeRequestDTO {
    @JsonAlias("appOrCaseNum")
    private String applicationNum;
}
