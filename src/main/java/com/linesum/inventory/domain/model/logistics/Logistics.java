package com.linesum.inventory.domain.model.logistics;

import com.linesum.inventory.domain.shared.Entity;

/**
 * 物流
 */
public class Logistics implements Entity<Logistics> {

    private LogisticsId logisticsId;

    @Override
    public boolean sameIdentityAs(Logistics other) {
        return other != null && this.logisticsId.sameValueAs(other.logisticsId);
    }
}
