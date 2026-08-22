package com.sahyog.msappreg.dto.program;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterProgramResponseDTO {
    private String applicationNum;
    private String status;
}
