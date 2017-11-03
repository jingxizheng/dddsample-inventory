package com.linesum.inventory.domain.model.order;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.shared.Entity;

/**
 * 联系人
 */
public class Contact implements Entity<Contact> {

    private ContactId contactId;

    private String name;

    private String address;

    private String telephone;

    public Contact(ContactId contactId, String name, String address, String telephone) {
        Preconditions.checkNotNull(contactId, "contactId is required");
        Preconditions.checkNotNull(name, "name is required");
        Preconditions.checkNotNull(address, "address is required");
        Preconditions.checkNotNull(telephone, "telephone is required");
        this.contactId = contactId;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    @Override
    public boolean sameIdentityAs(Contact other) {
        return other != null && this.contactId.sameValueAs(other.contactId);
    }

    public ContactId getContactId() {
        return contactId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }
}
