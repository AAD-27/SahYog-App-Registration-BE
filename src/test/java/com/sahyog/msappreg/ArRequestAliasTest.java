package com.sahyog.msappreg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahyog.msappreg.dto.InitializeRequestDTO;
import com.sahyog.msappreg.dto.address.RegisterAddressInitializeRequestDTO;
import com.sahyog.msappreg.dto.person.RegisterPersonInitializeRequestDTO;
import com.sahyog.msappreg.dto.program.RegisterProgramInitializeRequestDTO;
import com.sahyog.msappreg.dto.review.RegisterReviewInitializeRequestDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArRequestAliasTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void appOrCaseNumIsAcceptedByAllInitializeRequests() throws Exception {
        String json = "{\"appOrCaseNum\":\"A9000005\"}";

        assertEquals("A9000005", objectMapper.readValue(json, InitializeRequestDTO.class).getApplicationNum());
        assertEquals("A9000005", objectMapper.readValue(
                json, RegisterAddressInitializeRequestDTO.class).getApplicationNum());
        assertEquals("A9000005", objectMapper.readValue(
                json, RegisterPersonInitializeRequestDTO.class).getApplicationNum());
        assertEquals("A9000005", objectMapper.readValue(
                json, RegisterProgramInitializeRequestDTO.class).getApplicationNum());
        assertEquals("A9000005", objectMapper.readValue(
                json, RegisterReviewInitializeRequestDTO.class).getApplicationNum());
    }
}
