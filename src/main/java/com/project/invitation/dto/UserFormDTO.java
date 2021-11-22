package com.project.invitation.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class UserFormDTO {

    @NotBlank(message = "Please enter your E-mail address.")
    private String email;

    @NotBlank(message = "Please enter your password.")
    @Length(min = 8, message = "Password should be more than 8 characters.")
    private String password;
}
