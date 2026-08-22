package com.sahyog.msappreg.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterReviewSubmitResponseDTO {
    private String applicationNum;
    private String status;
}
