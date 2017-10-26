package com.linesum.inventory.domain.model.order;

import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Objects;

/**
 * 联系人ID
 */
public class ContactId implements ValueObject<ContactId> {

    private Long id;

    public ContactId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean sameValueAs(ContactId other) {
        return other != null && Objects.equals(this.id, other.id);
    }
}
