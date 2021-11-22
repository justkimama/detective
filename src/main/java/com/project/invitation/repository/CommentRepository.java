package com.project.invitation.repository;

import com.project.invitation.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>{

    List<Comment> findByContactNum(Long contactNum);

    @Modifying
    @Query(value = "UPDATE Comment c set c.email = '...', c.content = :content where c.email = :email")
    int updateComment(String content, String email);
}