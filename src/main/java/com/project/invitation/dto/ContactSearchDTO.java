package com.project.invitation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContactSearchDTO {

    private String searchDateType;

    private String searchBy;

    private String searchQuery = "";

}
