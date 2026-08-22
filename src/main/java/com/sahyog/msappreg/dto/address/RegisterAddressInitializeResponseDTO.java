package com.sahyog.msappreg.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAddressInitializeResponseDTO {
    private String applicationNum;
    private String pageId;
    private AddressDTO address;
    private boolean found;
}
