package com.sahyog.msappreg.dto.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPersonRequestDTO {
    private String pageId;
    private String applicationNum;
    private PersonDTO person;
}
