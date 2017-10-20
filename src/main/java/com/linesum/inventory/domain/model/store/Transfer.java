package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Objects;

/**
 * 库存转移 包括入库出库
 */
public class Transfer implements ValueObject<Transfer> {

    private LogicStore origin;
    private LogicStore destination;


    @Override
    public boolean sameValueAs(Transfer other) {
        return false;
    }

    /**
     * 转移状态
     */
    public enum TransferStatus implements ValueObject<TransferStatus> {
        ;

        @Override
        public boolean sameValueAs(TransferStatus other) {
            return Objects.equals(this, other);
        }
    }
}
