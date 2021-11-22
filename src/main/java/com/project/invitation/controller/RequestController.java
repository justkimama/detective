package com.project.invitation.controller;

import com.project.invitation.dto.CommentDTO;
import com.project.invitation.dto.ContactFormDTO;
import com.project.invitation.dto.ContactSearchDTO;
import com.project.invitation.entity.Comment;
import com.project.invitation.entity.Contact;
import com.project.invitation.service.CommentService;
import com.project.invitation.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RequestController {

    private final ContactService contactService;

    private final CommentService commentService;

    @GetMapping(value = {"/admin/requests", "/admin/requests/{page}"})
    public String contactManage(ContactSearchDTO contactSearchDto,
                                @PathVariable("page") Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,5);
        Page<Contact> contacts = contactService.getAdminContactPage(contactSearchDto, pageable);
        model.addAttribute("contacts", contacts);
        model.addAttribute("contactSearchDto",contactSearchDto);
        model.addAttribute("maxPage", 5);
        return "list";
    }

    public String getRandomPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&'};
        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());
        int idx = 0;
        int len = charSet.length;
        for (int i = 0; i < 10; i++) {
            idx = sr.nextInt(len);
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }

    @GetMapping(value = "/admin/requests/case={id}")
    public String contactDtl(Model model, @PathVariable("page")Optional<Integer> page,
                             @PathVariable("id") Long id){
        String password = getRandomPassword();
        System.out.println(password);
        ContactFormDTO contactFormDto = contactService.getContactDtl(id);
        List<Comment> comments = commentService.getCommentList(id);
        model.addAttribute("contact", contactFormDto);
        model.addAttribute("randomPassword", password);
        model.addAttribute("comments", comments);
        model.addAttribute("case", id);
        return "contactDtl";
    }

    @GetMapping(value = "/members/requests")
    public String userContactManage(Model model, Principal principal){
        List<Contact> contacts = contactService.getContactList(principal.getName());
        model.addAttribute("contacts", contacts);
        return "private";
    }


    @GetMapping(value = "/members/requests/case={id}")
    public String userContactDtl(Model model, @PathVariable("id") Long id, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ContactFormDTO contactFormDto = contactService.getContactDtl(id);
        List<Comment> comments = commentService.getCommentList(id);
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))
                && principal.getName().equals(contactFormDto.getEmail())) {
            model.addAttribute("contact", contactFormDto);
            model.addAttribute("comments", comments);
            model.addAttribute("case", id);
            return "privateDtl";
        } else {
            return "error";
        }
    }
}