package com.sahyog.msappreg.controller;

import com.sahyog.msappreg.dto.InitializeRequestDTO;
import com.sahyog.msappreg.dto.InitializeResponseDTO;
import com.sahyog.msappreg.dto.NextRequestDTO;
import com.sahyog.msappreg.dto.NextResponseDTO;
import com.sahyog.msappreg.service.RegisterApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/register-application")
@RequiredArgsConstructor
public class RegisterApplicationController {
    
    private static final Logger logger = LoggerFactory.getLogger(RegisterApplicationController.class);

    private final RegisterApplicationService service;

    @PostMapping("/initialize")
    public ResponseEntity<InitializeResponseDTO> initialize(@RequestBody InitializeRequestDTO requestDTO) {
        logger.info("Initialize request received: {}", requestDTO.getApplicationNum());
        InitializeResponseDTO responseDTO = service.initializeApplication(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/next")
    public ResponseEntity<NextResponseDTO> next(@RequestBody NextRequestDTO requestDTO) {
        logger.info("Next request received: {}", requestDTO.getApplicationNum());
        NextResponseDTO responseDTO = service.processNext(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
