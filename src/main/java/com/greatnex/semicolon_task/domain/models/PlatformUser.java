package com.greatnex.semicolon_task.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.greatnex.semicolon_task.domain.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static com.greatnex.semicolon_task.domain.validator.InputValidator.validateInput;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlatformUser {
    private String id;
    private String username;
    private String name;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private boolean emailVerified;
    private String newPassword;
    private LocalDate dateOfBirth;
    private Gender gender;
    private boolean enabled;
    private String keycloakClientId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXXXX'['VV']'")
    private ZonedDateTime dateCreated;


    public void validateUserRequiredData(){
        validateInput(getEmail(), "email");
        validateInput(getFirstName(), "firstName");
        validateInput(getLastName(), "lastName");
        validateInput(getMiddleName(), "middleName");
        validateInput(getPhoneNumber(), "phoneNumber");
        validateInput(getUsername(), "username");
        validateInput(getKeycloakClientId(), "keycloakClientId");
    }

   }
