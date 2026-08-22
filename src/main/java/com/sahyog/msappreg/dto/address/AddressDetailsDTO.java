package com.sahyog.msappreg.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetailsDTO {
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
