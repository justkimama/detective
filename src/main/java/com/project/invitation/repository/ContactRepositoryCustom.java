package com.project.invitation.repository;

import com.project.invitation.dto.ContactSearchDTO;
import com.project.invitation.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactRepositoryCustom {

    Page<Contact> getAdminContactPage(ContactSearchDTO contactSearchDto, Pageable pageable);

}
