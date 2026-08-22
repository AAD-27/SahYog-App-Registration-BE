package com.sahyog.msappreg.controller;

import com.sahyog.msappreg.dto.person.RegisterPersonInitializeRequestDTO;
import com.sahyog.msappreg.dto.person.RegisterPersonInitializeResponseDTO;
import com.sahyog.msappreg.dto.person.RegisterPersonRequestDTO;
import com.sahyog.msappreg.dto.person.RegisterPersonResponseDTO;
import com.sahyog.msappreg.service.RegisterPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register-person")
@RequiredArgsConstructor
public class RegisterPersonController {
    private final RegisterPersonService service;

    @PostMapping("/initialize")
    public ResponseEntity<RegisterPersonInitializeResponseDTO> initialize(
            @RequestBody RegisterPersonInitializeRequestDTO requestDTO) {
        return ResponseEntity.ok(service.initialize(requestDTO));
    }

    @PostMapping("/next")
    public ResponseEntity<RegisterPersonResponseDTO> next(@RequestBody RegisterPersonRequestDTO requestDTO) {
        return ResponseEntity.ok(service.processNext(requestDTO));
    }

    @PostMapping("/previous")
    public ResponseEntity<RegisterPersonResponseDTO> previous(@RequestBody RegisterPersonRequestDTO requestDTO) {
        return ResponseEntity.ok(service.processPrevious(requestDTO));
    }
}
