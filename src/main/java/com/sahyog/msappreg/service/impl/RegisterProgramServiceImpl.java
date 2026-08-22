package com.sahyog.msappreg.service.impl;

import com.sahyog.msappreg.dto.program.RegisterProgramRequestDTO;
import com.sahyog.msappreg.dto.program.RegisterProgramResponseDTO;
import com.sahyog.msappreg.dto.program.RegisterProgramInitializeRequestDTO;
import com.sahyog.msappreg.dto.program.RegisterProgramInitializeResponseDTO;
import com.sahyog.msappreg.entity.RegisterProgram;
import com.sahyog.msappreg.repository.RegisterApplicationRepository;
import com.sahyog.msappreg.repository.RegisterProgramRepository;
import com.sahyog.msappreg.service.RegisterProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegisterProgramServiceImpl implements RegisterProgramService {
    private final RegisterProgramRepository programRepository;
    private final RegisterApplicationRepository applicationRepository;

    @Override
    @Transactional(readOnly = true)
    public RegisterProgramInitializeResponseDTO initialize(RegisterProgramInitializeRequestDTO requestDTO) {
        if (requestDTO.getApplicationNum() == null || requestDTO.getApplicationNum().isBlank()) {
            return new RegisterProgramInitializeResponseDTO(null, "AR004", List.of(), false);
        }
        List<String> programs = programRepository.findByApplicationNumOrderByProgramIdAsc(requestDTO.getApplicationNum())
                .stream().map(program -> program.getProgramCode()).toList();
        return new RegisterProgramInitializeResponseDTO(requestDTO.getApplicationNum(), "AR004", programs,
                !programs.isEmpty());
    }

    @Override
    @Transactional
    public RegisterProgramResponseDTO processNext(RegisterProgramRequestDTO requestDTO) {
        savePrograms(requestDTO);
        return new RegisterProgramResponseDTO(requestDTO.getApplicationNum(), "Saved");
    }

    @Override
    @Transactional
    public RegisterProgramResponseDTO processPrevious(RegisterProgramRequestDTO requestDTO) {
        savePrograms(requestDTO);
        return new RegisterProgramResponseDTO(requestDTO.getApplicationNum(), "Saved");
    }

    private void savePrograms(RegisterProgramRequestDTO requestDTO) {
        if (requestDTO.getApplicationNum() == null || requestDTO.getApplicationNum().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "applicationNum is required");
        }
        if (requestDTO.getPrograms() == null || requestDTO.getPrograms().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "at least one program is required");
        }
        if (!applicationRepository.existsById(requestDTO.getApplicationNum())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found: " + requestDTO.getApplicationNum());
        }

        LinkedHashSet<String> uniquePrograms = new LinkedHashSet<>();
        for (String code : requestDTO.getPrograms()) {
            if (code == null || code.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "program codes cannot be blank");
            }
            uniquePrograms.add(code.trim());
        }

        if (uniquePrograms.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "program codes cannot be blank");
        }

        Map<String, RegisterProgram> existingPrograms = new LinkedHashMap<>();
        programRepository.findByApplicationNumOrderByProgramIdAsc(requestDTO.getApplicationNum())
                .forEach(program -> existingPrograms.put(program.getProgramCode(), program));

        // Remove only programs the user has deselected.
        programRepository.deleteAll(existingPrograms.values().stream()
                .filter(program -> !uniquePrograms.contains(program.getProgramCode()))
                .toList());

        // Reuse existing rows; create a row only for a newly selected program.
        for (String code : uniquePrograms) {
            RegisterProgram program = existingPrograms.getOrDefault(code, new RegisterProgram());
            program.setApplicationNum(requestDTO.getApplicationNum());
            program.setProgramCode(code);
            program.setProgramName(code);
            programRepository.save(program);
        }
    }
}
