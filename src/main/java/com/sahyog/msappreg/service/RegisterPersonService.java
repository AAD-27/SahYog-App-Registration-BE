package com.sahyog.msappreg.service;

import com.sahyog.msappreg.dto.person.RegisterPersonInitializeRequestDTO;
import com.sahyog.msappreg.dto.person.RegisterPersonInitializeResponseDTO;
import com.sahyog.msappreg.dto.person.RegisterPersonRequestDTO;
import com.sahyog.msappreg.dto.person.RegisterPersonResponseDTO;

public interface RegisterPersonService {
    RegisterPersonInitializeResponseDTO initialize(RegisterPersonInitializeRequestDTO requestDTO);
    RegisterPersonResponseDTO processNext(RegisterPersonRequestDTO requestDTO);
    RegisterPersonResponseDTO processPrevious(RegisterPersonRequestDTO requestDTO);
}
