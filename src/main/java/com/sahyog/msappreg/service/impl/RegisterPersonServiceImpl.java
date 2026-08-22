package com.sahyog.msappreg.service.impl;

import com.sahyog.msappreg.dto.person.PersonDTO;
import com.sahyog.msappreg.dto.person.RegisterPersonInitializeRequestDTO;
import com.sahyog.msappreg.dto.person.RegisterPersonInitializeResponseDTO;
import com.sahyog.msappreg.dto.person.RegisterPersonRequestDTO;
import com.sahyog.msappreg.dto.person.RegisterPersonResponseDTO;
import com.sahyog.msappreg.entity.Application;
import com.sahyog.msappreg.entity.RegisterPerson;
import com.sahyog.msappreg.repository.RegisterApplicationRepository;
import com.sahyog.msappreg.repository.RegisterPersonRepository;
import com.sahyog.msappreg.service.RegisterPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class RegisterPersonServiceImpl implements RegisterPersonService {
    private static final String PAGE_ID = "AR003";
    private static final String PRIMARY_APPLICANT = "Primary Applicant";

    private final RegisterPersonRepository personRepository;
    private final RegisterApplicationRepository applicationRepository;

    @Override
    @Transactional(readOnly = true)
    public RegisterPersonInitializeResponseDTO initialize(RegisterPersonInitializeRequestDTO requestDTO) {
        if (requestDTO.getApplicationNum() == null || requestDTO.getApplicationNum().isBlank()) {
            return new RegisterPersonInitializeResponseDTO(null, PAGE_ID, null, false);
        }

        Application application = applicationRepository.findByApplicationNumber(requestDTO.getApplicationNum())
                .orElse(null);
        if (application == null) {
            return new RegisterPersonInitializeResponseDTO(requestDTO.getApplicationNum(), PAGE_ID, null, false);
        }

        RegisterPerson savedPerson = personRepository.findFirstByApplicationNumAndPersonTypeOrderByPersonIdAsc(
                requestDTO.getApplicationNum(), PRIMARY_APPLICANT).orElse(null);
        PersonDTO prefilledPerson = savedPerson == null ? new PersonDTO() : toDto(savedPerson);
        prefilledPerson.setPersonType(PRIMARY_APPLICANT);
        // AR001 owns the applicant name, so AR003 always displays the latest AR001 values.
        prefilledPerson.setFirstName(application.getFirstName());
        prefilledPerson.setMiddleName(application.getMiddleName());
        prefilledPerson.setLastName(application.getLastName());
        return new RegisterPersonInitializeResponseDTO(requestDTO.getApplicationNum(), PAGE_ID,
                prefilledPerson, savedPerson != null);
    }

    @Override
    @Transactional
    public RegisterPersonResponseDTO processNext(RegisterPersonRequestDTO requestDTO) {
        savePerson(requestDTO);
        return new RegisterPersonResponseDTO(requestDTO.getApplicationNum(), "Saved");
    }

    @Override
    @Transactional
    public RegisterPersonResponseDTO processPrevious(RegisterPersonRequestDTO requestDTO) {
        savePerson(requestDTO);
        return new RegisterPersonResponseDTO(requestDTO.getApplicationNum(), "Saved");
    }

    private void savePerson(RegisterPersonRequestDTO requestDTO) {
        validateRequest(requestDTO);
        if (!applicationRepository.existsById(requestDTO.getApplicationNum())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Application not found: " + requestDTO.getApplicationNum());
        }

        PersonDTO details = requestDTO.getPerson();
        RegisterPerson person = personRepository.findFirstByApplicationNumAndPersonTypeOrderByPersonIdAsc(
                requestDTO.getApplicationNum(), details.getPersonType()).orElseGet(RegisterPerson::new);
        person.setApplicationNum(requestDTO.getApplicationNum());
        person.setPersonType(details.getPersonType());
        person.setFirstName(details.getFirstName());
        person.setMiddleName(details.getMiddleName());
        person.setLastName(details.getLastName());
        person.setDob(details.getDob());
        person.setAge(Period.between(details.getDob(), LocalDate.now()).getYears());
        person.setGender(details.getGender());
        person.setCasteRace(details.getCasteRace());
        person.setReligion(details.getReligion());
        person.setMaritalStatus(details.getMaritalStatus());
        person.setAadharNumber(details.getAadharNumber());
        person.setPanNumber(details.getPanNumber());
        person.setPassportNumber(details.getPassportNumber());
        personRepository.save(person);
    }

    private void validateRequest(RegisterPersonRequestDTO requestDTO) {
        if (requestDTO.getApplicationNum() == null || requestDTO.getApplicationNum().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "applicationNum is required");
        }
        PersonDTO person = requestDTO.getPerson();
        if (person == null || isBlank(person.getPersonType()) || isBlank(person.getFirstName())
                || isBlank(person.getLastName()) || person.getDob() == null || isBlank(person.getGender())
                || isBlank(person.getAadharNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "personType, firstName, lastName, dob, gender, and aadharNumber are required");
        }
        if (person.getDob().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dob cannot be in the future");
        }
        if (!person.getAadharNumber().matches("\\d{12}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "aadharNumber must contain exactly 12 digits");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private PersonDTO toDto(RegisterPerson person) {
        return new PersonDTO(person.getPersonType(), person.getFirstName(), person.getMiddleName(),
                person.getLastName(), person.getDob(), person.getGender(), person.getCasteRace(),
                person.getReligion(), person.getMaritalStatus(), person.getAadharNumber(),
                person.getPanNumber(), person.getPassportNumber());
    }
}
