package com.sahyog.msappreg.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicantDetailsDTO {
    private String name;
    private Integer age;
    private String gender;
}
