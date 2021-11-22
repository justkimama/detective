package com.project.invitation.controller;

import com.project.invitation.dto.UserFormDTO;
import com.project.invitation.entity.User;
import com.project.invitation.repository.UserRepository;
import com.project.invitation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/members/login")
    public String userLogin() {
        return "/member";
    }

    @GetMapping(value = "/members/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "Please confirm your ID or password.");
        return "member";
    }

    @GetMapping(value = "/members/modify")
    public String userModify(Model model, Principal principal) {
        model.addAttribute("userFormDto", new UserFormDTO());
        model.addAttribute("email", principal.getName());
        return "modify";
    }

    @PostMapping(value = "/members/modify")
    public String userModifyForm(@Valid @ModelAttribute("userFormDto") UserFormDTO userFormDto,
                                 BindingResult bindingResult, Principal principal, Model model) {
        String password = userFormDto.getPassword();
        String email = principal.getName();

        if (bindingResult.hasErrors()) {
            model.addAttribute("email", email);
            return "modify";
        }
        try {
            userFormDto.setEmail(email);
            userFormDto.setPassword(password);
            User user = User.modifyUser(userFormDto, passwordEncoder);
            userService.updateUser(user);
        } catch (Exception e){
            model.addAttribute("email", email);
            return "modify";
        }
        return "redirect:/";
    }
}