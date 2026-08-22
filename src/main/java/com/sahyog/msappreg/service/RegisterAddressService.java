package com.sahyog.msappreg.service;

import com.sahyog.msappreg.dto.address.RegisterAddressInitializeRequestDTO;
import com.sahyog.msappreg.dto.address.RegisterAddressInitializeResponseDTO;
import com.sahyog.msappreg.dto.address.RegisterAddressRequestDTO;
import com.sahyog.msappreg.dto.address.RegisterAddressResponseDTO;

public interface RegisterAddressService {
    RegisterAddressInitializeResponseDTO initialize(RegisterAddressInitializeRequestDTO requestDTO);
    RegisterAddressResponseDTO processNext(RegisterAddressRequestDTO requestDTO);
    RegisterAddressResponseDTO processPrevious(RegisterAddressRequestDTO requestDTO);
}
