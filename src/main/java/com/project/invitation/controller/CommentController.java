package com.project.invitation.controller;

import com.project.invitation.dto.CommentDTO;
import com.project.invitation.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/members/comment")
    private void writeComment(@RequestParam("contactNum") String contactNum, @RequestParam("content") String content, Principal principal) throws Exception {
        Long contactLong = Long.parseLong(contactNum);
        CommentDTO commentDto = new CommentDTO();
        commentDto.setContactNum(contactLong);
        String email = principal.getName();
        commentDto.setEmail(email);
        commentDto.setContent(content);
        commentDto.setUsername("Writer");

        commentService.saveComment(commentDto);
    }
}
