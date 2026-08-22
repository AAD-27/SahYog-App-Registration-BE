package com.sahyog.msappreg.controller;

import com.sahyog.msappreg.dto.review.RegisterReviewInitializeRequestDTO;
import com.sahyog.msappreg.dto.review.RegisterReviewInitializeResponseDTO;
import com.sahyog.msappreg.dto.review.RegisterReviewSubmitRequestDTO;
import com.sahyog.msappreg.dto.review.RegisterReviewSubmitResponseDTO;
import com.sahyog.msappreg.service.RegisterReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register-review")
@RequiredArgsConstructor
public class RegisterReviewController {
    private final RegisterReviewService service;

    @PostMapping("/initialize")
    public ResponseEntity<RegisterReviewInitializeResponseDTO> initialize(
            @RequestBody RegisterReviewInitializeRequestDTO requestDTO) {
        return ResponseEntity.ok(service.initialize(requestDTO));
    }

    @PostMapping("/submit")
    public ResponseEntity<RegisterReviewSubmitResponseDTO> submit(
            @RequestBody RegisterReviewSubmitRequestDTO requestDTO) {
        return ResponseEntity.ok(service.submit(requestDTO));
    }
}
