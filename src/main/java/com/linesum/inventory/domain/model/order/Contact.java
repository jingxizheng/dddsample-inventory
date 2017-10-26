package com.linesum.inventory.domain.model.order;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.shared.Entity;

/**
 * 联系人
 */
public class Contact implements Entity<Contact> {

    private ContactId customerId;

    private String name;

    private String address;

    private String telephone;

    public Contact(ContactId customerId, String name, String address, String telephone) {
        Preconditions.checkNotNull(customerId, "customerId is required");
        Preconditions.checkNotNull(name, "name is required");
        Preconditions.checkNotNull(address, "address is required");
        Preconditions.checkNotNull(telephone, "telephone is required");
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    @Override
    public boolean sameIdentityAs(Contact other) {
        return other != null && this.customerId.sameValueAs(other.customerId);
    }

    public ContactId getCustomerId() {
        return customerId;
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
