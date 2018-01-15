package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.ContactId;
import com.linesum.inventory.domain.repository.ContactRepository;
import com.linesum.inventory.BaseJunitTestCase;
import com.linesum.inventory.infrastructure.persistence.jpa.ContactRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.po.ContactPo;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhengjx on 2017/11/2.
 */
public class ContactRepositoryImplTest extends BaseJunitTestCase {

    @Autowired
    private ContactRepositoryJpa contactRepositoryJpa;

    @Autowired
    private ContactRepository contactRepository;

    private Long contactId;

    @Before
    public void setUp() throws Exception {
        ContactPo contactPo = contactRepositoryJpa.save(new ContactPo(
                null,
                "contact_name",
                "contact_address",
                "contact_telephone"
        ));
        contactId = contactPo.getId();
    }

    @Test
    public void find() throws Exception {
        Contact contact = contactRepository.find(new ContactId(contactId));
        Assertions.assertThat(contact)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("name", "contact_name")
                .hasFieldOrPropertyWithValue("address", "contact_address")
                .hasFieldOrPropertyWithValue("telephone", "contact_telephone");
    }

}