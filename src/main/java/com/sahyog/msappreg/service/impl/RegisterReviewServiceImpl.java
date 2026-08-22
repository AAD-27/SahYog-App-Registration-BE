package com.sahyog.msappreg.service.impl;

import com.sahyog.msappreg.dto.review.*;
import com.sahyog.msappreg.entity.RegisterAddress;
import com.sahyog.msappreg.entity.RegisterPerson;
import com.sahyog.msappreg.repository.RegisterAddressRepository;
import com.sahyog.msappreg.repository.RegisterPersonRepository;
import com.sahyog.msappreg.repository.RegisterProgramRepository;
import com.sahyog.msappreg.repository.RegisterApplicationRepository;
import com.sahyog.msappreg.service.RegisterReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterReviewServiceImpl implements RegisterReviewService {
    private static final String PRIMARY_APPLICANT = "Primary Applicant";
    private static final String PERMANENT = "PERMANENT";
    private static final String TEMPORARY = "TEMPORARY";

    private final RegisterPersonRepository personRepository;
    private final RegisterAddressRepository addressRepository;
    private final RegisterProgramRepository programRepository;
    private final RegisterApplicationRepository applicationRepository;

    @Override
    @Transactional(readOnly = true)
    public RegisterReviewInitializeResponseDTO initialize(RegisterReviewInitializeRequestDTO requestDTO) {
        String applicationNum = requestDTO.getApplicationNum();
        if (applicationNum == null || applicationNum.isBlank()) {
            return new RegisterReviewInitializeResponseDTO(null, "AR005", null, null, List.of(), false);
        }
        RegisterPerson person = personRepository.findFirstByApplicationNumAndPersonTypeOrderByPersonIdAsc(
                applicationNum, PRIMARY_APPLICANT).orElse(null);
        RegisterAddress permanent = addressRepository.findFirstByApplicationNumAndAddressTypeOrderByAddressIdAsc(
                applicationNum, PERMANENT).orElse(null);
        RegisterAddress temporary = addressRepository.findFirstByApplicationNumAndAddressTypeOrderByAddressIdAsc(
                applicationNum, TEMPORARY).orElse(null);
        List<String> programs = programRepository.findByApplicationNumOrderByProgramIdAsc(applicationNum).stream()
                .map(program -> program.getProgramCode()).toList();

        ApplicantDetailsDTO applicant = person == null ? null : new ApplicantDetailsDTO(
                fullName(person.getFirstName(), person.getMiddleName(), person.getLastName()), person.getAge(), person.getGender());
        AddressDetailsDTO addresses = permanent == null && temporary == null ? null
                : new AddressDetailsDTO(toAddress(permanent), toAddress(temporary));
        boolean found = person != null || permanent != null || temporary != null || !programs.isEmpty();
        return new RegisterReviewInitializeResponseDTO(applicationNum, "AR005", applicant, addresses, programs, found);
    }

    @Override
    @Transactional
    public RegisterReviewSubmitResponseDTO submit(RegisterReviewSubmitRequestDTO requestDTO) {
        if (requestDTO.getApplicationNum() == null || requestDTO.getApplicationNum().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "applicationNum is required");
        }
        var application = applicationRepository.findByApplicationNumber(requestDTO.getApplicationNum())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Application not found: " + requestDTO.getApplicationNum()));
        application.setApplicationStatus("REGISTERED");
        applicationRepository.save(application);
        return new RegisterReviewSubmitResponseDTO(requestDTO.getApplicationNum(), "Registered");
    }

    private ReviewAddressDTO toAddress(RegisterAddress address) {
        return address == null ? null : new ReviewAddressDTO(address.getLine1(), address.getLine2(), address.getCity(),
                address.getState(), address.getCountry(), address.getPincode());
    }

    private String fullName(String firstName, String middleName, String lastName) {
        return String.join(" ", List.of(firstName, middleName, lastName).stream()
                .filter(value -> value != null && !value.isBlank()).toList());
    }
}
