package com.sahyog.msappreg.dto.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private String personType;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dob;
    private String gender;
    private String casteRace;
    private String religion;
    private String maritalStatus;
    private String aadharNumber;
    private String panNumber;
    private String passportNumber;
}
