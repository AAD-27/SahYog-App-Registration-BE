package com.sahyog.msappreg.service.impl;

import com.sahyog.msappreg.dto.InitializeRequestDTO;
import com.sahyog.msappreg.dto.InitializeResponseDTO;
import com.sahyog.msappreg.dto.NextRequestDTO;
import com.sahyog.msappreg.dto.NextResponseDTO;
import com.sahyog.msappreg.entity.Application;
import com.sahyog.msappreg.entity.ApplicationSequence;
import com.sahyog.msappreg.repository.RegisterApplicationRepository;
import com.sahyog.msappreg.repository.ApplicationSequenceRepository;
import com.sahyog.msappreg.repository.RegisterAddressRepository;
import com.sahyog.msappreg.repository.RegisterPersonRepository;
import com.sahyog.msappreg.repository.RegisterProgramRepository;
import com.sahyog.msappreg.service.RegisterApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterApplicationServiceImpl implements RegisterApplicationService {

    private final RegisterApplicationRepository repository;
    private final ApplicationSequenceRepository sequenceRepository;
    private final RegisterAddressRepository addressRepository;
    private final RegisterPersonRepository personRepository;
    private final RegisterProgramRepository programRepository;

    private static final String APPLICATION_PREFIX = "A";
    private static final String SEQUENCE_ID = "APP_SEQ";
    private static final long INITIAL_APPLICATION_NUMBER = 9000000L;

    @Override
    public InitializeResponseDTO initializeApplication(InitializeRequestDTO requestDTO) {
        InitializeResponseDTO responseDTO = new InitializeResponseDTO();
        
        if (requestDTO.getApplicationNum() != null && !requestDTO.getApplicationNum().isEmpty()) {
            Optional<Application> existingApp = repository.findByApplicationNumber(requestDTO.getApplicationNum());
            
            if (existingApp.isPresent()) {
                Application app = existingApp.get();
                responseDTO.setApplicationNum(app.getApplicationNumber());
                responseDTO.setPageId(resolveCurrentPage(app));
                responseDTO.setFirstName(app.getFirstName());
                responseDTO.setMiddleName(app.getMiddleName());
                responseDTO.setLastName(app.getLastName());
                responseDTO.setMobileNumber(app.getMobileNumber());
                responseDTO.setEmailAddress(app.getEmailAddress());
                responseDTO.setApplicationDate(app.getApplicationDate());
                responseDTO.setApplicationStatus(displayStatus(app.getApplicationStatus()));
                responseDTO.setFound(true);
                return responseDTO;
            }
        }
        
        responseDTO.setFound(false);
        return responseDTO;
    }

    @Override
    @Transactional
    public NextResponseDTO processNext(NextRequestDTO requestDTO) {
        String applicationNumber;
        if (requestDTO.getApplicationNum() != null && !requestDTO.getApplicationNum().isEmpty()) {
            applicationNumber = requestDTO.getApplicationNum();
        } else {
            applicationNumber = generateApplicationNumber();
        }
        
        Application application = repository.findByApplicationNumber(applicationNumber)
                .orElse(new Application());
        
        application.setApplicationNumber(applicationNumber);
        application.setPageId("AR002");
        application.setFirstName(requestDTO.getFirstName());
        application.setMiddleName(requestDTO.getMiddleName());
        application.setLastName(requestDTO.getLastName());
        application.setMobileNumber(requestDTO.getMobileNumber());
        application.setEmailAddress(requestDTO.getEmailAddress());
        application.setApplicationDate(requestDTO.getApplicationDate());
        application.setApplicationStatus("IN_PROGRESS");
        application.setApplicantName(requestDTO.getFirstName() + " " + requestDTO.getMiddleName() + " " + requestDTO.getLastName());
        application.setApplicantEmail(requestDTO.getEmailAddress());
        application.setProgramName(requestDTO.getPageId());
        
        repository.save(application);

        return new NextResponseDTO(applicationNumber, "In Progress");
    }

    private String displayStatus(String status) {
        if ("REGISTERED".equalsIgnoreCase(status)) {
            return "Registered";
        }
        return "COMPLETED".equalsIgnoreCase(status) ? "Completed" : "In Progress";
    }

    private String resolveCurrentPage(Application application) {
        if ("Completed".equals(displayStatus(application.getApplicationStatus()))) {
            return "AR005";
        }
        if (application.getPageId() != null && !application.getPageId().isBlank()
                && !"AR001".equals(application.getPageId())) {
            return application.getPageId();
        }
        String applicationNum = application.getApplicationNumber();
        if (!programRepository.findByApplicationNumOrderByProgramIdAsc(applicationNum).isEmpty()) {
            return "AR005";
        }
        if (personRepository.findFirstByApplicationNumAndPersonTypeOrderByPersonIdAsc(
                applicationNum, "Primary Applicant").isPresent()) {
            return "AR004";
        }
        if (addressRepository.findFirstByApplicationNumAndAddressTypeOrderByAddressIdAsc(
                applicationNum, "PERMANENT").isPresent()) {
            return "AR003";
        }
        return "AR002";
    }

    /**
     * Called by the final (AR005) submit flow once all registration screens are complete.
     */
    @Override
    @Transactional
    public void completeApplication(String applicationNum) {
        Application application = repository.findByApplicationNumber(applicationNum)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + applicationNum));
        application.setApplicationStatus("COMPLETED");
        repository.save(application);
    }

    @Transactional
    private String generateApplicationNumber() {
        ApplicationSequence sequence = sequenceRepository.findById(SEQUENCE_ID).orElse(null);
        
        if (sequence == null) {
            sequence = new ApplicationSequence(SEQUENCE_ID, INITIAL_APPLICATION_NUMBER, null);
            sequenceRepository.save(sequence);
        }
        
        Long nextValue = sequence.getNextValue();
        sequence.setNextValue(nextValue + 1);
        sequenceRepository.save(sequence);
        
        return APPLICATION_PREFIX + nextValue;
    }
}
