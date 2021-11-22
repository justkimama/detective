package com.project.invitation.controller;

import com.project.invitation.config.CustomAuthenticationEntryPoint;
import com.project.invitation.dto.ContactFormDTO;
import com.project.invitation.dto.ContactSearchDTO;
import com.project.invitation.entity.Contact;
import com.project.invitation.entity.User;
import com.project.invitation.repository.ContactRepository;
import com.project.invitation.repository.UserRepository;
import com.project.invitation.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Member;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping(value = {"/","/contact/new"})
    public String contactForm(Model model){
        model.addAttribute("contactFormDto", new ContactFormDTO());
        return "index";
    }

    @PostMapping(value = "/contact/new")
    public String contactNew(@Valid @ModelAttribute("contactFormDto") ContactFormDTO contactFormDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()) {
            return "index";
        }

        try{
            contactService.saveContact(contactFormDTO);
        } catch (Exception e){
            return "index";
        }
        return "message";
    }
}
