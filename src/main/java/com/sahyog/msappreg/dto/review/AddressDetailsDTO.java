package com.sahyog.msappreg.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDetailsDTO {
    private ReviewAddressDTO permanent;
    private ReviewAddressDTO temporary;
}
