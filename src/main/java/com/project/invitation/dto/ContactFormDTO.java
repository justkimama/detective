package com.project.invitation.dto;

import com.project.invitation.entity.Contact;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ContactFormDTO {

    private String id;

    @NotBlank(message = "Please enter your name.")
    private String name;

    @NotBlank(message = "Please enter your E-mail address.")
    private String email;

    @NotBlank(message = "Please enter your subject.")
    private String subject;

    @NotBlank(message = "Please enter your message.")
    private String message;

    private List<ContactFormDTO> contactFormDTOList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Contact createContact(){
        return modelMapper.map(this,Contact.class);
    }

    public static ContactFormDTO of(Contact contact){
        return modelMapper.map(contact,ContactFormDTO.class);
    }
}
