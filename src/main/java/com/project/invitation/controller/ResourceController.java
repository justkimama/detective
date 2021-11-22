package com.project.invitation.controller;

import com.project.invitation.constant.UserRole;
import com.project.invitation.entity.Contact;
import com.project.invitation.entity.User;
import com.project.invitation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ResourceController {

    private final UserService userService;

    @GetMapping(value = "/admin/resources")
    public String resourceManage(Model model){
        UserRole role = UserRole.USER;
        List<User> users = userService.getUserList(role);
        model.addAttribute("users", users);
        return "resource";
    }
}