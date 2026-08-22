package com.sahyog.msappreg.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RegisterReviewInitializeResponseDTO {
    private String applicationNum;
    private String pageId;
    private ApplicantDetailsDTO applicantDetails;
    private AddressDetailsDTO addressDetails;
    private List<String> programsSelected;
    private boolean found;
}
