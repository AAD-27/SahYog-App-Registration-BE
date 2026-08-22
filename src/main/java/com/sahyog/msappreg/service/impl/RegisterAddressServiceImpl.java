package com.sahyog.msappreg.service.impl;

import com.sahyog.msappreg.dto.address.AddressDTO;
import com.sahyog.msappreg.dto.address.AddressDetailsDTO;
import com.sahyog.msappreg.dto.address.RegisterAddressInitializeRequestDTO;
import com.sahyog.msappreg.dto.address.RegisterAddressInitializeResponseDTO;
import com.sahyog.msappreg.dto.address.RegisterAddressRequestDTO;
import com.sahyog.msappreg.dto.address.RegisterAddressResponseDTO;
import com.sahyog.msappreg.entity.RegisterAddress;
import com.sahyog.msappreg.repository.RegisterAddressRepository;
import com.sahyog.msappreg.repository.RegisterApplicationRepository;
import com.sahyog.msappreg.service.RegisterAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RegisterAddressServiceImpl implements RegisterAddressService {
    private static final String PERMANENT = "PERMANENT";
    private static final String TEMPORARY = "TEMPORARY";
    private static final String PAGE_ID = "AR002";

    private final RegisterAddressRepository addressRepository;
    private final RegisterApplicationRepository applicationRepository;

    @Override
    @Transactional(readOnly = true)
    public RegisterAddressInitializeResponseDTO initialize(RegisterAddressInitializeRequestDTO requestDTO) {
        if (requestDTO.getApplicationNum() == null || requestDTO.getApplicationNum().isBlank()) {
            return new RegisterAddressInitializeResponseDTO(null, PAGE_ID, null, false);
        }
        RegisterAddress permanent = addressRepository.findFirstByApplicationNumAndAddressTypeOrderByAddressIdAsc(
                requestDTO.getApplicationNum(), PERMANENT).orElse(null);
        if (permanent == null) {
            return new RegisterAddressInitializeResponseDTO(requestDTO.getApplicationNum(), PAGE_ID, null, false);
        }
        RegisterAddress temporary = addressRepository.findFirstByApplicationNumAndAddressTypeOrderByAddressIdAsc(
                requestDTO.getApplicationNum(), TEMPORARY).orElse(null);
        AddressDetailsDTO permanentDetails = toDetails(permanent);
        AddressDetailsDTO temporaryDetails = temporary == null ? null : toDetails(temporary);
        boolean sameAsPermanent = temporary != null && permanentDetails.equals(temporaryDetails);
        return new RegisterAddressInitializeResponseDTO(requestDTO.getApplicationNum(), PAGE_ID,
                new AddressDTO(permanentDetails, temporaryDetails, sameAsPermanent), true);
    }

    @Override
    @Transactional
    public RegisterAddressResponseDTO processNext(RegisterAddressRequestDTO requestDTO) {
        saveAddresses(requestDTO);
        return new RegisterAddressResponseDTO(requestDTO.getApplicationNum(), "Saved");
    }

    @Override
    @Transactional
    public RegisterAddressResponseDTO processPrevious(RegisterAddressRequestDTO requestDTO) {
        saveAddresses(requestDTO);
        return new RegisterAddressResponseDTO(requestDTO.getApplicationNum(), "Saved");
    }

    private void saveAddresses(RegisterAddressRequestDTO requestDTO) {
        validateRequest(requestDTO);
        if (!applicationRepository.existsById(requestDTO.getApplicationNum())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Application not found: " + requestDTO.getApplicationNum());
        }
        AddressDetailsDTO permanent = requestDTO.getAddress().getPermanent();
        AddressDetailsDTO temporary = requestDTO.getAddress().isSameAsPermanent()
                ? permanent : requestDTO.getAddress().getTemporary();
        validateAddress(permanent, "permanent");
        validateAddress(temporary, "temporary");
        saveAddress(requestDTO.getApplicationNum(), PERMANENT, permanent);
        saveAddress(requestDTO.getApplicationNum(), TEMPORARY, temporary);
    }

    private void saveAddress(String applicationNum, String addressType, AddressDetailsDTO details) {
        RegisterAddress address = addressRepository.findFirstByApplicationNumAndAddressTypeOrderByAddressIdAsc(
                applicationNum, addressType).orElseGet(RegisterAddress::new);
        address.setApplicationNum(applicationNum);
        address.setAddressType(addressType);
        address.setLine1(details.getLine1());
        address.setLine2(details.getLine2());
        address.setCity(details.getCity());
        address.setState(details.getState());
        address.setCountry(details.getCountry());
        address.setPincode(details.getPincode());
        addressRepository.save(address);
    }

    private void validateRequest(RegisterAddressRequestDTO requestDTO) {
        if (requestDTO.getApplicationNum() == null || requestDTO.getApplicationNum().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "applicationNum is required");
        }
        if (requestDTO.getAddress() == null || requestDTO.getAddress().getPermanent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "permanent address is required");
        }
        if (!requestDTO.getAddress().isSameAsPermanent() && requestDTO.getAddress().getTemporary() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "temporary address is required when sameAsPermanent is false");
        }
    }

    private void validateAddress(AddressDetailsDTO address, String addressType) {
        if (isBlank(address.getLine1()) || isBlank(address.getCity()) || isBlank(address.getState())
                || isBlank(address.getCountry()) || isBlank(address.getPincode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    addressType + " address requires line1, city, state, country, and pincode");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private AddressDetailsDTO toDetails(RegisterAddress address) {
        return new AddressDetailsDTO(address.getLine1(), address.getLine2(), address.getCity(),
                address.getState(), address.getCountry(), address.getPincode());
    }
}
