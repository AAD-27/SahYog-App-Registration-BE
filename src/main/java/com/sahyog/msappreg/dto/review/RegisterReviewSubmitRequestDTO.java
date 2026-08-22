package com.sahyog.msappreg.dto.review;

import com.sahyog.msappreg.dto.person.PersonDTO;
import lombok.Data;

import java.util.List;

@Data
public class RegisterReviewSubmitRequestDTO {
    private String pageId;
    private String applicationNum;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNumber;
    private String emailAddress;
    private String applicationDate;
    private PersonDTO person;
    private List<String> programs;
}
