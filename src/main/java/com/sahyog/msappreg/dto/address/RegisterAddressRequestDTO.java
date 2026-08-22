package com.sahyog.msappreg.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAddressRequestDTO {
    private String pageId;
    private String applicationNum;
    private AddressDTO address;
}
