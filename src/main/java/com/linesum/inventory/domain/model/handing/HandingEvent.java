package com.linesum.inventory.domain.model.handing;

import com.linesum.inventory.domain.model.order.Order;
import com.linesum.inventory.domain.shared.DomainEvent;
import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Date;
import java.util.Objects;

public class HandingEvent implements DomainEvent<HandingEvent> {

    private Order order;

    private Type type;

    private Date acceptDate;

    public HandingEvent(Order order, Type type) {
        this.order = order;
        this.type = type;
    }

    public HandingEvent(Order order, Type type, Date acceptDate) {
        this.order = order;
        this.type = type;
        this.acceptDate = acceptDate;
        this.order.accept(acceptDate);
    }

    public Order getOrder() {
        return order;
    }

    public Type getType() {
        return type;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    @Override
    public boolean sameEventAs(HandingEvent other) {
        return other != null
                && order.sameIdentityAs(other.order)
                && type.sameValueAs(other.type)
                && Objects.equals(acceptDate, other.acceptDate);
    }

    public enum Type implements ValueObject<Type> {
        IN_STORE_START,
        OUT_STORE_START,
        IN_STORE_DONE,
        OUT_STORE_DONE;

        @Override
        public boolean sameValueAs(Type other) {
            return Objects.equals(this, other);
        }
    }
}

