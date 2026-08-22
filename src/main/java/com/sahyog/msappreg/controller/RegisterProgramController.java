package com.sahyog.msappreg.controller;

import com.sahyog.msappreg.dto.program.RegisterProgramRequestDTO;
import com.sahyog.msappreg.dto.program.RegisterProgramResponseDTO;
import com.sahyog.msappreg.dto.program.RegisterProgramInitializeRequestDTO;
import com.sahyog.msappreg.dto.program.RegisterProgramInitializeResponseDTO;
import com.sahyog.msappreg.service.RegisterProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register-program")
@RequiredArgsConstructor
public class RegisterProgramController {
    private final RegisterProgramService service;

    @PostMapping("/initialize")
    public ResponseEntity<RegisterProgramInitializeResponseDTO> initialize(
            @RequestBody RegisterProgramInitializeRequestDTO requestDTO) {
        return ResponseEntity.ok(service.initialize(requestDTO));
    }

    @PostMapping("/next")
    public ResponseEntity<RegisterProgramResponseDTO> next(@RequestBody RegisterProgramRequestDTO requestDTO) {
        return ResponseEntity.ok(service.processNext(requestDTO));
    }

    @PostMapping("/previous")
    public ResponseEntity<RegisterProgramResponseDTO> previous(@RequestBody RegisterProgramRequestDTO requestDTO) {
        return ResponseEntity.ok(service.processPrevious(requestDTO));
    }
}
