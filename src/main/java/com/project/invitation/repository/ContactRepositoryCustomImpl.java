package com.project.invitation.repository;

import com.project.invitation.dto.ContactSearchDTO;
import com.project.invitation.entity.Contact;
import com.project.invitation.entity.QContact;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class ContactRepositoryCustomImpl implements ContactRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public ContactRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("name",searchBy)){
            return QContact.contact.name.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("message",searchBy)){
            return QContact.contact.message.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<Contact> getAdminContactPage(ContactSearchDTO contactSearchDto, Pageable pageable){
        QueryResults<Contact> results = queryFactory
                .selectFrom(QContact.contact)
                .where(searchByLike(contactSearchDto.getSearchBy(),
                        contactSearchDto.getSearchQuery()))
                .orderBy(QContact.contact.id.desc())
                .offset((pageable.getOffset()))
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Contact> content =results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);
    }
}
