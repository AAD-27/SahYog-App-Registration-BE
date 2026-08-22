package com.sahyog.msappreg.service;

import com.sahyog.msappreg.dto.program.RegisterProgramRequestDTO;
import com.sahyog.msappreg.dto.program.RegisterProgramResponseDTO;
import com.sahyog.msappreg.dto.program.RegisterProgramInitializeRequestDTO;
import com.sahyog.msappreg.dto.program.RegisterProgramInitializeResponseDTO;

public interface RegisterProgramService {
    RegisterProgramInitializeResponseDTO initialize(RegisterProgramInitializeRequestDTO requestDTO);
    RegisterProgramResponseDTO processNext(RegisterProgramRequestDTO requestDTO);
    RegisterProgramResponseDTO processPrevious(RegisterProgramRequestDTO requestDTO);
}
