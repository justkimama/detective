package com.project.invitation.entity;

import com.project.invitation.constant.UserRole;
import com.project.invitation.dto.UserFormDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Data
@ToString
@NoArgsConstructor
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @CreationTimestamp
    private Timestamp createTimestamp;

    @UpdateTimestamp
    private Timestamp updateTimeStamp;

    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;


    public static User createUser(UserFormDTO userFormDTO, PasswordEncoder passwordEncoder) {

        User user = new User();
        user.setEmail(userFormDTO.getEmail());
        String password = passwordEncoder.encode(userFormDTO.getPassword());
        user.setPassword(password);
        user.setRole(UserRole.USER);
        return user;
    }

    public static User modifyUser(UserFormDTO userFormDTO, PasswordEncoder passwordEncoder) {

        User user = new User();
        user.setEmail(userFormDTO.getEmail());
        String password = passwordEncoder.encode(userFormDTO.getPassword());
        user.setPassword(password);
        return user;
    }

    public static User deleteUser(UserFormDTO userFormDTO) {

        User user = new User();
        user.setEmail(userFormDTO.getEmail());
        return user;
    }
}