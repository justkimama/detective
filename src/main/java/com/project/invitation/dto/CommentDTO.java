package com.project.invitation.dto;

import com.project.invitation.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CommentDTO {

    private Long contactNum;

    private String username;

    private String email;

    private String content;

    private List<CommentDTO> commentDtoList = new ArrayList<>();

    private  static ModelMapper modelMapper = new ModelMapper();

    public Comment createComment(){
        return modelMapper.map(this, Comment.class);
    }

    public static CommentDTO of(Comment comment){ return modelMapper.map(comment,CommentDTO.class); }

}
