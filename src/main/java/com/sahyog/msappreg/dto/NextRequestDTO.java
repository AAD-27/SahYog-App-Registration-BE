package com.sahyog.msappreg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NextRequestDTO {
    private String applicationNum;
    private String pageId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNumber;
    private String emailAddress;
    private String applicationDate;
}
