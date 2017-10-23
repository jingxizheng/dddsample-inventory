package com.linesum.inventory.domain.model.logistics;

import com.linesum.inventory.domain.shared.Entity;

/**
 * 物流
 */
public class Logistics implements Entity<Logistics> {

    private LogisticsId logisticsId; // 物流ID

    private String logisticsNo; // 物流单号

    private static final String INIT_LOGISTICS_NO = null;

    @Override
    public boolean sameIdentityAs(Logistics other) {
        return other != null && this.logisticsId.sameValueAs(other.logisticsId);
    }
}
