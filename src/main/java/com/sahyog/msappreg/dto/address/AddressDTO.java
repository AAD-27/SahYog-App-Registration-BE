package com.sahyog.msappreg.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private AddressDetailsDTO permanent;
    private AddressDetailsDTO temporary;
    private boolean sameAsPermanent;
}
