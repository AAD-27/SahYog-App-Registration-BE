package com.sahyog.msappreg.dto.address;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAddressRequestDTO {
    private String pageId;
    @JsonAlias("appOrCaseNum")
    private String applicationNum;
    private AddressDTO address;
}
