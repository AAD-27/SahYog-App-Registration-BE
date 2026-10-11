package com.sahyog.msappreg.dto.review;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class RegisterReviewInitializeRequestDTO {
    @JsonAlias("appOrCaseNum")
    private String applicationNum;
}
