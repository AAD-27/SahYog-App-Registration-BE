package com.sahyog.msappreg.controller;

import com.sahyog.msappreg.dto.address.RegisterAddressInitializeRequestDTO;
import com.sahyog.msappreg.dto.address.RegisterAddressInitializeResponseDTO;
import com.sahyog.msappreg.dto.address.RegisterAddressRequestDTO;
import com.sahyog.msappreg.dto.address.RegisterAddressResponseDTO;
import com.sahyog.msappreg.service.RegisterAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register-address")
@RequiredArgsConstructor
public class RegisterAddressController {
    private final RegisterAddressService service;

    @PostMapping("/initialize")
    public ResponseEntity<RegisterAddressInitializeResponseDTO> initialize(
            @RequestBody RegisterAddressInitializeRequestDTO requestDTO) {
        return ResponseEntity.ok(service.initialize(requestDTO));
    }

    @PostMapping("/next")
    public ResponseEntity<RegisterAddressResponseDTO> next(@RequestBody RegisterAddressRequestDTO requestDTO) {
        return ResponseEntity.ok(service.processNext(requestDTO));
    }

    @PostMapping("/previous")
    public ResponseEntity<RegisterAddressResponseDTO> previous(@RequestBody RegisterAddressRequestDTO requestDTO) {
        return ResponseEntity.ok(service.processPrevious(requestDTO));
    }
}
