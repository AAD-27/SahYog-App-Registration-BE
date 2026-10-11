package com.sahyog.msappreg.dto.address;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAddressInitializeRequestDTO {
    @JsonAlias("appOrCaseNum")
    private String applicationNum;
}
