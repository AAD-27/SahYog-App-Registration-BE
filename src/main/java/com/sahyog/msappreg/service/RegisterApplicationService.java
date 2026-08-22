package com.sahyog.msappreg.service;

import com.sahyog.msappreg.dto.InitializeRequestDTO;
import com.sahyog.msappreg.dto.InitializeResponseDTO;
import com.sahyog.msappreg.dto.NextRequestDTO;
import com.sahyog.msappreg.dto.NextResponseDTO;

public interface RegisterApplicationService {
    InitializeResponseDTO initializeApplication(InitializeRequestDTO requestDTO);
    NextResponseDTO processNext(NextRequestDTO requestDTO);
    void completeApplication(String applicationNum);
}
