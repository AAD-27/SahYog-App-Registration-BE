package com.sahyog.msappreg.dto.program;

import lombok.Data;
import java.util.List;

@Data
public class RegisterProgramRequestDTO {
    private String pageId;
    private String applicationNum;
    private List<String> programs;
}
