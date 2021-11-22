package com.project.invitation.controller;

import com.project.invitation.dto.CommentDTO;
import com.project.invitation.dto.ContactFormDTO;
import com.project.invitation.dto.MailDTO;
import com.project.invitation.dto.UserFormDTO;
import com.project.invitation.entity.Comment;
import com.project.invitation.entity.Contact;
import com.project.invitation.entity.User;
import com.project.invitation.service.CommentService;
import com.project.invitation.service.ContactService;
import com.project.invitation.service.MailService;
import com.project.invitation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private final CommentService commentService;

    private final PasswordEncoder passwordEncoder;

    private final ContactService contactService;

    @Autowired
    private MailService mailService;

    @PostMapping(value = "/admin/members/new")
    public void userForm(@RequestParam("email") String email, @RequestParam("password") String password) {

        UserFormDTO userFormDto = new UserFormDTO();
        userFormDto.setEmail(email);
        userFormDto.setPassword(password);

        User user = User.createUser(userFormDto, passwordEncoder);
        userService.saveUser(user);
    }

    @RequestMapping(value = "/admin/email", method = RequestMethod.POST)
    public void sendMail(@RequestParam("email") String email, @RequestParam("password") String password) {

        MailDTO mailDto = new MailDTO();

        mailDto.setAddress(email);
        mailDto.setTitle("Thank you for your patience.");
        mailDto.setContent("ID: " + email + System.lineSeparator() + "Password: " + password);

        mailService.sendMail(mailDto);

    }

    @PostMapping("/admin/comment")
    private void writeComment(@RequestParam("contactNum") String contactNum, @RequestParam("content") String content, Principal principal) throws Exception {
        Long contactLong = Long.parseLong(contactNum);
        CommentDTO commentDto = new CommentDTO();
        commentDto.setContactNum(contactLong);
        String email = principal.getName();
        commentDto.setEmail(email);
        commentDto.setContent(content);
        commentDto.setUsername("Detective");

        commentService.saveComment(commentDto);
    }

    @RequestMapping (value = "/admin/shred", method = RequestMethod.POST)
    private void deleteUser(@RequestParam("email") String email){

        UserFormDTO userFormDTO = new UserFormDTO();
        userFormDTO.setEmail(email);
        User user = User.deleteUser(userFormDTO);

        ContactFormDTO contactFormDTO = new ContactFormDTO();
        contactFormDTO.setEmail(email);
        contactFormDTO.setName("...");
        contactFormDTO.setMessage("삭제되었습니다.");
        contactFormDTO.setSubject("삭제되었습니다.");
        Contact contact = Contact.modifyContact(contactFormDTO);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setEmail(email);
        commentDTO.setContent("삭제되었습니다.");
        Comment comment = Comment.modifyComment(commentDTO);

        contactService.updateContact(contact);
        commentService.updateComment(comment);
        userService.deleteUser(user);
    }
}