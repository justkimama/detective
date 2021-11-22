package com.project.invitation.entity;

import com.project.invitation.dto.ContactFormDTO;
import com.project.invitation.dto.UserFormDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Entity
@ToString
@NoArgsConstructor
@Getter @Setter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;

    private String name;

    private String email;

    private String subject;

    private String message;

    @CreationTimestamp
    private Timestamp createTimestamp;

    @OneToMany(mappedBy = "contact")
    private List<Comment> comments;

    public static Contact createContact(ContactFormDTO contactFormDTO) {

        Contact contact = new Contact();
        contact.setName(contactFormDTO.getName());
        contact.setName(contactFormDTO.getEmail());
        contact.setName(contactFormDTO.getSubject());
        contact.setName(contactFormDTO.getMessage());
        return contact;
    }

    public static Contact modifyContact(ContactFormDTO contactFormDTO) {

        Contact contact = new Contact();
        contact.setName(contactFormDTO.getName());
        contact.setEmail(contactFormDTO.getEmail());
        contact.setSubject(contactFormDTO.getSubject());
        contact.setMessage(contactFormDTO.getMessage());
        return contact;
    }
}