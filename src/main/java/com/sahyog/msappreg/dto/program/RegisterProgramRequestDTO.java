package com.sahyog.msappreg.dto.program;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import java.util.List;

@Data
public class RegisterProgramRequestDTO {
    private String pageId;
    @JsonAlias("appOrCaseNum")
    private String applicationNum;
    private List<String> programs;
}
