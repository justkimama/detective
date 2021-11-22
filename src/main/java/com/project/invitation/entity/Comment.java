package com.project.invitation.entity;

import com.project.invitation.dto.CommentDTO;
import com.project.invitation.dto.ContactFormDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String email;

    private Long contactNum;

    private String username;

    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="contact_id")
    private Contact contact;

    @CreationTimestamp
    private Timestamp createTimestamp;

    public static Comment modifyComment(CommentDTO commentDTO) {

        Comment comment = new Comment();
        comment.setEmail(commentDTO.getEmail());
        comment.setContent(commentDTO.getContent());
        return comment;
    }
}
