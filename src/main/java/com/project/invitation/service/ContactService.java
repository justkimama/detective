package com.project.invitation.service;


import com.project.invitation.dto.ContactFormDTO;
import com.project.invitation.dto.ContactSearchDTO;
import com.project.invitation.entity.Contact;
import com.project.invitation.entity.User;
import com.project.invitation.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public Long saveContact(ContactFormDTO contactFormDTO) throws Exception{
        Contact contact = contactFormDTO.createContact();
        contactRepository.save(contact);
        return contact.getId();
    }

    @Transactional(readOnly = true)
    public List<Contact> getContactList(String email){
        return contactRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Page<Contact> getAdminContactPage(ContactSearchDTO contactSearchDto, Pageable pageable){

        return contactRepository.getAdminContactPage(contactSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public ContactFormDTO getContactDtl(Long id) {
        List<ContactFormDTO> contactFormDTOList = new ArrayList<>();
        Contact contact = contactRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        ContactFormDTO contactFormDTO = ContactFormDTO.of(contact);
        return contactFormDTO;
    }

    public void updateContact(Contact contact){
        contactRepository.updateContact(contact.getName(),contact.getSubject(),contact.getMessage(),contact.getEmail());
    }
}
