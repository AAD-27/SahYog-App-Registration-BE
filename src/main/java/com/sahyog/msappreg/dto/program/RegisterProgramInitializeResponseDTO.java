package com.sahyog.msappreg.dto.program;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RegisterProgramInitializeResponseDTO {
    private String applicationNum;
    private String pageId;
    private List<String> programs;
    private boolean found;
}
