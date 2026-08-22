package com.sahyog.msappreg.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewAddressDTO {
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
