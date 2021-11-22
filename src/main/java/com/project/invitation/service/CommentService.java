package com.project.invitation.service;

import com.project.invitation.dto.CommentDTO;
import com.project.invitation.entity.Comment;
import com.project.invitation.entity.Contact;
import com.project.invitation.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Long saveComment(CommentDTO commentDto) throws Exception{
        Comment comment = commentDto.createComment();
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentList(Long contactNum) {
        return commentRepository.findByContactNum(contactNum);
    }

    public void updateComment(Comment comment){
        commentRepository.updateComment(comment.getContent(),comment.getEmail());
    }
}