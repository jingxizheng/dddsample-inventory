package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.ContactId;
import com.linesum.inventory.domain.repository.ContactRepository;
import com.linesum.inventory.infrastructure.persistence.jpa.ContactRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.mapper.ContactMapper;
import com.linesum.inventory.infrastructure.persistence.po.ContactPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by zhengjx on 2017/11/2.
 */
@Repository
public class ContactRepositoryImpl implements ContactRepository {

    @Autowired
    private ContactRepositoryJpa contactRepositoryJpa;

    @Autowired
    private ContactMapper contactMapper;

    @Override
    public Contact find(ContactId contactId) {
//        ContactPo contactPo = contactMapper.selectByPrimaryKey(contactId.getId());
        ContactPo contactPo = contactRepositoryJpa.findOne(contactId.getId());
        return new Contact(
                new ContactId(contactPo.getId()),
                contactPo.getName(),
                contactPo.getAddress(),
                contactPo.getTelephone()
        );
    }
}
