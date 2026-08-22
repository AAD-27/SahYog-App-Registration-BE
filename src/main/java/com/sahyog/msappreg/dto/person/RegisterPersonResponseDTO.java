package com.sahyog.msappreg.dto.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPersonResponseDTO {
    private String applicationNum;
    private String status;
}
