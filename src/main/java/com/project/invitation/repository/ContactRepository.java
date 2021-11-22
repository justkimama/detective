package com.project.invitation.repository;

import com.project.invitation.entity.Contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long>,
    QuerydslPredicateExecutor<Contact>, ContactRepositoryCustom{

    List<Contact> findByEmail(String email);


    @Modifying
    @Query(value = "UPDATE Contact c set c.email = '...', c.name = :name, c.subject = :subject, c.message = :message where c.email = :email")
    int updateContact(String name, String subject, String message, String email);
}