package com.sahyog.msappreg.service;

import com.sahyog.msappreg.dto.review.RegisterReviewInitializeRequestDTO;
import com.sahyog.msappreg.dto.review.RegisterReviewInitializeResponseDTO;
import com.sahyog.msappreg.dto.review.RegisterReviewSubmitRequestDTO;
import com.sahyog.msappreg.dto.review.RegisterReviewSubmitResponseDTO;

public interface RegisterReviewService {
    RegisterReviewInitializeResponseDTO initialize(RegisterReviewInitializeRequestDTO requestDTO);
    RegisterReviewSubmitResponseDTO submit(RegisterReviewSubmitRequestDTO requestDTO);
}
