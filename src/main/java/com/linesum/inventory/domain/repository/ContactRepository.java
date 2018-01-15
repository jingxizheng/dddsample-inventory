package com.linesum.inventory.domain.repository;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.ContactId;

/**
 * Created by zhengjx on 2017/11/2.
 */
public interface ContactRepository {

    Contact find(ContactId contactId);
}
